package io.zoran.core.application.common.mappers;

import io.zoran.core.domain.impl.ZoranUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 17/11/2018.
 */
@MapperDefinition(input = OAuth2User.class, output = ZoranUser.class)
public class OauthUserMapper implements Mapper<OAuth2User,ZoranUser> {

    @Override
    public ZoranUser map(OAuth2User oAuth2User) {
        return ZoranUser.from(oAuth2User.getAttributes(), oAuth2User.getAuthorities());
    }
}
