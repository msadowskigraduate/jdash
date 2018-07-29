package io.zoran.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 29/07/2018.
 */
@Slf4j
@EnableScheduling
@EnableFeignClients
@EnableAspectJAutoProxy
@EnableEurekaClient
@EnableHystrix
public class ZoranConfiguration implements InitializingBean{

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info(" Configured!");
    }
}
