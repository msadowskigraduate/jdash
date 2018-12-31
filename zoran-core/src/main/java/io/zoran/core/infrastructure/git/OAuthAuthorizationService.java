package io.zoran.core.infrastructure.git;

import io.zoran.domain.git.AuthResponse;
import io.zoran.infrastructure.configuration.domain.GitHub;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 09/12/2018.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthAuthorizationService {
    private static String CLIENT_ID = "client_id";
    private static String CLIENT_SECRET = "client_secret";
    private static String CODE = "code";

    private final GitAuthorizationProxyService service;
    private final GitHub gitHub;

    public String gitAuthorizeAppRedirect() {
        return getAuthUrl();
    }

    public void exchangeAccessCodeForToken(String accessToken) {
        MultiValueMap<String, Object> paramMap = prepareParameters(accessToken);
        AuthResponse result = service.exchangeAccessForToken(MediaType.APPLICATION_JSON_UTF8_VALUE, paramMap);
        log.info(result.toString());
    }

    private MultiValueMap<String, Object> prepareParameters(String code) {
        MultiValueMap<String, Object> map = new LinkedMultiValueMap();
        map.add(CLIENT_ID, gitHub.getClient_id());
        map.add(CLIENT_SECRET, gitHub.getClient_secret());
        map.add(CODE, code);
        return map;
    }

    private String getAuthUrl() {
        return "https://github.com/login/oauth/authorize?client_id=" + gitHub.getClient_id() + "&scope="
                + gitHub.getScopes().replace(" ", "%20");
    }
}
