package com.imooc.seller.service.impl;

import com.imooc.seller.DTO.CartDTO;
import com.imooc.seller.DTO.OrderDTO;
import com.imooc.seller.converter.OrderMaster2OrderDTOConverter;
import com.imooc.seller.dataobject.OrderDetail;
import com.imooc.seller.dataobject.OrderMaster;
import com.imooc.seller.dataobject.ProductInfo;
import com.imooc.seller.enums.OrderStatusEnums;
import com.imooc.seller.enums.PayStatusEnums;
import com.imooc.seller.enums.ResultEnums;
import com.imooc.seller.exception.SellerException;
import com.imooc.seller.repository.OrderDetailRepository;
import com.imooc.seller.repository.OrderMasterRepository;
import com.imooc.seller.service.OrderService;
import com.imooc.seller.service.ProductInfoService;
import com.imooc.seller.utils.KeyUtil;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductInfoServiceImpl productInfoService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        String orderId = KeyUtil.genUniqueKey();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        // 查询商品数量、价格【from商品表】
        for(OrderDetail orderDetail: orderDTO.getOrderDetailList()) {
            ProductInfo productInfo = productInfoService.findOne(orderDetail.getProductId());
            if(productInfo == null) {
                throw new SellerException(ResultEnums.PRODUCT_NOT_EXIST);
            }
            // 计算订单总价
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);

            // 写入订单明细表
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            orderDetailRepository.save(orderDetail);
        }
        // 写入订单表
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnums.NEW_ORDER.getCode());
        orderMaster.setPayStatus(PayStatusEnums.NOT_PAY.getCode());
        orderMasterRepository.save(orderMaster);

        // 扣库存
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e ->
                new CartDTO(e.getProductId(), e.getProductQuantity())
        ).collect(Collectors.toList());
        productInfoService.decreaseStock(cartDTOList);

        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        // 查询订单表
        OrderMaster orderMaster = orderMasterRepository.findById(orderId).orElse(null);
        if(orderMaster == null) {
            throw new SellerException(ResultEnums.PRODUCT_NOT_EXIST);
        }
        // 查询订单详情表
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if(CollectionUtils.isEmpty(orderDetailList)) {
            throw new SellerException(ResultEnums.PRODUCT_DETAIL_ERROR);
        }

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);

        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());

        Page<OrderDTO> orderDTOPage = new PageImpl<OrderDTO>(orderDTOList,pageable, orderMasterPage.getTotalElements());
        return orderDTOPage;
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();
        // 判断订单状态，非新订单不能取消
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnums.NEW_ORDER.getCode())) {
            log.info("【订单取消】DTO:{}",orderDTO );
            throw new SellerException(ResultEnums.ORDER_STATUS_ERROR);
        }

        orderDTO.setOrderStatus(OrderStatusEnums.CANCEL_ORDER.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        // 修改订单状态
        OrderMaster orderMasterResult = orderMasterRepository.save(orderMaster);
        if(orderMasterResult == null) {
            throw new SellerException(ResultEnums.ORDER_UPDATE_FAIL);
        }

        // 返回库存
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            throw new SellerException(ResultEnums.ORDER_DETAIL_EMPTY);
        }
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e-> new CartDTO(e.getProductId(),e.getProductQuantity()))
                .collect(
                Collectors.toList());

        productInfoService.increaseStock(cartDTOList);

        // 如果已支付，需要退款
        if(orderDTO.getPayStatus().equals(PayStatusEnums.SUCCESS_PAY.getCode())){
            // TODO
        }
        return orderDTO;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        // 判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnums.NEW_ORDER.getCode())) {
            log.info("【订单完结】订单状态不正确 DTO:{}",orderDTO );
            throw new SellerException(ResultEnums.ORDER_STATUS_ERROR);
        }
        // 修改订单
        orderDTO.setOrderStatus(OrderStatusEnums.FINISHED_ORDER.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster orderMasterResult = orderMasterRepository.save(orderMaster);
        if(orderMasterResult == null) {
            log.info("【订单完结】更新失败 DTO:{}",orderDTO );
            throw new SellerException(ResultEnums.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        // 判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnums.NEW_ORDER.getCode())) {
            log.info("【订单支付成功】订单状态不正确 DTO:{}",orderDTO );
            throw new SellerException(ResultEnums.ORDER_STATUS_ERROR);
        }

        // 判断支付状态
        if(!orderDTO.getPayStatus().equals(PayStatusEnums.NOT_PAY.getCode())) {
            log.info("【订单支付成功】订单支付状态不正确 DTO:{}",orderDTO );
            throw new SellerException(ResultEnums.ORDER_PAY_STATUS_ERROR);
        }

        // 修改支付状态
        orderDTO.setPayStatus(PayStatusEnums.SUCCESS_PAY.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster orderMasterResult = orderMasterRepository.save(orderMaster);
        if(orderMasterResult == null) {
            log.info("【订单支付成功】更新失败 orderMaster:{}",orderMaster );
            throw new SellerException(ResultEnums.ORDER_PAY_UPDATE_FAIL);
        }
        return orderDTO;
    }
}
