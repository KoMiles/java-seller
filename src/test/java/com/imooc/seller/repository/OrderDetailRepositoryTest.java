package com.imooc.seller.repository;

import static org.junit.Assert.*;

import com.imooc.seller.dataobject.OrderDetail;
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
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository repository;

    @Test
    public void save() {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("123456");
        orderDetail.setOrderId("11111111");
        orderDetail.setProductId("123457");
        orderDetail.setProductName("小笼包");
        orderDetail.setProductPrice(new BigDecimal("6.2"));
        orderDetail.setProductQuantity(3);
        orderDetail.setProductIcon("http://xxx.jpg.com");

        OrderDetail result = repository.save(orderDetail);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByOrderId() {
        List<OrderDetail> OrderDetailList = repository.findByOrderId("11111111");
        Assert.assertNotEquals(0, OrderDetailList.size());
    }
}