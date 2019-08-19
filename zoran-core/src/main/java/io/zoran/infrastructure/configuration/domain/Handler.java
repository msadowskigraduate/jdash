package io.zoran.infrastructure.configuration.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 07/02/2019.
 */

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(value = "pipeline.model")
public class Handler {
    private String name;
    private String clazz;
    private String description;
    private List<Parameters> parameters;

    @Data
    @ConfigurationProperties(value = "pipeline.model.parameters")
    static class Parameters {
        private String name;
        private String value;
    }
}
