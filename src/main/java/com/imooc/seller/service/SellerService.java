package com.imooc.seller.service;

import com.imooc.seller.dataobject.SellerInfo;

/**
 * @author komiles@163.com
 * @date 2020-04-08 22:31
 */
public interface SellerService {

    SellerInfo save(SellerInfo sellerInfo);

    SellerInfo findSellerByOpenid(String openid);

}
