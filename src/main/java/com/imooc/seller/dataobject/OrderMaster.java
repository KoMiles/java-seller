package com.imooc.seller.dataobject;

import com.imooc.seller.enums.OrderStatusEnums;
import com.imooc.seller.enums.PayStatusEnums;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

@Data
@Entity
@DynamicUpdate
public class OrderMaster {

    @Id
    private String orderId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    private String buyerOpenid;

    private BigDecimal orderAmount;

    private Integer orderStatus = OrderStatusEnums.NEW_ORDER.getCode();

    private Integer payStatus = PayStatusEnums.NOT_PAY.getCode();

    private Date createTime;

    private Date updateTime;
}
