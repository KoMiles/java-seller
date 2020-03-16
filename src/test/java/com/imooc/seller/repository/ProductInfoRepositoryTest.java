package com.imooc.seller.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.imooc.seller.dataobject.ProductInfo;
import java.math.BigDecimal;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {

    @Autowired
    ProductInfoRepository repository;

    @Test
    public void saveTest() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123456");
        productInfo.setProductName("鸡蛋汤");
        productInfo.setProductPrice(new BigDecimal(3.2));
        productInfo.setProductStock(10);
        productInfo.setProductDescription("很好喝的粥");
        productInfo.setProductIcon("http://xxxx/xxx.jpg");
        productInfo.setProductStatus(0);
        productInfo.setCategoryType(2);
        ProductInfo result = repository.save(productInfo);
        Assert.assertNotNull(result);

    }

    @Test
    public void findProductInfoByCategoryType() {
        List<ProductInfo> productInfoList = repository.findProductInfoByProductStatus(0);
        Assert.assertNotEquals(0, productInfoList.size());
    }
}