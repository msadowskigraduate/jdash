package io.zoran.core.application.user;

import io.zoran.core.domain.impl.ZoranUser;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;

import static io.zoran.core.domain.user.UserState.ACTIVE;
import static io.zoran.core.domain.user.UserState.ANONYMOUS;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 31/12/2018.
 */
@UtilityClass
class SpecialUserMapper {

    static ZoranUser getSuperUser() {
        return ZoranUser.builder()
                .id("ADMIN")
                .name("ADMIN")
                .state(ACTIVE)
                .userType("ADMIN")
                .login("ADMIN")
                .lastLogin(LocalDateTime.MIN)
                .build();
    }

    static ZoranUser getAnonymousUser() {
        return ZoranUser.builder()
                .state(ANONYMOUS)
                .lastLogin(LocalDateTime.MIN)
                .build();
    }
}
