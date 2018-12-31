package io.zoran.infrastructure.integrations;

import io.zoran.domain.git.LicenseResponse;
import io.zoran.domain.git.NewRepositoryRequest;
import io.zoran.domain.git.RepositoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 23/12/2018.
 */
@FeignClient(name = "github", url = GitConsts.GIT_URL)
public interface GitProxyService {

    @GetMapping("/licenses")
    LicenseResponse getLicenses();

    @GetMapping("/user")
    String getAuthResponse(@RequestHeader("Authorization") String bearerToken);

    @PostMapping("/user/repos")
    RepositoryResponse createNewRepository(NewRepositoryRequest repositoryRequest);
}
