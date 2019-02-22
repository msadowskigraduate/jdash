package io.zoran.application.template;

import io.zoran.application.template.context.TemplateClassContext;

import java.nio.file.Path;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 18.02.2019
 */
public interface TemplateProcessor {
    void compile(TemplateClassContext tc);
    boolean canProcess(Path tc);
}
