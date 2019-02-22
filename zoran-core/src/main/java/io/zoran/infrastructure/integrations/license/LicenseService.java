package io.zoran.infrastructure.integrations.license;

import io.zoran.domain.git.License;
import io.zoran.infrastructure.configuration.domain.Zoran;
import io.zoran.infrastructure.integrations.git.GitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 09.01.2019
 */
@Service
@RequiredArgsConstructor
public class LicenseService {
    private final LicenseRepository repository;
    private final GitService gitService;
    private final Zoran zoran;

    @PostConstruct
    void checkAndPopulate() {
        if(repository.count() < 1) {
            repository.saveAll(gitService.getLicenses());
        }
    }

    public List<License> getAll() {
        checkAndPopulate();
        return repository.findAll();
    }

    public Optional<License> getLicense(String key) {
        return repository.findByKey(key);
    }

    public License getDefaultLicense() {
        return repository.findByKey(zoran.getGenerator().getDefaultLicense()).get();
    }

    public License getOrDefault(String key) {
        return getLicense(key).orElseGet(this::getDefaultLicense);
    }
}
