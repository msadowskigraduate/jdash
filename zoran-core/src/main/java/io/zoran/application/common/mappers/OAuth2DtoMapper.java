package io.zoran.application.common.mappers;

import io.zoran.domain.impl.ZoranUser;
import io.zoran.domain.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 19.11.2018
 */
@RequiredArgsConstructor
@MapperDefinition(input = OAuth2User.class, output = UserDto.class)
public class OAuth2DtoMapper implements BiMapper<OAuth2User, OAuth2AccessToken, UserDto>{
    private final BiMapper<OAuth2User, OAuth2AccessToken, ZoranUser> oAuth2UserZoranUserMapper;
    private final Mapper<ZoranUser, UserDto> zoranUserUserDtoMapper;

    @Override
    public UserDto map(OAuth2User oAuth2User, OAuth2AccessToken auth2AccessToken) {
        return zoranUserUserDtoMapper.map(oAuth2UserZoranUserMapper.map(oAuth2User, auth2AccessToken));
    }
}