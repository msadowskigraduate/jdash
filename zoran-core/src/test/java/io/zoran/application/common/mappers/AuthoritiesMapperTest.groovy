package io.zoran.application.common.mappers

import org.springframework.security.core.authority.SimpleGrantedAuthority
import spock.lang.Specification

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 14/12/2018.
 *
 * @since
 */
class AuthoritiesMapperTest extends Specification {
    AuthoritiesMapper mapper = new AuthoritiesMapper()

    def "should map Authority to String"() {
        given:
        def auth = new SimpleGrantedAuthority("fakeRole")

        when:
        def result = mapper.map(auth)

        then:
        result == "fakeRole"
    }
}
