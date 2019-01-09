package io.zoran.domain.resource.project;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 20.11.2018
 */
@Data
@Builder
@Document
public class ProjectDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String projectDetailsId;
    private String projectName;
    private String name;
    private String lead;
    private String description;
    private String[] tags;
    private String gitUrl;
    private String projectLanguage;
    private String groupId;
    private String artifactId;
    private String version;
}
