package com.imooc.seller.service.impl;

import com.imooc.seller.DTO.OrderDTO;
import com.imooc.seller.service.PayService;
import com.imooc.seller.utils.JsonTestUtil;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PayServiceImpl implements PayService {

    @Autowired
    private BestPayServiceImpl bestPayService;

    private static String ORDERNAME = "微信支付订单名称";

    private static String OPENID = "oTgZpwY_D0o3IU6kLE4LAYkvzUEk";

    public PayResponse create(OrderDTO orderDTO) {
        PayRequest payRequest = new PayRequest();
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_MP);
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setOrderName(ORDERNAME);
        payRequest.setOrderAmount(0.01);
        payRequest.setOpenid(OPENID);
        log.info("【微信支付】请求参数：{}", payRequest);
        PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("【微信支付】返回结果：{}", payResponse);
        return payResponse;
    }
}
