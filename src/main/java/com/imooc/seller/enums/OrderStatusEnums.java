package com.imooc.seller.enums;

import lombok.Getter;

@Getter
public enum OrderStatusEnums implements CodeEnums{
    NEW_ORDER(0, "新订单"),
    FINISHED_ORDER(1, "订单完成"),
    CANCEL_ORDER(2, "订单取消"),
    ;
    private Integer code;

    private String message;

    OrderStatusEnums(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

//    public static OrderStatusEnums getOrderStatusEnums(Integer code){
//        for(OrderStatusEnums orderStatusEnums : OrderStatusEnums.values()){
//            if(orderStatusEnums.getCode().equals(code)) {
//                return orderStatusEnums;
//            }
//        }
//        return null;
//    }
}

