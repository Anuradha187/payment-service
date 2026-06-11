package com.payment.config;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {

	@Bean
	public BlockingQueue<Long> paymentQueue(){
		
		return new LinkedBlockingQueue<>();
	}
}
