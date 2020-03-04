package com.imooc.seller.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.imooc.seller.DTO.OrderDTO;
import com.imooc.seller.dataobject.OrderDetail;
import com.imooc.seller.enums.ResultEnums;
import com.imooc.seller.exception.SellerException;
import com.imooc.seller.form.OrderForm;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderForm2OrderDTOConverter {

    public static OrderDTO convert(OrderForm orderForm)
    {
        Gson gson = new Gson();

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

        List<OrderDetail> orderDetailList = new ArrayList<>();

        try {
            orderDetailList = gson.fromJson(orderForm.getItems(),new TypeToken<List<OrderDetail>>(){}.getType());
        } catch (Exception e) {
            log.info("【对象转换错误】String:{}", orderForm.getItems());
            throw new SellerException(ResultEnums.PARAMS_ERROR);
        }


        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
}
