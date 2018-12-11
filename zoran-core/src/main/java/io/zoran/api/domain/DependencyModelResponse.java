package io.zoran.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Value object containing information that populates frontend application.
 *
 * Contains list of dependencies for given language and version.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor(staticName = "of")
public class DependencyModelResponse {
    private List<ResourceDependencyMetadata> dependencies;
}
