package com.imooc.seller.enums;

import lombok.Getter;

@Getter
public enum ProductStatusEnums implements CodeEnums{
    UP(0, "在架"),
    DONW(1, "下架");

    private Integer code;

    private String message;

    ProductStatusEnums(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
