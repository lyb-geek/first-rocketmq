
package com.demo.rocketmq.listener;

import java.util.List;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class DefaultMessageListener implements MessageListenerConcurrently {
	
	@Override
	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
		for (MessageExt messageExt : msgs) {
			
			String topic = messageExt.getTopic();
			
			String msg = new String(messageExt.getBody());
			
			System.out.println("成功订阅到msg为：" + msg + ";topic为：" + topic + "的主题内容");
			
			if (!StringUtils.hasText(topic)) {
				// 舍弃没有topic的消息
				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
			}
			
		}
		
		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}
	
}
