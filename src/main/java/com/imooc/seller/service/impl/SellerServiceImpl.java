package com.imooc.seller.service.impl;

import com.imooc.seller.dataobject.SellerInfo;
import com.imooc.seller.repository.SellerInfoRepository;
import com.imooc.seller.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author komiles@163.com
 * @date 2020-04-08 22:33
 */
@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerInfoRepository sellerInfoRepository;

    @Override
    public SellerInfo save(SellerInfo sellerInfo) {
        return sellerInfoRepository.save(sellerInfo);
    }

    @Override
    public SellerInfo findSellerByOpenid(String openid) {
       return sellerInfoRepository.findSellerInfoByOpenid(openid);
    }
}
