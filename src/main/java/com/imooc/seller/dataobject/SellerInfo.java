package com.imooc.seller.dataobject;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

/**
 * @author komiles@163.com
 * @date 2020-04-08 22:24
 */
@Entity
@DynamicUpdate
@Data
public class SellerInfo {

    @Id
    private String sellerId;

    private String username;

    private String password;

    private String openid;

    private Date createTime;

    private Date updateTime;
}
