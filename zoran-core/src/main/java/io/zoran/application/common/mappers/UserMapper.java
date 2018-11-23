package io.zoran.application.common.mappers;

import io.zoran.core.domain.impl.ZoranUser;
import io.zoran.core.domain.user.UserDto;
import lombok.RequiredArgsConstructor;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 17.11.2018
 */
@MapperDefinition(input = ZoranUser.class, output = UserDto.class)
@RequiredArgsConstructor
public class UserMapper implements Mapper<ZoranUser, UserDto> {

    @Override
    public UserDto map(ZoranUser principal) {
        return UserDto.of(
                principal.getLogin(),
                principal.getName(),
                principal.getState().name(),
                principal.getEmail(),
                principal.getAvatarUrl(),
                principal.getRepoUrl(),
                principal.getHtmlUrl(),
                principal.getUserType(),
                principal.getLastLogin().toString()
        );
    }
}
