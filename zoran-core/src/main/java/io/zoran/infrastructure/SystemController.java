package io.zoran.infrastructure;

import io.zoran.infrastructure.configuration.domain.VersionHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 30/11/2018.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class SystemController {
    private final VersionHolder versionHolder;

    @GetMapping(value = "/build-info", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    VersionHolder getVersion() throws IOException {
        return versionHolder;
    }
}
