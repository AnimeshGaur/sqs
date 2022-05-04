package com.ns.awssqspoc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.config.annotation.EnableSqs;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Consumer {
    @Autowired
    private QueueMessagingTemplate queueMessagingTemplate;

    //@Value("${cloud.aws.end-point.uri}")
    private String endPoint ="https://sqs.ap-south-1.amazonaws.com/325325683952/PRIMARY_SCORING";


    @Async
    @SqsListener(value = "https://sqs.ap-south-1.amazonaws.com/325325683952/PRIMARY_SCORING", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void receiveMessage(String stringJson) {

        log.error("Message Received using SQS Listner " + stringJson);

    }
}
