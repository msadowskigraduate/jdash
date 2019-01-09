package io.zoran.infrastructure.configuration.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 09.01.2019
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class GeneratorProperties {
    private String defaultLicense;
}
