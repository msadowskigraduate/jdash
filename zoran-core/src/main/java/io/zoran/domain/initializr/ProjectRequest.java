package io.zoran.domain.initializr;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 21.02.2019
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectRequest {
    private List<String> style = new ArrayList<>();

    private List<String> dependencies = new ArrayList<>();

    private String name;

    private String type;

    private String description;

    private String groupId;

    private String artifactId;

    private String version;

    private String bootVersion;

    private String packaging;

    private String applicationName;

    private String language;

    private String packageName;

    private String javaVersion;

    // The base directory to create in the archive - no baseDir by default
    private String baseDir;
}
