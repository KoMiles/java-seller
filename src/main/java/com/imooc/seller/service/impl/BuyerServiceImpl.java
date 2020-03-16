package com.imooc.seller.service.impl;

import com.imooc.seller.DTO.OrderDTO;
import com.imooc.seller.enums.ResultEnums;
import com.imooc.seller.exception.SellerException;
import com.imooc.seller.service.BuyerService;
import com.imooc.seller.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private OrderService orderService;

    @Override
    public OrderDTO findOrderOne(String openid, String orderId) {
        return checkOrderOwner(openid, orderId);
    }

    @Override
    public OrderDTO cancelOrder(String openid, String orderId) {
        OrderDTO orderDTO = checkOrderOwner(openid, orderId);
        if (orderDTO == null) {
            log.info("【取消订单】订单详情不存在,orderId:{}", orderId);
            throw new SellerException(ResultEnums.ORDER_DETAIL_EMPTY);
        }
        return orderService.cancel(orderDTO);
    }


    private OrderDTO checkOrderOwner(String openid, String orderId) {
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (orderDTO == null) {
            return null;
        }
        if (!orderDTO.getBuyerOpenid().equals(openid)) {
            log.info("【查询订单】不是你的订单,openid:{}, orderDTO.openid:{}", openid, orderDTO.getBuyerOpenid());
            throw new SellerException(ResultEnums.ORDER_OWNER_ERROR);
        }
        return orderDTO;
    }

}
