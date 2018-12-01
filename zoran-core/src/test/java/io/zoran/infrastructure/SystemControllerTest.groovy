package io.zoran.infrastructure

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 30/11/2018.
 *
 * @since 1.0
 */
@AutoConfigureMockMvc
@SpringBootTest
class SystemControllerTest extends Specification {

    @Autowired
    MockMvc mockMvc

    def "should get version correctly"() {
        when: 'calling version info endpoint'
        mockMvc.perform(get("/build-info"))
                .andExpect(status().is2xxSuccessful())
                .andReturn()
        then:
        noExceptionThrown()
    }
}
