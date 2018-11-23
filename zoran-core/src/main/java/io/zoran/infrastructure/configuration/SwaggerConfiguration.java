package io.zoran.infrastructure.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 23.11.2018
 */
@Slf4j
@EnableSwagger2
class SwaggerConfiguration implements InitializingBean {

    @Override
    public void afterPropertiesSet() {
      log.info("Swagger enabled!");
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Zoran IO",
                "API Overview",
                "API TOS",
                null,
                new Contact("Micha", "",""),
                "MIT",
                null,
                Collections.emptyList());
    }
}
