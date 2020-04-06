package com.imooc.seller.form;

import com.imooc.seller.enums.ProductStatusEnums;
import com.imooc.seller.utils.CodeEnumsUtil;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @author komiles@163.com
 * @date 2020-04-06 21:06
 */
@Data
public class ProductForm {

    private String productId;

    private String productName;

    private BigDecimal productPrice;

    private Integer productStock;

    private String productDescription;

    private String productIcon;

    private Integer categoryType;

}
