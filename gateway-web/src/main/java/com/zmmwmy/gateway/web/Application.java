package com.zmmwmy.gateway.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author zhouqiming
 * @date 2021-04-12 8:39 下午
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.zmmwmy")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
