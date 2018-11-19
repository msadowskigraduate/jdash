package io.zoran.core.application.common.mappers;

import io.zoran.core.domain.impl.ZoranUser;
import io.zoran.core.domain.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;

import static java.util.stream.Collectors.toList;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 17.11.2018
 */
@MapperDefinition(input = ZoranUser.class, output = UserDto.class)
@RequiredArgsConstructor
public class UserMapper implements Mapper<ZoranUser, UserDto> {

    private final Mapper<GrantedAuthority, String> mapper;

    @Override
    public UserDto map(ZoranUser principal) {
        return UserDto.of(
                principal.getLogin(),
                principal.getName(),
                principal.getAuthorities() == null ? new ArrayList<>() :
                        principal.getAuthorities().stream()
                        .map(mapper::map)
                        .collect(toList())
        );
    }
}
