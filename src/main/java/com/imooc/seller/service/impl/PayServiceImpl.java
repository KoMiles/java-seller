package com.imooc.seller.service.impl;

import com.imooc.seller.DTO.OrderDTO;
import com.imooc.seller.enums.ResultEnums;
import com.imooc.seller.exception.SellerException;
import com.imooc.seller.service.PayService;
import com.imooc.seller.utils.JsonTestUtil;
import com.imooc.seller.utils.MathUtil;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PayServiceImpl implements PayService {

    @Autowired
    private BestPayServiceImpl bestPayService;

    @Autowired
    private OrderServiceImpl orderService;

    private static String ORDERNAME = "微信支付订单名称";

    private static String OPENID = "oTgZpwY_D0o3IU6kLE4LAYkvzUEk";

    @Override
    public PayResponse create(OrderDTO orderDTO) {
        PayRequest payRequest = new PayRequest();
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_MP);
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setOrderName(ORDERNAME);
        payRequest.setOrderAmount(0.01);
        payRequest.setOpenid(OPENID);
        log.info("【微信支付】发起支付 请求参数：{}", payRequest);
        PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("【微信支付】发起支付 返回结果：{}", payResponse);
        return payResponse;
    }

    /**
     * 异步通知
     */
    @Override
    public PayResponse notify(String notifyData) {
        // 1.验证签名
        // 2.支付状态
        // 3.支付金额
        // 4.支付人
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        OrderDTO orderDTO = orderService.findOne(payResponse.getOrderId());
        if(orderDTO == null) {
            log.error("【微信支付】异步通知，订单不存在：{}", payResponse.getOrderId());
            throw new SellerException(ResultEnums.ORDER_NOT_EXIST);
        }
        if(!MathUtil.equals(orderDTO.getOrderAmount().doubleValue(), payResponse.getOrderAmount())){
            log.error("【微信支付】异步通知，订单金额不一致：订单id:{},系统金额:{},微信通知金额:{}",
                    payResponse.getOrderId(),
                    orderDTO.getOrderAmount(),
                    payResponse.getOrderAmount());
            throw new SellerException(ResultEnums.ORDER_AMOUNT_NOT_EQUAL);
        }

        // 修改支付状态
        orderService.paid(orderDTO);
        log.info("【微信支付】异步通知 返回结果：{}", payResponse);
        return payResponse;
    }

    /**
     * 退款
     */
    @Override
    public RefundResponse refund(OrderDTO orderDTO) {
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        refundRequest.setOrderId(orderDTO.getOrderId());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_MP);
        log.info("【微信支付】微信退款 请求参数：{}", refundRequest);
        RefundResponse refundResponse = bestPayService.refund(refundRequest);
        log.info("【微信支付】微信退款 返回结果：{}", refundResponse);
        return refundResponse;
    }
}
