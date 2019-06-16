package io.zoran.domain.manifest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 18.02.2019
 *
 * Represents a single templated class.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Template {
    private String name;
    private String filename;
    private Location preferredLocation;
    private List<Context> context;
    private Map<String, Object> additionalProperties;
}
