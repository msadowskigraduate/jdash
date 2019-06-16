package io.zoran.infrastructure

import groovyx.net.http.RESTClient
import org.springframework.http.MediaType
import spock.lang.Specification

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 05.03.2
 19
 */
class LongTestIT extends Specification {
    RESTClient restClient = new RESTClient("http://localhost:8082", MediaType.APPLICATION_JSON)

    def "should get version correctly"() {
        when: "get application version"
        def response = restClient.get(
                path: '/api/ui/resource',
                requestContentType: MediaType.APPLICATION_JSON
        )

        then:
        response.status == 200
        response.data != null
        print(response.data)
    }
}
