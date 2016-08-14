package com.disney.studios;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Bootstraps the Spring Boot com.disney.studios.Application
 *
 * Created by hzineddin
 */
@SpringBootApplication
public class Application {

    @Value("${threadpool.coresize}")
    private int coreSize;

    @Value("${threadpool.maxsize}")
    private int maxSize;

    @Value("${threadpool.queueCapacity}")
    private int queueCapacity;

    @Bean
    ThreadPoolTaskExecutor mvcTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("dogApp");
        executor.setCorePoolSize(coreSize);
        executor.setMaxPoolSize(maxSize);
        executor.setThreadGroupName("dogAppGroup");
        executor.setQueueCapacity(queueCapacity);
        return executor;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
