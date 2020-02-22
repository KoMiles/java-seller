package com.imooc.seller.service;

import com.imooc.seller.dataobject.ProductInfo;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductInfoService {
    // 查找一个商品
    ProductInfo findOne(String Id);

    // 查找在线的商品
    List<ProductInfo> findOnlineList(Integer productStatus);

    // 查找全部商品,带分页
    Page<ProductInfo> findAll(Pageable pageable);

    // 保存商品
    ProductInfo save(ProductInfo productInfo);

    // 加库存

    // 减库存
}
