package io.zoran.infrastructure.integrations.git;

import io.zoran.domain.git.License;
import io.zoran.domain.git.NewRepositoryRequest;
import io.zoran.domain.git.RepositoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 23/12/2018.
 */
@FeignClient(name = "github", url = GitConsts.GIT_URL)
interface GitProxyService {

    @GetMapping("/licenses")
    List<License> getLicenses(@RequestParam("client_id") String clientId, @RequestParam("client_secret") String clientSecret);

    @GetMapping("/user")
    String getAuthResponse(@RequestParam("client_id") String clientId, @RequestParam("client_secret") String clientSecret);

    @GetMapping("/user")
    String getAuthResponse(@RequestHeader("Authorization") String bearerToken);

    @PostMapping("/user/repos")
    RepositoryResponse createNewRepository(NewRepositoryRequest repositoryRequest);
}
