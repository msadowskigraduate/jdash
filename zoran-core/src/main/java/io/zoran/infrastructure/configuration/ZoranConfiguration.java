package io.zoran.infrastructure.configuration;

import io.zoran.core.infrastructure.configuration.ZoranCoreConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 29/07/2018.
 */
@Slf4j
@EnableAspectJAutoProxy
@Import({ZoranCoreConfiguration.class, SwaggerConfiguration.class})
public class ZoranConfiguration implements InitializingBean{

    @Override
    public void afterPropertiesSet() {
        log.info("Zoran fully Configured!");
    }
}
