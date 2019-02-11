package io.zoran.application.pipelines.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.nio.file.Path;
import java.time.LocalDateTime;

import static io.zoran.application.pipelines.domain.PipelineProcessingStatus.*;
import static javax.persistence.GenerationType.AUTO;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 26/01/2019.
 */
@Data
@Document
public class PipelineAsyncTask {
    @Id
    @GeneratedValue(strategy = AUTO)
    private String idTask;
    private String idClient;
    private LocalDateTime dateStart;
    private PipelineProcessingStatus status;
    private String definitionId;
    private Path resultPath;

    @Builder
    public PipelineAsyncTask(String idClient, String definition) {
        this.idClient = idClient;
        this.dateStart = LocalDateTime.now();
        this.status = QUEUED;
        this.definitionId = definition;
    }

    private PipelineProcessingStatus with(PipelineProcessingStatus status) {
        this.setStatus(status);
        return this.status;
    }

    public PipelineAsyncTask failTask() {
        this.with(FAILED);
        return this;
    }

    public PipelineAsyncTask startTask() {
        this.with(IN_PROGRESS);
        return this;
    }

    public PipelineAsyncTask finishTask() {
        this.with(FINISHED);
        return this;
    }
}