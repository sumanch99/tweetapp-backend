package com.tweetapp.handler;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Suman Chakraborty
 * December 2022
 */
@Service
@Slf4j
public class KafkaConsumer {
	
	private static final String KAFKA_TOPIC = "ForgotPassword";
	private static final String GROUP = "twitter-group";
	
	/*
	 * Message Consumer for Kafka
	 * */
	@KafkaListener(topics = KAFKA_TOPIC, groupId = GROUP)
	public void listenToCodeDecodeKafkaTopic(String messageReceived) {
		log.info("Message received, The user trying to change password:" + messageReceived);
	}
}
