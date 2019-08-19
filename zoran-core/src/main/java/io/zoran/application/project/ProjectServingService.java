package io.zoran.application.project;

import io.zoran.domain.manifest.ResourceType;
import io.zoran.domain.resource.Resource;
import lombok.RequiredArgsConstructor;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.ZipFileSet;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 26.02.2019
 */
@Service
@RequiredArgsConstructor
public class ProjectServingService {

    private final ProjectRetrievalService service;

    public byte[] getArchived(Resource resource) throws IOException {
        Path originalFile = service.getLocalPath(resource);
        Path destinationFile = Paths.get(generateFileName(resource.getName(), "zip"));
        String wrapperScript = getWrapperScript(resource);
        getZip(originalFile.toFile(), wrapperScript, destinationFile.toFile()).execute();
        return StreamUtils.copyToByteArray(new FileInputStream(destinationFile.toFile()));
    }

    private Zip getZip(File dir, String wrapperScript, File destination) {
        Zip zip = new Zip();
        zip.setProject(new Project());
        zip.setDefaultexcludes(false);
        ZipFileSet set = new ZipFileSet();
        set.setDir(dir);
        set.setFileMode("755");
        set.setIncludes(wrapperScript);
        set.setDefaultexcludes(false);
        zip.addFileset(set);
        set = new ZipFileSet();
        set.setDir(dir);
        set.setIncludes("**,");
        set.setExcludes(wrapperScript);
        set.setDefaultexcludes(false);
        zip.addFileset(set);
        zip.setDestFile(destination);
        return zip;
    }

    private String generateFileName(String pathName, String extension) {
        String tmp = pathName.replaceAll(" ", "_");
        try {
            return URLEncoder.encode(tmp, "UTF-8") + "." + extension;
        }
        catch (UnsupportedEncodingException ex) {
            throw new IllegalStateException("Cannot encode URL", ex);
        }
    }

    private static String getWrapperScript(Resource description) {
        ResourceType buildSystem = description.getResourceType();
        String script;

        switch(buildSystem) {
            case MAVEN_POM: script = "mvnw"; break;
            case MAVEN_PROJECT: script = "mvnw"; break;
            case GRADLE_CONFIG: script = "gradlew"; break;
            case GRADLE_PROJECT: script = "gradlew"; break;
            default: script = "mvnw"; break;
        }
        return script;
    }
}
