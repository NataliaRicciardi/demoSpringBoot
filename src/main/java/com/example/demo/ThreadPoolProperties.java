package com.example.demo;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.thread-pool")
public record ThreadPoolProperties(int corePoolSize, int maxPoolSize, int queueCapacity) {}