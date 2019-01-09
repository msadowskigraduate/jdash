package io.zoran.application.common.utils

import spock.lang.Specification

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 08/01/2019.
 *
 * @since
 */
class XSSFilterUtilsTest extends Specification {
    private static String INPUT = ''
    def "should correctly sanitize XSS containing string"() {
        when:
        def result = XSSFilterUtils.sanitize()
    }
}
