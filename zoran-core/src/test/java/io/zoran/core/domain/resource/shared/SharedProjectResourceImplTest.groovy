package io.zoran.core.domain.resource.shared

import io.zoran.core.domain.resource.ResourcePrivileges
import io.zoran.core.infrastructure.exception.ResourceAccessPriviligesException
import spock.lang.Specification
import spock.lang.Unroll

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 20.11.2018
 */
class SharedProjectResourceImplTest extends Specification {
    SharedProjectResource sharedProjectResource = new SharedProjectResource("id", "idResource")

    @Unroll
    def "should give access to user of #id"() {
        given:
        when:
        sharedProjectResource.giveAccess(id, access)

        then:
        sharedProjectResource.getAccessFor(id) == access

        where:
        id       | access
        "fakeId" | ResourcePrivileges.WRITE
        "fakeId" | ResourcePrivileges.READ
    }

    def "should throw exception if status is revoked or exceeds its rights"() {
        given:
        def id = "fakeid"

        when:
        sharedProjectResource.giveAccess(id, ResourcePrivileges.OWNER)

        then:
        thrown(ResourceAccessPriviligesException)
    }

    def "should revoke access"() {
        given:
        def id = "fakeid"

        when:
        sharedProjectResource.giveAccess(id, ResourcePrivileges.REVOKED)

        then:
        sharedProjectResource.getAccessFor(id) == ResourcePrivileges.REVOKED
    }
}