package com.imooc.seller.repository;

import com.imooc.seller.dataobject.ProductCategory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Integer> {

    // 通过类目id查询类目
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
