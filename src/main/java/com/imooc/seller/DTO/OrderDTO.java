package com.imooc.seller.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion;
import com.imooc.seller.dataobject.OrderDetail;
import com.imooc.seller.enums.OrderStatusEnums;
import com.imooc.seller.enums.PayStatusEnums;
import com.imooc.seller.utils.CodeEnumsUtil;
import com.imooc.seller.utils.serializer.Date2LongSerializer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
//@JsonInclude(Include.NON_NULL)
//@JsonSerialize(include = Inclusion.NON_NULL)
public class OrderDTO {

    private String orderId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    private String buyerOpenid;

    private BigDecimal orderAmount;

    private Integer orderStatus;

    private Integer payStatus;

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    private List<OrderDetail> orderDetailList = new ArrayList<>();

    @JsonIgnore
    public OrderStatusEnums getOrderStatusEnums()
    {
        return CodeEnumsUtil.getByCode(orderStatus,OrderStatusEnums.class);
//        return OrderStatusEnums.getOrderStatusEnums(orderStatus);
    }

    @JsonIgnore
    public PayStatusEnums getPayStatusEnums()
    {
        return CodeEnumsUtil.getByCode(payStatus,PayStatusEnums.class);
//        return PayStatusEnums.getPayStatusEnums(payStatus);
    }
}
