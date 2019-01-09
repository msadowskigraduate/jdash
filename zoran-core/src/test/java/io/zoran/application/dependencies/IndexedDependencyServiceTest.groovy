package io.zoran.application.dependencies

import io.zoran.application.ResourceTestSpec
import io.zoran.application.security.SecurityResourceService
import spock.lang.Unroll
/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 07/01/2019.
 *
 * @since
 */
class IndexedDependencyServiceTest extends ResourceTestSpec {
    IndexedDependencyService service
    SecurityResourceService resourceService

    def setup() {
        resourceService = Mock(SecurityResourceService)
        service = new IndexedDependencyService(resourceService)
    }

    def "should return correct identifier"() {
        def result
        when:
        result = service.getIdentifier()
        then:
        result == 'indexer'
    }

    @Unroll
    def "should return dependencies from template trees for #identifier"() {
        when:
        service.getDependenciesForVersion(identifier)

        then:
        1 * resourceService.authorizedGetAllResourcesConnectedWithPrincipal() >> [getSampleResource("ADMIN")]
        0 * _

        where:
        identifier | _
        null       | _
        "1.0"      | _
        ""         | _
        " "        | _
    }

    @Unroll
    def "should return dependencies from template trees for #identifier for non null indexed"() {
        when:
        def result = service.getDependenciesForVersion(identifier)

        then:
        result.isEmpty()
        1 * resourceService.authorizedGetAllResourcesConnectedWithPrincipal() >> []
        0 * _

        where:
        identifier | _
        null       | _
        "1.0"      | _
        ""         | _
        " "        | _
    }
}
