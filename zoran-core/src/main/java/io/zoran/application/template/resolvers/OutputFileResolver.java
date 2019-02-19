package io.zoran.application.template.resolvers;

import io.zoran.application.template.context.TemplateClassContext;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 19.02.2019
 */
@Slf4j
@UtilityClass
public class OutputFileResolver {

    public File resolveFileName(TemplateClassContext ctx) throws IOException {
        Path p = Paths.get(ctx.getOutputPath() + "/" + ctx.getOutputFileName());
        p.toFile().getParentFile().mkdirs();
        return Files.createFile(p).toFile();
    }
}