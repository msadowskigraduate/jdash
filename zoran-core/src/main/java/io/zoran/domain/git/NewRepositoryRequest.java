package io.zoran.domain.git;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 29/12/2018.
 */
@Deprecated
@Data(staticConstructor = "of")
@AllArgsConstructor(staticName = "of")
public class NewRepositoryRequest {
    private String name;
    private String description;
    private String homepage;

    @JsonProperty("private")
    private Boolean licensePrivate;
}
