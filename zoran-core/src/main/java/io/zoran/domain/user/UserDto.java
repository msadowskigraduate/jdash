package io.zoran.domain.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 17.11.2018
 */
@Value(staticConstructor = "of")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    @JsonProperty
    private final String login;
    @JsonProperty
    private final String name;
    @JsonProperty
    private final String state;
    @JsonProperty
    private final String email;
    @JsonProperty
    private final String avatarUrl;
    @JsonProperty
    private final String repoUrl;
    @JsonProperty
    private final String htmlUrl;
    @JsonProperty
    private final String userType;
    @JsonProperty
    private final String lastLogin;
}
