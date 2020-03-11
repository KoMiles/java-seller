package com.imooc.seller.enums;

public enum ResultEnums {
    SUCCESS(0,"成功"),
    PARAMS_ERROR(1,"参数不正确"),
    PRODUCT_NOT_EXIST(10,"商品不存在"),
    PRODUCT_STOCK_ERROR(11,"库存不正确"),
    PRODUCT_DETAIL_ERROR(12,"订单详情不存在"),
    ORDER_STATUS_ERROR(13,"订单状态不正确"),
    ORDER_UPDATE_FAIL(14,"修改订单状态失败"),
    ORDER_DETAIL_EMPTY(15,"订单详情为空"),
    ORDER_PAY_STATUS_ERROR(16,"支付状态不正确"),
    ORDER_PAY_UPDATE_FAIL(17,"支付状态更新失败"),
    CART_NOT_EMPTY(18,"购物车不能为空"),
    ORDER_OWNER_ERROR(19,"不是你的订单"),
    WECHART_MP_ERROR(20,"微信授权错误"),

    ;

    private Integer code;

    private String message;

    ResultEnums(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
