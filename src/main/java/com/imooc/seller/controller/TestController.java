package com.imooc.seller.controller;

import com.imooc.seller.DTO.OrderDTO;
import com.imooc.seller.enums.ResultEnums;
import com.imooc.seller.exception.SellerException;
import com.imooc.seller.service.OrderService;
import com.imooc.seller.service.PayService;
import com.imooc.seller.service.impl.BuyerServiceImpl;
import com.imooc.seller.service.impl.PayServiceImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerServiceImpl buyerService;

    @Autowired
    private PayService payService;

    @GetMapping("/hello")
    public void hello(@RequestParam("id") Integer id)
//    public void hello(HttpServletRequest request)
    {
//        if(check(request)) {
//            System.out.println("OK");
//        } else {
//            System.out.println("error");
//        }
//        String id = request.getParameter("id");
        log.info("id:{}", id);
        System.out.println(id);
    }

    @GetMapping("/list")
    public void list(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize,
            HttpServletRequest request) {
        if (check(request)) {
            System.out.println("OK");
        } else {
            System.out.println("error");
        }
//        log.info("page:{},pageSize:{}", page, pageSize);
//        System.out.println("page:"+page+"pageSize:"+pageSize);
    }

    @GetMapping("/check")
    public boolean check(HttpServletRequest request) {
        String uri = request.getRequestURI();
//        ArrayList<String> urlList = new ArrayList<>();
//        urlList.add("/sell/test2");
//        urlList.add("/sell/test1");
        List<String> urlList = Arrays.asList("/sell/test2", "/sell/test");
        for (String path : urlList) {
            if (uri.contains(path)) {
                return true;
            }
        }
//        return urlList.contains(uri);
//        return uri.contains("/sell/test1");
        return false;
    }

    @GetMapping("/refund")
    public void refund()
    {
        String openid = "oTgZpwY_D0o3IU6kLE4LAYkvzUEk";

        if (StringUtils.isEmpty(openid)) {
            log.error("【订单列表】openid不能为空，openid:{}", openid);
            throw new SellerException(ResultEnums.PARAMS_ERROR);
        }
        ArrayList<String> orderIdList =  buyerService.orderIdList(openid);

        for(String orderId: orderIdList) {
            try{
                OrderDTO orderDTO = orderService.findOne(orderId);
                payService.refund(orderDTO);
            } catch (SellerException e) {
//                log.error("【订单列表】订单退款异常，错误信息:{}", e.getMessage());
                continue;
            }


        }
    }


}
