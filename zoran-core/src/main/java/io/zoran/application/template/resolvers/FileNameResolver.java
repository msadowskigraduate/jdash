package io.zoran.application.template.resolvers;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang.StringUtils;

import java.nio.file.Path;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 19.02.2019
 */
@UtilityClass
public class FileNameResolver {
    private static final String[] SUPPORTED_FILE_EXTENSIONS = {".kt", ".java", ".groovy"};

    public String resolve(String fileName) {
        for(String extension : SUPPORTED_FILE_EXTENSIONS) {
            fileName = StringUtils.removeEnd(fileName, extension);
        }

        return fileName.trim();
    }

    public static String resolveLanguageFromTemplate(Path file) {
        String extension = file.getFileName().toString();

        if(extension.contains(".kt."))
            return "kotlin";

        if(extension.contains(".java."))
            return "java";

        if(extension.contains(".groovy."))
            return "groovy";

        return "java";
    }
}
