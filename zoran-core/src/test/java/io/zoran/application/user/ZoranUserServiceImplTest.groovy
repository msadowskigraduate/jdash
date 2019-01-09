package io.zoran.application.user

import io.zoran.domain.impl.ZoranUser
import io.zoran.domain.user.UserState
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDateTime

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 19/11/2018.
 *
 * @since
 */
class ZoranUserServiceImplTest extends Specification {

    ZoranUserServiceImpl zoranUserService
    UserStore userStore

    def setup() {
        userStore = Mock()
        zoranUserService = new ZoranUserServiceImpl(userStore)
    }

    def "Should successfully authenticate user"() {
        given:

        when:
        def user = zoranUserService.authenticateUser("123")

        then:
        notThrown(UnauthorizedUserException)
        1 * userStore.findById(_ as String) >> Optional.of(ZoranUser.builder().state(UserState.ACTIVE).build())
        assert user instanceof ZoranUser
        user.getLastLogin().getMinute() == LocalDateTime.now().getMinute()
        0 * _
    }

    def "Should throw exception with cannot authenticate user"() {
        given:

        when:
        def user = zoranUserService.authenticateUser("123")

        then:
        thrown(UnauthorizedUserException)
        1 * userStore.findById(_ as String) >> Optional.empty()
        0 * _
    }

    @Unroll
    def "Should throw exception if user's status is #state"() {
        given:

        when:
        def user = zoranUserService.authenticateUser("123")

        then:
        thrown(UnauthorizedUserException)
        1 * userStore.findById(_ as String) >> Optional.of(ZoranUser.builder().state(state).build())
        0 * _

        where:
        state                    | _
        UserState.ACCESS_REVOKED | _
        UserState.INACTIVE       | _

    }

    def "Should revoke access for user"() {
        given:

        when:
        zoranUserService.revokeAccessFor("123")

        then:
        1 * userStore.findById(_ as String) >> Optional.of(ZoranUser.builder().id("fakeId").build())
        1 * userStore.deleteById(_ as String)
        1 * userStore.save(_ as ZoranUser)
        0 * _

    }
}
