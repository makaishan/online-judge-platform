package com.mks.luoojbackendjudgeservice.rabbitmq;

import com.mks.luoojbackendcommon.constant.RabbitMQConstant;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InitRabbitMQ {
    public static void initRabbitMQ() {
        try {
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost(RabbitMQConstant.LOCALHOST);
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(RabbitMQConstant.JUDGE_EXCHANGE_NAME, "direct");
            channel.queueDeclare(RabbitMQConstant.JUDGE_QUEUE_NAME, true, false, false, null);
            channel.queueBind(RabbitMQConstant.JUDGE_QUEUE_NAME, RabbitMQConstant.JUDGE_EXCHANGE_NAME, RabbitMQConstant.JUDGE_ROUTING_KEY);
            log.info("rabbitmq 启动成功。");
        } catch (Exception e) {
            log.error("rabbitmq 启动失败。" + e.getMessage());
        }
    }
}
