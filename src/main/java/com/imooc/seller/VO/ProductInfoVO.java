package com.imooc.seller.VO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.imooc.seller.dataobject.ProductCategory;
import com.imooc.seller.enums.ProductStatusEnums;
import com.imooc.seller.utils.CodeEnumsUtil;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 单个商品对象
 */
@Data
public class ProductInfoVO {

    @JsonProperty("id")
    private String productId;

    @JsonProperty("name")
    private String productName;

    @JsonProperty("price")
    private BigDecimal productPrice;

    @JsonProperty("description")
    private String productDescription;

    @JsonProperty("icon")
    private String productIcon;

    @JsonIgnore
    private Integer productStock;

    @JsonIgnore
    private Integer productStatus;

    @JsonIgnore
    private Integer categoryType;

    @JsonIgnore
    private Date createTime;

    @JsonIgnore
    private Date updateTime;


    public ProductStatusEnums getProductStatusEnums(){
        return CodeEnumsUtil.getByCode(productStatus,ProductStatusEnums.class);
    }

    @JsonIgnore
    private ProductCategory productCategory;
}
