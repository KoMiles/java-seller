package com.imooc.seller.repository;

import com.imooc.seller.dataobject.ProductCategory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Integer> {



//    List<ProductCategory> findProductCategoryByCategoryTypeIn(List<Integer> categoryTypeList);
//    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
    List<ProductCategory> findByCategoryTypeNotIn(List<Integer> categoryTypeList);
}
