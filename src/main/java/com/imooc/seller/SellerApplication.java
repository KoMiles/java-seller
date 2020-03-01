package com.imooc.seller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SellerApplication {

    public static void main(String[] args) {
        System.out.println("============= Application Start ==========");
        SpringApplication.run(SellerApplication.class, args);
        System.out.println("============= Application End ==========");
    }

}
