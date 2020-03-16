package com.imooc.seller.service;

import com.imooc.seller.DTO.OrderDTO;
import com.lly835.bestpay.model.PayResponse;

public interface PayService {

    PayResponse create(OrderDTO orderDTO);

}
