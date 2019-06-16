package io.zoran.application.template.processors;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import io.zoran.application.template.Processor;
import io.zoran.application.template.TemplateProcessor;
import io.zoran.application.template.context.TemplateClassContext;
import io.zoran.application.template.resolvers.FileNameResolver;
import io.zoran.application.template.resolvers.OutputFileResolver;
import io.zoran.infrastructure.exception.ZoranTemplateException;
import org.apache.commons.lang.StringUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;

import static io.zoran.application.template.TemplateClassConst.FILE_NAME;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 18.02.2019
 */
@Processor
public class MustacheTemplateProcessor implements TemplateProcessor {
    private static final String MUSTACHE_EXTENSION = ".mustache";

    @Override
    public void compile(TemplateClassContext templateContext) {
            try {
                MustacheFactory factory = new DefaultMustacheFactory();
                String outputFileName = getFileName(templateContext.getTemplateFile());
                templateContext.registerInContext(FILE_NAME, FileNameResolver.resolve(outputFileName));
                templateContext.setOutputFileName(outputFileName);
                Writer writer = new FileWriter(OutputFileResolver.resolveFileName(templateContext));
                Mustache mustache = factory.compile(templateContext.getTemplateReader(), templateContext.getTemplateFile().toString());
                mustache.execute(writer, templateContext.getManifestContext());
                writer.flush();
            } catch (IOException e) {
                throw new ZoranTemplateException(e.getMessage());
            }
    }

    @Override
    public boolean canProcess(Path tc) {
//        return tc.toFile().isFile() && tc.getFileName().endsWith(MUSTACHE_EXTENSION);
        return tc.toString().endsWith(MUSTACHE_EXTENSION);
    }

    private String getFileName(Path templatePath) {
        return StringUtils.removeEnd(templatePath.getFileName().toString(), MUSTACHE_EXTENSION);
    }
}
