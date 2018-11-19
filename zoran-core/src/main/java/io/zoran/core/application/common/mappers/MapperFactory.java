package io.zoran.core.application.common.mappers;

import io.zoran.core.domain.impl.ZoranUser;
import io.zoran.core.domain.user.UserDto;
import io.zoran.core.infrastructure.exception.MapperNotFoundException;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
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
        return new UserMapper(authoritiesMapper());
    }

    @Bean
    Mapper<OAuth2User, ZoranUser> oAuth2UserZoranUserMapper() {
        return new OauthUserMapper();
    }

    @Autowired
    private void setMapperList(List<Mapper> mapperList) {
        this.mapperList = mapperList;
    }
}
