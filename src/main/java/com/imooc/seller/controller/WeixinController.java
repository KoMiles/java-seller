package com.imooc.seller.controller;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/weixin")
@Slf4j
public class WeixinController {

    @GetMapping("/auth")
    public void auth(@RequestParam("code") String code)
    {
        log.info("进入auth方法");
        log.info("回调获取code:{}", code);

//        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx0a8f8e9e240e1b01&secret=209b93beada12f8174ffecf2246acf62&code="+code+"&grant_type=authorization_code";
//
//        RestTemplate restTemplate = new RestTemplate();
//        String response = restTemplate.getForObject(url,String.class);
//        log.info("response:{}", response);
        WxMpDefaultConfigImpl config = new WxMpDefaultConfigImpl();
        config.setAppId("..."); // 设置微信公众号的appid
        config.setSecret("..."); // 设置微信公众号的app corpSecret
        config.setToken("..."); // 设置微信公众号的token
        config.setAesKey("..."); // 设置微信公众号的EncodingAESKey
    }
}
