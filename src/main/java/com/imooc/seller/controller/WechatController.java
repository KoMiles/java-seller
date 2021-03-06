package com.imooc.seller.controller;

import com.imooc.seller.config.WechatAccountConfig;
import com.imooc.seller.enums.ResultEnums;
import com.imooc.seller.exception.SellerException;
import java.net.URLEncoder;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//@RestController
@Controller
@RequestMapping("/wechat")
@Slf4j
public class WechatController {

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WechatAccountConfig wechatAccountConfig;

    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl) {
        String url = "http://komiles.nat100.top/sell/wechat/userInfo";
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO,
                URLEncoder.encode(returnUrl));
        log.info("【微信网页授权】result:{}", redirectUrl);
        return "redirect:" + redirectUrl;
    }

    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code") String code, @RequestParam("state") String returnUrl) {
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);

        } catch (WxErrorException e) {
            log.error("【获取用户信息】{}", e.getMessage());
            throw new SellerException(ResultEnums.WECHART_MP_ERROR.getCode(), e.getError().getErrorMsg());
        }
        String openid = wxMpOAuth2AccessToken.getOpenId();
        log.info("【获取用户信息】openid:{}", openid);
        return "redirect:" + returnUrl + "?openid=" + openid;
    }
}
