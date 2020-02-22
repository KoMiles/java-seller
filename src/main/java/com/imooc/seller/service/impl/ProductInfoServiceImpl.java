package com.imooc.seller.service.impl;

import com.imooc.seller.dataobject.ProductInfo;
import com.imooc.seller.enums.ProductStatusEnums;
import com.imooc.seller.repository.ProductInfoRepository;
import com.imooc.seller.service.ProductInfoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    private ProductInfoRepository repository;

    @Override
    public ProductInfo findOne(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<ProductInfo> findOnlineList(Integer productStatus) {
        return repository.findProductInfoByProductStatus(ProductStatusEnums.DONW.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }
}
