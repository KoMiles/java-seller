package com.imooc.seller.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.imooc.seller.dataobject.ProductCategory;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository repository;


    @Test
    public void findOneTest()
    {
        ProductCategory productCategory = repository.findById(1).orElse(null);
        System.out.println(productCategory.toString());
    }

    @Test
    @Transactional
    public void addTest()
    {
        ProductCategory productCategory = new ProductCategory("吃喝榜",3);
        ProductCategory result = repository.save(productCategory);

        Assert.assertNotNull(result);
    }

    @Test
    public void updateTest()
    {
        ProductCategory productCategory = repository.findById(1).orElse(null);
        productCategory.setCategoryType(12);
        repository.save(productCategory);

    }


    @Test
    public void findProductCategoryByCategoryTypeInTest()
    {
        List<Integer> list = Arrays.asList(4,6,8);
        List<ProductCategory> result = repository.findByCategoryTypeNotIn(list);
        Assert.assertNotEquals(0,result.size());
    }
}