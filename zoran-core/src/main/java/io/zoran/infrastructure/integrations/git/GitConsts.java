package io.zoran.infrastructure.integrations.git;

import lombok.experimental.UtilityClass;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 23/12/2018.
 */
@UtilityClass
public class GitConsts {
    static final String SEPARATOR = "://";
    static final String GIT_HUB_API_HOST = "api.github.com";
    static final String GIT_HUB_HOST = "github.com";
    static final String SCHEME = "https";
    static final int PORT = 443;
    static final String AUTH_URL = SCHEME + SEPARATOR + GIT_HUB_HOST + "/login/oauth";

    static String CLIENT_ID = "client_id";
    static String CLIENT_SECRET = "client_secret";
    static String CODE = "code";
    static String SCOPE_DELIMITER = " ";
}
