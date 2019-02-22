package io.zoran.application.common.mappers;

import io.zoran.domain.user.AccessToken;
import lombok.experimental.UtilityClass;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 15.02.2019
 */
@UtilityClass
public class AccessTokenMapper {

    public static AccessToken map(OAuth2AccessToken token) {
        return new AccessToken(token.getTokenType(), token.getTokenValue(), token.getIssuedAt(), token.getExpiresAt());
    }
}
