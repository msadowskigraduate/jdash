package io.zoran.infrastructure.integrations.git;

import lombok.experimental.UtilityClass;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 23/12/2018.
 */
@UtilityClass
public class GitConsts {
    static final String GIT_URL = "https://api.github.com";
    static final String AUTH_URL = "https://github.com/login/oauth";
    static String CLIENT_ID = "client_id";
    static String CLIENT_SECRET = "client_secret";
    static String CODE = "code";
    static String SCOPE_DELIMITER = " ";

    static final String scheme = "https";
    static final int port = 443;
    static final String gitHubApiHost = "api.github.com";
    static final String gitHubHost = "github.com";
}
