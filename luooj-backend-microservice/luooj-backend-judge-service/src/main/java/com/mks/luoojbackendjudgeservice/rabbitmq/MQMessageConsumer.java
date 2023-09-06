package com.mks.luoojbackendjudgeservice.rabbitmq;

import com.mks.luoojbackendcommon.common.ErrorCode;
import com.mks.luoojbackendcommon.constant.RabbitMQConstant;
import com.mks.luoojbackendcommon.exception.BusinessException;
import com.mks.luoojbackendjudgeservice.service.JudgeService;
import com.mks.luoojbackendmodel.model.entity.QuestionSubmit;
import com.rabbitmq.client.Channel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
@Slf4j
public class MQMessageConsumer {

    @Resource
    private JudgeService judgeService;

    @SneakyThrows
    @RabbitListener(queues = {RabbitMQConstant.JUDGE_QUEUE_NAME}, ackMode = "MANUAL")
    public void receiveMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        log.info("receiveMessage message = {}", message);
        long questionSubmitId = Long.parseLong(message);
        try {
            QuestionSubmit questionSubmit = judgeService.doJudge(questionSubmitId);
            System.out.println(questionSubmit);
            if (questionSubmit == null) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "判题失败。");
            }
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            channel.basicNack(deliveryTag, false, false);
        }
    }
}
