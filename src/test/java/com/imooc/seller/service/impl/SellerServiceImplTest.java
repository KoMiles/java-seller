package com.imooc.seller.service.impl;

import static org.junit.Assert.*;

import com.imooc.seller.dataobject.SellerInfo;
import com.imooc.seller.service.SellerService;
import com.imooc.seller.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author komiles@163.com
 * @date 2020-04-08 22:34
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class SellerServiceImplTest {

    @Autowired
    private SellerService sellerService;

    private static final String OPEN_ID = "aaaaaa";

    @Test
    public void save() {
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setSellerId(KeyUtil.genUniqueKey());
        sellerInfo.setUsername("张三");
        sellerInfo.setPassword("123456");
        sellerInfo.setOpenid(OPEN_ID);

        SellerInfo result = sellerService.save(sellerInfo);
        Assert.assertEquals(OPEN_ID, result.getOpenid());
    }

    @Test
    public void findSellerByOpenid() {
        SellerInfo result = sellerService.findSellerByOpenid(OPEN_ID);
        Assert.assertEquals(OPEN_ID,result.getOpenid());
    }
}