package io.zoran.domain.manifest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 18.02.2019
 *
 * Context contains data values for templates.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Context {
    private String name;
    private String value;
    private Map<String, Object> additionalProperties;
}
