package com.imooc.seller.enums;

import lombok.Getter;

@Getter
public enum PayStatusEnums {
    NOT_PAY(0,"未支付"),
    pay(1,"已支付")
    ;
    private Integer code;

    private String message;

    PayStatusEnums(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
