package com.imooc.seller.service;

import com.imooc.seller.DTO.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    // 创建订单
    OrderDTO create(OrderDTO orderDTO);

    // 查询单个订单
    OrderDTO findOne(String orderId);

    // 查询订单列表
    Page<OrderDTO> findList(String buyerOpenid, Pageable pageable);

    // 取消订单
    OrderDTO cancel(OrderDTO orderDTO);

    // 订单完成
    OrderDTO finish(OrderDTO orderDTO);

    // 支付完成
    OrderDTO paid(OrderDTO orderDTO);

    // 查询订单列表
    Page<OrderDTO> findList(Pageable pageable);
}
