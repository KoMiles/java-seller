package com.imooc.seller.controller;

import com.imooc.seller.DTO.OrderDTO;
import com.imooc.seller.VO.ResultVO;
import com.imooc.seller.converter.OrderForm2OrderDTOConverter;
import com.imooc.seller.enums.ResultEnums;
import com.imooc.seller.exception.SellerException;
import com.imooc.seller.form.OrderForm;
import com.imooc.seller.service.OrderService;
import com.imooc.seller.service.impl.BuyerServiceImpl;
import com.imooc.seller.service.impl.OrderServiceImpl;
import com.imooc.seller.utils.ResultVOUtils;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/buyer/order")
public class BuyerOrderController {

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private BuyerServiceImpl buyerService;

    private final  static String PAY_OPENID = "oTgZpwY_D0o3IU6kLE4LAYkvzUEk";

    // 创建订单
    @PostMapping("/create")
    public ResultVO<Map<String, String>> createOrder(@Valid OrderForm orderForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("【创建订单】参数不正确, orderForm:{}", orderForm);
            throw new SellerException(ResultEnums.PARAMS_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {

            log.error("【创建订单】购物车不能为空，orderDTO:{}", orderDTO);
            throw new SellerException(ResultEnums.CART_NOT_EMPTY);
        }
//        orderDTO.setBuyerOpenid(PAY_OPENID);
        OrderDTO orderResult = orderService.create(orderDTO);

        Map<String, String> map = new HashMap<>();

        map.put("orderId", orderResult.getOrderId());
        return ResultVOUtils.success(map);

    }

    // 订单列表
    @GetMapping("/list")
    public ResultVO<OrderDTO> listOrder(@RequestParam("openid") String openid,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {

        if (StringUtils.isEmpty(openid)) {
            log.error("【订单列表】openid不能为空，openid:{}", openid);
            throw new SellerException(ResultEnums.PARAMS_ERROR);
        }
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<OrderDTO> orderDTOPage = orderService.findList(openid, pageRequest);

        return ResultVOUtils.success(orderDTOPage.getContent());
    }


    // 订单详情
    @GetMapping("/detail")
    public ResultVO<OrderDTO> detailOrder(@RequestParam("openid") String openid,
            @RequestParam("orderId") String orderId) {
        if (StringUtils.isEmpty(openid)) {
            log.error("【订单详情】openId不能为空，openid:{}", openid);
            throw new SellerException(ResultEnums.PARAMS_ERROR);
        }
        if (StringUtils.isEmpty(orderId)) {
            log.error("【订单详情】orderid不能为空，orderId:{}", orderId);
            throw new SellerException(ResultEnums.PARAMS_ERROR);
        }
        OrderDTO orderDTO = buyerService.findOrderOne(openid, orderId);
        return ResultVOUtils.success(orderDTO);
    }

    // 订单取消
    @PostMapping("/cancel")
    public ResultVO cancelOrder(@RequestParam("openid") String openid, @RequestParam("orderId") String orderId) {
        if (StringUtils.isEmpty(openid)) {
            log.error("【取消订单】openId不能为空，openid:{}", openid);
            throw new SellerException(ResultEnums.PARAMS_ERROR);
        }
        if (StringUtils.isEmpty(orderId)) {
            log.error("【取消订单】orderid不能为空，orderId:{}", orderId);
            throw new SellerException(ResultEnums.PARAMS_ERROR);
        }
        buyerService.cancelOrder(openid, orderId);
        return ResultVOUtils.success();
    }

}
