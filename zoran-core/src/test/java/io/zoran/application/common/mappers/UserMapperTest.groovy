package io.zoran.application.common.mappers

import io.zoran.core.domain.impl.ZoranUser
import io.zoran.core.domain.user.User
import io.zoran.core.domain.user.UserDto
import io.zoran.core.domain.user.UserState
import spock.lang.Specification

import java.time.LocalDateTime

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 14/12/2018.
 *
 * @since
 */
class UserMapperTest extends Specification {
    Mapper<User, UserDto> mapper = new UserMapper()

    def "should correctly map user to userDto"() {
        given:
        def user = ZoranUser.builder()
                .id("fakeId")
                .login("fakeLogin")
                .state(UserState.ACTIVE)
                .avatarUrl("fakeUrl")
                .email("fakeEmail")
                .htmlUrl("fakeHtmlUrl")
                .name("fakeName")
                .userType("fakeUserType")
                .repoUrl("repoUrl")
                .lastLogin(LocalDateTime.MIN)
                .build()

        when:
        def result = mapper.map(user)
        then:
        0 * _
        result != null
        result.getLogin() == user.getLogin()
        result.getState() == user.getState().name()
        result.getAvatarUrl() == user.getAvatarUrl()
        result.getEmail() == user.getEmail()
        result.getHtmlUrl() == user.getHtmlUrl()
        result.getName() == user.getName()
        result.getUserType() == user.getUserType()
        result.getRepoUrl() == user.getRepoUrl()
        result.getLastLogin() == user.getLastLogin().toString()
    }
}
