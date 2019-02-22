package io.zoran.infrastructure.services;

import lombok.experimental.UtilityClass;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 29/12/2018.
 */
@UtilityClass
public class TokenAppender {

    public String getRequestHeaderToken(String tokenValue) {
        if(tokenValue == null) {
            tokenValue = "";
        }
        return "token " + tokenValue;
    }

    public String getRequestHeaderToken(OAuth2AccessToken tokenValue) {
        return getRequestHeaderToken(tokenValue.getTokenValue());
    }
}
