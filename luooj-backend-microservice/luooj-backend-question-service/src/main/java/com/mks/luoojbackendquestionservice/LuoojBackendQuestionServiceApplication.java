package com.mks.luoojbackendquestionservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@MapperScan("com.mks.luoojbackendquestionservice.mapper")
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@ComponentScan("com.mks")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.mks.luoojbackendserviceclient.service"})
public class LuoojBackendQuestionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LuoojBackendQuestionServiceApplication.class, args);
    }

}
