package io.zoran.application.pipelines.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Map;

import static javax.persistence.GenerationType.AUTO;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 23/01/2019.
 */
@Data
@Document
public class PipelineDefinition {
    @Id
    @GeneratedValue(strategy = AUTO)
    private String idDefinition;
    private String name;
    private String idOwner;
    private String idSharingGroup;
    private Map<Integer, PipelineTaskParamMap> orderTaskMap;
}
