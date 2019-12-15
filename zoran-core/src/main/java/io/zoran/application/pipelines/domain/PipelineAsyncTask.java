package io.zoran.application.pipelines.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static io.zoran.application.pipelines.domain.PipelineProcessingStatus.*;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 26/01/2019.
 */
@Data
@Document
public class PipelineAsyncTask {
    @Id
    private String id;
    private String idClient;
    private LocalDateTime dateStart;
    private PipelineProcessingStatus status;
    private PipelineDefinition definition;
    private Path resultPath;
    private List<String> messages;

    @Builder
    public PipelineAsyncTask(String id, String idClient, PipelineDefinition definition) {
        this.id = id;
        this.idClient = idClient;
        this.dateStart = LocalDateTime.now();
        this.status = QUEUED;
        this.definition = definition;
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

    public PipelineAsyncTask addMessage(String message) {
        if(this.messages == null) {
            this.messages = new ArrayList<>();
        }
        this.messages.add(message);
        return this;
    }
}