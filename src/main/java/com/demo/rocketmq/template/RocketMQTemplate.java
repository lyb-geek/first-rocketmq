
package com.demo.rocketmq.template;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RocketMQTemplate {
	
	@Autowired
	DefaultMQProducer rocketmqProduct;
	
	public void send(String topic, String msgStr) {
		try {
			
			Message msg = new Message(topic, msgStr.getBytes());
			
			SendResult result = rocketmqProduct.send(msg);
			
			if (SendStatus.SEND_OK == result.getSendStatus()) {
				System.out.println("消息发送成功-->topic:" + topic + "|msg:" + msgStr);
			}
		} catch (BeansException e) {
			e.printStackTrace();
		} catch (MQClientException e) {
			e.printStackTrace();
		} catch (RemotingException e) {
			e.printStackTrace();
		} catch (MQBrokerException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
}
