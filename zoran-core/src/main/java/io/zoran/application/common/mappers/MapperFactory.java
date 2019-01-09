package io.zoran.application.common.mappers;

import io.zoran.domain.impl.ZoranUser;
import io.zoran.domain.user.UserDto;
import io.zoran.infrastructure.exception.MapperNotFoundException;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 17.11.2018
 */
@Configuration
public class MapperFactory {

    private List<Mapper> mapperList;

    public Mapper getMapper(Class resultType, Class inputType) {
        requireNonNull(this.mapperList);
        for (Mapper m : this.mapperList) {
            @NonNull MapperDefinition a = m.getClass().getAnnotation(MapperDefinition.class);
            if (a.output().equals(resultType) && a.input().equals(inputType)) {
                return m;
            }
        }
        throw new MapperNotFoundException(inputType, resultType);
    }

    @Bean
    Mapper<ZoranUser, UserDto> userMapper() {
        return new UserMapper();
    }

    @Bean
    Mapper<OAuth2User, ZoranUser> oAuth2UserZoranUserMapper() {
        return new OauthUserMapper();
    }

    @Bean
    Mapper<OAuth2User, UserDto> OauthDtoMapper(@Autowired Mapper<OAuth2User, ZoranUser> oAuth2UserZoranUserMapper,
                                               @Autowired Mapper<ZoranUser, UserDto> zoranUserUserDtoMapper) {
        return new OAuth2DtoMapper(oAuth2UserZoranUserMapper, zoranUserUserDtoMapper);
    }

    @Bean
    public DependencyItemToModelMapper dependencyItemToModelMapper() {
        return new DependencyItemToModelMapper();
    }

    @Autowired
    private void setMapperList(List<Mapper> mapperList) {
        this.mapperList = mapperList;
    }
}
