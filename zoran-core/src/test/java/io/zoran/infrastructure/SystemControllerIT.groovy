package io.zoran.infrastructure

import groovyx.net.http.RESTClient
import io.zoran.infrastructure.configuration.domain.VersionHolder
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 30/11/2018.
 *
 * @since 1.0
 */
@SpringBootTest(
        classes = [SystemController.class, VersionHolder.class],
        webEnvironment = SpringBootTest.WebEnvironment.MOCK
)
@ContextConfiguration
class SystemControllerIT extends Specification {

    RESTClient restClient = new RESTClient("http://localhost:8082", MediaType.APPLICATION_JSON)

    def "should get version correctly"() {
        when: "get application version"
        def response = restClient.get(
                path: '/build-info',
                requestContentType: MediaType.APPLICATION_JSON
        )

        then:
        response.status == 200
        response.data != null
        print(response.data)
    }
}
