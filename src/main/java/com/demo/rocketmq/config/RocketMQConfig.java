
package com.demo.rocketmq.config;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.demo.rocketmq.listener.DefaultMessageListener;

@Configuration
@ConfigurationProperties(prefix = "spring.rocketmq")
public class RocketMQConfig {
	
	private String					namesrvAddr;
	
	private String					producerGroup;
	
	private String					consumerGroup;
	
	private String					consumeBatchSize;
	
	private String					topics;
	
	@Autowired
	private DefaultMessageListener	defaultMessageListener;
	
	@Bean
	public DefaultMQProducer defaultMQProducer() {
		System.out.println(namesrvAddr);
		DefaultMQProducer producer = new DefaultMQProducer();
		producer.setProducerGroup(producerGroup);
		producer.setNamesrvAddr(namesrvAddr);
		producer.setVipChannelEnabled(false);
		return producer;
	}
	
	@Bean
	public DefaultMQPushConsumer defaultMQPushConsumer() {
		
		DefaultMQPushConsumer consunmer = new DefaultMQPushConsumer();
		
		consunmer.setConsumerGroup(consumerGroup);
		consunmer.setConsumeMessageBatchMaxSize(Integer.parseInt(consumeBatchSize));
		consunmer.setMessageListener(defaultMessageListener);
		consunmer.setVipChannelEnabled(false);
		
		Map<String, String> subscription = new HashMap<String, String>();
		
		StringTokenizer strToke = new StringTokenizer(topics, ",");
		while (strToke.hasMoreElements()) {
			subscription.put(strToke.nextToken(), "*");
		}
		consunmer.setSubscription(subscription);
		
		return consunmer;
	}
	
	public String getNamesrvAddr() {
		return namesrvAddr;
	}
	
	public void setNamesrvAddr(String namesrvAddr) {
		this.namesrvAddr = namesrvAddr;
	}
	
	public String getProducerGroup() {
		return producerGroup;
	}
	
	public void setProducerGroup(String producerGroup) {
		this.producerGroup = producerGroup;
	}
	
	public String getConsumerGroup() {
		return consumerGroup;
	}
	
	public void setConsumerGroup(String consumerGroup) {
		this.consumerGroup = consumerGroup;
	}
	
	public String getConsumeBatchSize() {
		return consumeBatchSize;
	}
	
	public void setConsumeBatchSize(String consumeBatchSize) {
		this.consumeBatchSize = consumeBatchSize;
	}
	
	public String getTopics() {
		return topics;
	}
	
	public void setTopics(String topics) {
		this.topics = topics;
	}
	
}
