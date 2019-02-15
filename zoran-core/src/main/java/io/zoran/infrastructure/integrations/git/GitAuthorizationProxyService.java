package io.zoran.infrastructure.integrations.git;

import io.zoran.domain.git.AuthResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import static io.zoran.infrastructure.integrations.git.GitConsts.AUTH_URL;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 29/12/2018.
 */
@FeignClient(name = "githubAuth", url = AUTH_URL)
interface GitAuthorizationProxyService {
    @PostMapping(path = "/access_token")
    AuthResponse exchangeAccessForToken(@RequestHeader("Accept") String acceptType, @RequestBody MultiValueMap<String, Object> params);

    @GetMapping(path = "/authorize")
    String authorize(@RequestParam("client_id") String clientId, @RequestParam("scope") String scope);
}
