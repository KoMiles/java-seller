package com.imooc.seller.controller;

import com.imooc.seller.DTO.OrderDTO;
import com.imooc.seller.enums.ResultEnums;
import com.imooc.seller.exception.SellerException;
import com.imooc.seller.service.OrderService;
import com.imooc.seller.service.PayService;
import com.imooc.seller.service.impl.OrderServiceImpl;
import com.lly835.bestpay.model.PayResponse;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/pay")
@Slf4j
public class PayController {

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private PayService payService;

    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
            @RequestParam("returnUrl") String returnUrl,
            Map<String,Object> map)
    {
        OrderDTO orderDTO = orderService.findOne(orderId);
        if(orderDTO == null ) {
            log.info("【订单支付】订单详情不存在，订单id为：{}", orderId);
            throw new SellerException(ResultEnums.PRODUCT_DETAIL_ERROR);
        }

        PayResponse payResponse = payService.create(orderDTO);
        map.put("payResponse",payResponse);
        map.put("returnUrl", returnUrl);
        return new ModelAndView("pay/create", map);
    }

    @PostMapping("/notify")
    public void notifyUrl()
    {

    }
}
