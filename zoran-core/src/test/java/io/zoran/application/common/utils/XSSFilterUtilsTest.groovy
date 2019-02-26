package io.zoran.application.common.utils

import io.zoran.infrastructure.services.XSSFilterUtils
import spock.lang.Specification

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 08/01/2019.
 *
 * @since
 */
class XSSFilterUtilsTest extends Specification {
    private static String INPUT = 'hello <a name="n"\n' +
            'href="javascript:alert(\'xss\')">*you*</a>'
    def "should correctly sanitize XSS containing string"() {
        when:
        def result = XSSFilterUtils.sanitize(INPUT)
        then:
        !result.contains("javascript")
        print(result)
    }
}
