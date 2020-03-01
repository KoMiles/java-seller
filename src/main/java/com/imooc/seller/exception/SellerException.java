package com.imooc.seller.exception;

import com.imooc.seller.enums.ResultEnums;

public class SellerException extends  RuntimeException{

    private Integer code;

    private String message;

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public SellerException(ResultEnums resultEnums) {
        super(resultEnums.getMessage());
        this.code = resultEnums.getCode();
    }
}
