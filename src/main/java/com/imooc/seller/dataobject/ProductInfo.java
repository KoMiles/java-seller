package com.imooc.seller.dataobject;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.imooc.seller.enums.ProductStatusEnums;
import com.imooc.seller.utils.CodeEnumsUtil;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.Data;
import org.apache.commons.lang3.EnumUtils;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Data
@DynamicUpdate
public class ProductInfo {

    @Id
    private String productId;

    private String productName;

    private BigDecimal productPrice;

    private Integer productStock;

    private String productDescription;

    private String productIcon;

    private Integer productStatus = ProductStatusEnums.UP.getCode();

    private Integer categoryType;

    private Date createTime;

    private Date updateTime;

    public ProductStatusEnums getProductStatusEnums(){
        return CodeEnumsUtil.getByCode(productStatus,ProductStatusEnums.class);
    }

}
