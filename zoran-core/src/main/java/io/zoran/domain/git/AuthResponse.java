package io.zoran.domain.git;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 29/12/2018.
 */
@Data
@JsonIgnoreProperties
public class AuthResponse {

    @JsonProperty("access_token")
    private String accessToken;

    private String scope;

    @JsonProperty("token_type")
    private String tokenType;
}
