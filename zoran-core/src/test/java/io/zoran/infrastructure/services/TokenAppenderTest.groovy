package io.zoran.infrastructure.services

import spock.lang.Specification
import spock.lang.Unroll

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 30/12/2018.
 *
 * @since
 */
class TokenAppenderTest extends Specification {

    @Unroll
    def "should create correct token header"() {
        when:
        def expected = TokenAppender.getRequestHeaderToken(tokenValue)
        then:
        expected == result

        where:
        tokenValue  | result
        "faketoken" | "token faketoken"
        ""          | "token "
        null        | "token "
    }
}
