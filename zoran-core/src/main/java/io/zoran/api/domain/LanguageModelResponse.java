package io.zoran.api.domain;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.List;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 01/01/2019.
 */
@Data(staticConstructor = "of")
@JsonIgnoreProperties(ignoreUnknown = true)
public class LanguageModelResponse {
    private final List<String> supportedLanguages;
}
