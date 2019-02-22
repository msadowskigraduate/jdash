package io.zoran.application.common.mappers

import io.zoran.domain.impl.ZoranUser
import io.zoran.domain.user.AccessToken
import io.zoran.domain.user.User
import io.zoran.domain.user.UserDto
import io.zoran.domain.user.UserState
import org.springframework.security.oauth2.core.OAuth2AccessToken
import spock.lang.Specification

import java.time.Instant
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
                            .accessToken(
                                 new AccessToken(
                                         OAuth2AccessToken.TokenType.BEARER,
                                         "fakeAccessToken",
                                         Instant.now(),
                                         Instant.now().plusNanos(100000000000)))
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
