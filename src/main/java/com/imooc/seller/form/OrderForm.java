package com.imooc.seller.form;

import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class OrderForm {

    @NotEmpty(message = "名字不能为空")
    private String name;

    @NotEmpty(message = "手机号不能为空")
    private String phone;

    @NotEmpty(message = "地址不能为空")
    private String address;

    @NotEmpty(message = "openid不能为空")
    private String openid;

    @NotEmpty(message = "购物车不能为空")
    private String items;
}
