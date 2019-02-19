package io.zoran.infrastructure.integrations.git;

import io.zoran.domain.git.AuthResponse;
import io.zoran.infrastructure.configuration.domain.GitHub;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static io.zoran.infrastructure.integrations.git.GitConsts.SCOPE_DELIMITER;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 09/12/2018.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthAuthorizationService {
    private final GitAuthorizationProxyService service;
    private final GitHub gitHub;

    public AuthResponse getAccessTokenForGit(String... scopes) {
        if(scopes == null || 1 > scopes.length) {
            scopes = gitHub.getScope().split(",");
        }
        String normalizedScopes = StringUtils.join(scopes, SCOPE_DELIMITER);
        String authorizationCode = service.authorize(gitHub.getClient_id(), normalizedScopes);
        return exchangeAccessCodeForToken(authorizationCode);
    }

    private AuthResponse exchangeAccessCodeForToken(String accessToken) {
        MultiValueMap<String, Object> paramMap = prepareParameters(accessToken);
        AuthResponse result = service.exchangeAccessForToken(MediaType.APPLICATION_JSON_UTF8_VALUE, paramMap);
        log.info(result.toString());
        return result;
    }

    private MultiValueMap<String, Object> prepareParameters(String code) {
        MultiValueMap<String, Object> map = new LinkedMultiValueMap();
        map.add(GitConsts.CLIENT_ID, gitHub.getClient_id());
        map.add(GitConsts.CLIENT_SECRET, gitHub.getClient_secret());
        map.add(GitConsts.CODE, code);
        return map;
    }
}
