package io.zoran.domain.initializr;

import io.spring.initializr.generator.project.ResolvedProjectDescription;

import java.nio.file.Path;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 21.02.2019
 */
public class ProjectGenerationResult {
    private final ResolvedProjectDescription projectDescription;

    private final Path rootDirectory;

    public ProjectGenerationResult(ResolvedProjectDescription projectDescription,
                            Path rootDirectory) {
        this.projectDescription = projectDescription;
        this.rootDirectory = rootDirectory;
    }

    /**
     * Return the {@link ResolvedProjectDescription} that was used to generate the
     * project.
     * @return the project description
     */
    public ResolvedProjectDescription getProjectDescription() {
        return this.projectDescription;
    }

    /**
     * Return the root directory.
     * @return the root directory
     * @see ResolvedProjectDescription#getBaseDirectory()
     */
    public Path getRootDirectory() {
        return this.rootDirectory;
    }
}
