package io.zoran.infrastructure.configuration.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 03/02/2019.
 */
@Data
@Component
@ConfigurationProperties(value = "pipeline")
public class PipelineMetadataModel {

    private List<Handler> model;

    @Nullable
    public Handler getByClass(String clazz) {
        return this.model.stream()
                .filter(x -> x.getClazz().equals(clazz))
                .findFirst()
                .orElse(null);
    }
}
