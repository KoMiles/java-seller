package com.imooc.seller.controller;

import com.imooc.seller.DTO.OrderDTO;
import com.imooc.seller.enums.ResultEnums;
import com.imooc.seller.exception.SellerException;
import com.imooc.seller.service.OrderService;
import com.imooc.seller.service.PayService;
import com.imooc.seller.service.impl.OrderServiceImpl;
import com.imooc.seller.service.impl.PayServiceImpl;
import com.lly835.bestpay.model.PayResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/pay")
@Slf4j
public class PayController {

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private PayServiceImpl payService;

    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
            @RequestParam("returnUrl") String returnUrl,
            Map<String, Object> map) {
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (orderDTO == null) {
            log.info("【订单支付】订单详情不存在，订单id为：{}", orderId);
            throw new SellerException(ResultEnums.PRODUCT_DETAIL_ERROR);
        }

        PayResponse payResponse = payService.create(orderDTO);
        map.put("payResponse", payResponse);
        log.info("【创建订单】返回url地址-returnUrl:{}", returnUrl);
        try {
            String decodeUrl = URLDecoder.decode(returnUrl, "UTF-8");
            log.info("【创建订单】解析之后的地址-decodeUrl:{}", decodeUrl);
            map.put("returnUrl", decodeUrl);
        } catch (UnsupportedEncodingException e) {
            log.error("[支付订单] 解析返回地址错误, returnUrl={}", returnUrl);
            e.printStackTrace();
        }
        return new ModelAndView("pay/create", map);
    }

    @PostMapping("/notify")
    public ModelAndView notifyUrl(@RequestBody String notifyData) {
        log.error("[支付异步通知] 返回数据={}", notifyData);
        payService.notify(notifyData);
        return new ModelAndView("pay/success");
    }
}
