package io.zoran.application.template;

import io.zoran.application.template.processors.MustacheTemplateProcessor;
import io.zoran.infrastructure.exception.ZoranTemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 19.02.2019
 */
@Component
@RequiredArgsConstructor
public class TemplateProcessorFactory {
    private final List<TemplateProcessor> processors;

    private Optional<TemplateProcessor> getProcessorForType(Class clazz) {
        if (processors != null && processors.size() > 0) {
            return processors.stream()
                             .filter(x -> x.getClass().isAssignableFrom(clazz))
                             .findFirst();
        }
        throw new ZoranTemplateException("No template processor found!");
    }

    public TemplateProcessor getDefaultProcessor() {
        if (processors != null && processors.size() > 0) {
            return getProcessorForType(MustacheTemplateProcessor.class)
                    .orElseGet(() -> processors.get(0));
        }
        throw new ZoranTemplateException("No template processor found!");
    }

    public TemplateProcessor getForTypeOrDefault(Class clazz) {
        return getProcessorForType(clazz).orElseGet(this::getDefaultProcessor);
    }

    public TemplateProcessor getProcessorForPath(Path p) {
        return processors.stream()
                .filter(x -> x.canProcess(p))
                .findFirst()
                .orElseGet(() -> null);
    }
}