package io.zoran.core.domain.resource.dto;

import lombok.RequiredArgsConstructor;
import lombok.Value;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 22.11.2018
 */
@Value
@RequiredArgsConstructor
public class ProjectResourceDto {
    private String resourceIdentifier;
    private String projectName;
    private String resourceVisibility;
    private String author;
    private String type;
    //TODO maybe some type of project? Author,
}
