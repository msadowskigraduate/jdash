package io.zoran.domain.initializr;

import io.spring.initializr.generator.project.MutableProjectDescription;

import java.nio.file.Path;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 21.02.2019
 */
public class ProjectGenerationResult {
    private final MutableProjectDescription projectDescription;

    private final Path rootDirectory;

    public ProjectGenerationResult(MutableProjectDescription projectDescription,
                            Path rootDirectory) {
        this.projectDescription = projectDescription;
        this.rootDirectory = rootDirectory;
    }

    /**
     * Return the {@link MutableProjectDescription} that was used to generate the
     * project.
     * @return the project description
     */
    public MutableProjectDescription getProjectDescription() {
        return this.projectDescription;
    }

    /**
     * Return the root directory.
     * @return the root directory
     * @see MutableProjectDescription#getBaseDirectory()
     */
    public Path getRootDirectory() {
        return this.rootDirectory;
    }
}
