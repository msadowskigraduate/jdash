package io.zoran.core.domain.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.List;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 17.11.2018
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@RequiredArgsConstructor(staticName = "of")
public class UserDto {
    @JsonProperty
    private final String login;
    @JsonProperty
    private final String name;
    @JsonProperty
    private final List<String> authorities;
}
