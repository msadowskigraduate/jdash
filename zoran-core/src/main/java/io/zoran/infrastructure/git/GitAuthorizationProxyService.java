package io.zoran.infrastructure.git;

import io.zoran.domain.git.AuthResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import static io.zoran.infrastructure.integrations.GitConsts.AUTH_URL;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 29/12/2018.
 */
@FeignClient(name = "githubAuth", url = AUTH_URL)
interface GitAuthorizationProxyService {
    @PostMapping(path = "/access_token")
    AuthResponse exchangeAccessForToken(@RequestHeader("Accept") String acceptType, @RequestBody MultiValueMap<String, Object> params);
}
