package io.zoran.application.common.mappers;

import io.zoran.domain.impl.ZoranUser;
import io.zoran.domain.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 19.11.2018
 */
@RequiredArgsConstructor
@MapperDefinition(input = OAuth2User.class, output = UserDto.class)
public class OAuth2DtoMapper implements Mapper<OAuth2User, UserDto>{
    private final Mapper<OAuth2User, ZoranUser> oAuth2UserZoranUserMapper;
    private final Mapper<ZoranUser, UserDto> zoranUserUserDtoMapper;

    @Override
    public UserDto map(OAuth2User oAuth2User) {
        return zoranUserUserDtoMapper.map(oAuth2UserZoranUserMapper.map(oAuth2User));
    }
}
