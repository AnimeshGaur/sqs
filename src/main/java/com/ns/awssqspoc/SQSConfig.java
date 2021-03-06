package com.ns.awssqspoc;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.config.SimpleMessageListenerContainerFactory;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class SQSConfig {


    @Bean
    public QueueMessagingTemplate queueMessagingTemplate() {
        return new QueueMessagingTemplate(amazonSQSAsync());
    }

    public AmazonSQSAsync amazonSQSAsync() {

        AmazonSQSAsyncClientBuilder amazonSQSAsyncClientBuilder = AmazonSQSAsyncClientBuilder.standard();
        AmazonSQSAsync amazonSQSAsync = null;
        try{
            amazonSQSAsyncClientBuilder.withRegion(Regions.AP_SOUTH_1);
            AWSStaticCredentialsProvider basicAWSCredentials = new AWSStaticCredentialsProvider(new DefaultAWSCredentialsProviderChain().getCredentials());
            amazonSQSAsyncClientBuilder.withCredentials(new AWSStaticCredentialsProvider((AWSCredentials) basicAWSCredentials));
            amazonSQSAsync = amazonSQSAsyncClientBuilder.build();
        }catch (Exception e){
            amazonSQSAsync = AmazonSQSAsyncClientBuilder.defaultClient();
        }

        return amazonSQSAsync;

    }

    @Bean
    public SimpleMessageListenerContainerFactory simpleMessageListenerContainerFactory(AmazonSQSAsync amazonSqs) {
        SimpleMessageListenerContainerFactory factory = new SimpleMessageListenerContainerFactory();
        factory.setAmazonSqs(amazonSqs);
        factory.setAutoStartup(true);
        factory.setMaxNumberOfMessages(10);
        factory.setWaitTimeOut(10);
        factory.setBackOffTime(Long.valueOf(60000));
        return factory;
    }


}