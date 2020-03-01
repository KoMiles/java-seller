package com.imooc.seller.repository;

import static org.junit.Assert.*;

import com.imooc.seller.dataobject.OrderMaster;
import com.imooc.seller.enums.OrderStatusEnums;
import com.imooc.seller.enums.PayStatusEnums;
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
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository repository;

    private final static String BUYEROPENID = "88888888";

    @Test
    public void save()
    {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("222222");
        orderMaster.setBuyerName("张三");
        orderMaster.setBuyerPhone("123456789001");
        orderMaster.setBuyerAddress("北京市回龙观");
        orderMaster.setBuyerOpenid(BUYEROPENID);
        orderMaster.setOrderAmount(new BigDecimal("19.2"));
        orderMaster.setOrderStatus(OrderStatusEnums.CANCEL_ORDER.getCode());
        orderMaster.setPayStatus(PayStatusEnums.NOT_PAY.getCode());
        OrderMaster result = repository.save(orderMaster);
        Assert.assertNotNull(result);
    }


    @Test
    public void findByBuyerOpenid() {
        List<OrderMaster> result = repository.findByBuyerOpenid(BUYEROPENID);
        Assert.assertNotEquals(0, result.size());
    }
}