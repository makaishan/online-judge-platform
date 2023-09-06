package com.mks.luoojbackendcommon.constant;

/**
 * RabbitMQ常量
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
public interface RabbitMQConstant {

    /**
     * 本地主机
     */
    String LOCALHOST = "localhost";
    /**
     * 判题交换机
     */
    String JUDGE_EXCHANGE_NAME = "judge_exchange";
    /**
     * 判题队列
     */
    String JUDGE_QUEUE_NAME = "judge_queue";
    /**
     * 判题routingKey
     */
    String JUDGE_ROUTING_KEY = "judge_routingKey";

}
