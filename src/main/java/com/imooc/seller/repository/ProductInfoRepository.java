package com.imooc.seller.repository;

import com.imooc.seller.dataobject.ProductInfo;
import java.awt.print.Pageable;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {

    // 通过类目查询商品
    List<ProductInfo> findProductInfoByCategoryType(Integer id);

    // 通过商品状态查询商品
    List<ProductInfo> findProductInfoByProductStatus(Integer productStatus);
}

