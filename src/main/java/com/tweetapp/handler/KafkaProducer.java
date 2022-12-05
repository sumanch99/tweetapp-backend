package com.tweetapp.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * @author Suman Chakraborty
 * December 2022
 */
@Service
public class KafkaProducer {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	private static final String KAFKA_TOPIC = "ForgotPassword";
	
	/*
	 * Method to send Message to consumer
	 * .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
	 * .\bin\windows\kafka-server-start.bat .\config\server.properties 
	 *  
	 * */
	public void sendMessage(String message) {
		kafkaTemplate.send(KAFKA_TOPIC, message);
	}
	
}