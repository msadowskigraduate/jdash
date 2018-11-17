package io.zoran.core.application.common.mappers;

import io.zoran.core.domain.user.UserDto;

import java.security.Principal;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 17.11.2018
 */
@MapperDefinition(input = Principal.class, output = UserDto.class)
public class UserMapper implements Mapper<Principal, UserDto> {

    @Override
    public UserDto map(Principal principal) {
        return null;
    }
}
