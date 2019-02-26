package io.zoran.application.common;

import io.zoran.infrastructure.configuration.ProductionOnly;
import io.zoran.infrastructure.configuration.domain.Zoran;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.core.annotation.Order;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 25.02.2019
 */
@ProductionOnly
@Slf4j
@Order(-1)
@RequiredArgsConstructor
class LocalCleanupService {
    private final Zoran properties;

    @PostConstruct
    void cleanUp() {
        log.info("Starting cleanup service...");

        Path[] paths = {Paths.get(properties.getStorage().getPath()), Paths.get(properties.getStorage().getLocal())};

        Stream.of(paths)
              .forEach(x -> {
                  try {
                      FileUtils.deleteDirectory(x.toFile());
                  } catch (IOException e) {
                      log.error("Cleanup service failed! \n " + e.getMessage());
                  }
              });

        log.info("Clean up finished!");
    }
}
