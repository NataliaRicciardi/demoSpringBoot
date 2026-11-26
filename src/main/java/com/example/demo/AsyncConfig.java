package com.example.demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ThreadPoolProperties.class)
public class AsyncConfig {
	@Bean(destroyMethod = "shutdown")
	ExecutorService jobExecutor(ThreadPoolProperties props) {
		return new ThreadPoolExecutor(
				props.corePoolSize(), props.maxPoolSize(), 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(props.queueCapacity()),
				new ThreadPoolExecutor.AbortPolicy()
		);
		
		// Experiment 1, fixed pool, small bounded queue
		/*
		return new ThreadPoolExecutor(
				3, 3, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(3),
				new ThreadPoolExecutor.AbortPolicy()
		);
		*/
		// submitted 15 requests, rejected 9 and threw RejectedExecutionException for full tasks
		
		
		// Experiment 2, rejection policies
		
		// CallerRunsPolicy
		// submitted 15 requests, completes all, but when the queue is full, the caller completes the task so it waits to submit the next one
		
		// DiscardPolicy
		// submitted 15 requests, no exceptions triggered and Post requests marked successful, but only 6 tasks completed
		
		// DiscardOldestPolicy
		// submitted 15 requests, no exceptions triggered and post requests marked successful, but jobs 4, 5, 6, 16, 17, 18 only completed
		
	}
}
