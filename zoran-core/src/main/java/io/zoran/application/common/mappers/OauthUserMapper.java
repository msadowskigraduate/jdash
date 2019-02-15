package io.zoran.application.common.mappers;

import io.zoran.domain.impl.ZoranUser;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 17/11/2018.
 */
@MapperDefinition(input = OAuth2User.class, output = ZoranUser.class)
public class OauthUserMapper implements BiMapper<OAuth2User, OAuth2AccessToken, ZoranUser> {

    @Override
    public ZoranUser map(OAuth2User oAuth2User, OAuth2AccessToken auth2AccessToken) {
        if(oAuth2User instanceof ZoranUser) {
            return (ZoranUser) oAuth2User;
        }
        return ZoranUser.from(oAuth2User.getAttributes(), oAuth2User.getAuthorities(), AccessTokenMapper.map(auth2AccessToken));
    }
}
