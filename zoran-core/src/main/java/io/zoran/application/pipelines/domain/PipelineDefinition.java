package io.zoran.application.pipelines.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static javax.persistence.GenerationType.AUTO;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 23/01/2019.
 */
@Data
@Document
@Builder
public class PipelineDefinition {
    @Id
    @GeneratedValue(strategy = AUTO)
    private String id;
    private String name;
    private String idOwner;
    private String idSharingGroup;
    private Integer noOfRuns;
    private LocalDateTime lastRun;
    private PipelineStatus status;
    private String resourceId;
    @Builder.Default private Map<Integer, PipelineTaskParamMap> orderTaskMap = new HashMap<>();

    public void incrementBuildNo() {
        this.noOfRuns = noOfRuns++;
    }
}
