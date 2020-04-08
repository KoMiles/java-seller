package com.imooc.seller.repository;

import com.imooc.seller.dataobject.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author komiles@163.com
 * @date 2020-04-08 22:27
 */
public interface SellerInfoRepository extends JpaRepository<SellerInfo, String> {

    // 通过openid查询用户

    SellerInfo findSellerInfoByOpenid(String openid);

}
