package io.zoran.application.template.context;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static io.zoran.application.template.TemplateClassConst.PACKAGE_NAME;
import static io.zoran.application.template.TemplateClassConst.PROJECT_NAME;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 18.02.2019
 * <p>
 * Context per template class.
 */
@Getter
public class TemplateClassContext {
    private final String outputPath;
    private final Path templateFile;
    private final Map<String, String> manifestContext;

    @Setter
    private String outputFileName;

    @Builder
    public TemplateClassContext(Path templateFile, String packageName, String projectName,
                                String outputPath, TemplateContextTuple... additional) {
        this.manifestContext = new HashMap<>();
        this.manifestContext.put(PACKAGE_NAME, packageName);
        this.manifestContext.put(PROJECT_NAME, projectName);
        this.outputPath = outputPath;
        this.templateFile = templateFile;

        if (additional != null) {
            Stream.of(additional)
                  .forEach(x -> manifestContext.put(x.getKey(), x.getValue()));
        }
    }

    public void registerInContext(@NonNull String key, @NonNull String value) {
        this.manifestContext.put(key, value);
    }

    public void registerInContext(@NonNull TemplateContextTuple entry) {
        this.manifestContext.put(entry.getKey(), entry.getValue());
    }

    public Reader getTemplateReader() throws FileNotFoundException {
        return new FileReader(templateFile.toFile());
    }
}