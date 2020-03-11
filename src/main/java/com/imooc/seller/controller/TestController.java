package com.imooc.seller.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @GetMapping("/hello")
    public void hello(@RequestParam("id") Integer id)
//    public void hello(HttpServletRequest request)
    {
//        if(check(request)) {
//            System.out.println("OK");
//        } else {
//            System.out.println("error");
//        }
//        String id = request.getParameter("id");
        log.info("id:{}", id);
        System.out.println(id);
    }

    @GetMapping("/list")
    public void list(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize, HttpServletRequest request)
    {
        if(check(request)) {
            System.out.println("OK");
        } else {
            System.out.println("error");
        }
//        log.info("page:{},pageSize:{}", page, pageSize);
//        System.out.println("page:"+page+"pageSize:"+pageSize);
    }

    @GetMapping("check")
    public boolean check(HttpServletRequest request)
    {
        String uri = request.getRequestURI();
//        ArrayList<String> urlList = new ArrayList<>();
//        urlList.add("/sell/test2");
//        urlList.add("/sell/test1");
        List<String> urlList = Arrays.asList("/sell/test2", "/sell/test");
        for (String path:urlList) {
            if(uri.contains(path)) {
                return true;
            }
        }
//        return urlList.contains(uri);
//        return uri.contains("/sell/test1");
        return false;
    }
}
