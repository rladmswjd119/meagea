package project.async;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "fileThread")
    public Executor imageFileThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor thread = new ThreadPoolTaskExecutor();
        thread.setCorePoolSize(4);
        thread.setMaxPoolSize(10);
        thread.setQueueCapacity(100);
        thread.setThreadNamePrefix("animalFile-");

        return thread;
    }
}
