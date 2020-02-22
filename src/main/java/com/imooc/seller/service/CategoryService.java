package com.imooc.seller.service;

import com.imooc.seller.dataobject.ProductCategory;
import java.util.List;

public interface CategoryService {

    // 查找一个
    ProductCategory findOne(Integer id);

    // 查找全部
    List<ProductCategory> findAll();

    // 根据分类查询
    List<ProductCategory> findByCategoryTypeIn(List<Integer> idList);

    // 保存
    ProductCategory save(ProductCategory productCategory);


}
