package com.imooc.seller.service;

import com.imooc.seller.DTO.OrderDTO;

public interface BuyerService {

    OrderDTO findOrderOne(String openid, String orderId);

    OrderDTO cancelOrder(String openid, String orderId);
}
