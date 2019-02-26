package io.zoran.application.project;

import io.zoran.domain.resource.Resource;

import java.io.IOException;
import java.nio.file.Path;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 26.02.2019
 */
public interface ProjectRetrievalService {

    Path getLocalPath(Resource resource) throws IOException;
}
