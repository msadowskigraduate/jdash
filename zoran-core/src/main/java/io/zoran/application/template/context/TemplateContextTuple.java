package io.zoran.application.template.context;

import lombok.Data;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 19.02.2019
 */
@Data(staticConstructor = "of")
public class TemplateContextTuple {
    private final String key;
    private final String value;
}
