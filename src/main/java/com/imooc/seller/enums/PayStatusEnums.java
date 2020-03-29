package com.imooc.seller.enums;

import lombok.Getter;

@Getter
public enum PayStatusEnums implements CodeEnums{
    NOT_PAY(0, "未支付"),
    SUCCESS_PAY(1, "已支付");
    private Integer code;

    private String message;

    PayStatusEnums(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

//    public static PayStatusEnums getPayStatusEnums(Integer code){
//        for(PayStatusEnums payStatusEnums : PayStatusEnums.values()){
//            if(payStatusEnums.getCode().equals(code)) {
//                return payStatusEnums;
//            }
//        }
//        return null;
//    }
}
