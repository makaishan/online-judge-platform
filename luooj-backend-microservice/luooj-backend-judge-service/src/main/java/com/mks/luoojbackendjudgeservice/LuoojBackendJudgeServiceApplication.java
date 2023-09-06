package com.mks.luoojbackendjudgeservice;

import com.mks.luoojbackendjudgeservice.rabbitmq.InitRabbitMQ;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@ComponentScan("com.mks")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.mks.luoojbackendserviceclient.service"})
public class LuoojBackendJudgeServiceApplication {

    public static void main(String[] args) {
        // 项目启动前启动消息队列
        InitRabbitMQ.initRabbitMQ();
        SpringApplication.run(LuoojBackendJudgeServiceApplication.class, args);
    }

}
