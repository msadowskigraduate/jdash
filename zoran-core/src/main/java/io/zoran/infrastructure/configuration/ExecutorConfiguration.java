package io.zoran.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 26/01/2019.
 */
@EnableAsync
@Configuration
class ExecutorConfiguration {

    @Bean(name = "pipelineProcessorExecutor")
    public Executor pipelineThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(30);
        executor.setThreadNamePrefix("pipelineProcessor-");
        executor.initialize();
        return executor;
    }

}
