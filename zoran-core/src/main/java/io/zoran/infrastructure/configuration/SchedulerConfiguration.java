package io.zoran.infrastructure.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 09/08/2018.
 */

@Slf4j
@Configuration
@EnableScheduling
class SchedulerConfiguration implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("Scheduler initialized!");
    }
}
