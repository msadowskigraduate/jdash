package io.zoran.core.application.resource

import io.zoran.core.domain.resource.ResourcePrivileges
import io.zoran.core.domain.resource.shared.SharedProjectResource
import io.zoran.core.infrastructure.exception.ResourceNotFoundException
import io.zoran.core.infrastructure.resource.SharedResourceRepository
import spock.lang.Specification
import spock.lang.Unroll

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 20.11.2018
 */
class SharedResourceServiceImplTest extends Specification {
    SharedResourceRepository repository
    SharedResourceServiceImpl service

    def setup() {
        repository = Mock()
        service = new SharedResourceServiceImpl(repository)
    }

    def "should throw an exception if resource is not found"() {
        given:
        when:
        def string = service.getAccessPrivilegeFor("fakeProjectId", "fakeUserId")
        then:
        thrown(ResourceNotFoundException)
        1 * repository.findByProjectId(_ as String) >> Optional.empty()
        0 * _
    }

    @Unroll
    def "should return #result type for given user #userId in given project"() {
        given:
        when:
        def string = service.getAccessPrivilegeFor("fakeProjectId", userId)
        then:
        assert string.equals(result)
        1 * repository.findByProjectId(_ as String) >> Optional.of(getSampleResource())
        0 * _

        where:
        userId              | result
        "fakeUserId"        | "WRITE"
        "Fake123"           | "READ"
        "invalidfakeUserId" | "REVOKED"
    }

    @Unroll
    def "should grant privilege #result to #userId "() {
        given:
        def resource = getSampleResource()

        when:
        service.giveAccess("fakeProjectId", userId, result)

        then:
        resource.getAccessFor(userId).name() == result
        1 * repository.findByProjectId(_ as String) >> Optional.of(resource)
        1 * repository.deleteById(_ as String)
        1 * repository.save(_ as SharedProjectResource)
        0 * _

        where:
        userId              | result
        "fakeUserId"        | "WRITE"
        "Fake123"           | "READ"
        "invalidfakeUserId" | "REVOKED"
    }

    @Unroll
    def "should correctly revoke access for #userId"() {
        given:
        def resource = getSampleResource()

        when:
        service.revokeAccessFor("fakeProjectId", userId)

        then:
        resource.getAccessFor(userId) == ResourcePrivileges.REVOKED
        1 * repository.findByProjectId(_ as String) >> Optional.of(resource)
        1 * repository.deleteById(_ as String)
        1 * repository.save(_ as SharedProjectResource)
        0 * _

        where:
        userId              | result
        "fakeUserId"        | "WRITE"
        "Fake123"           | "READ"
        "invalidfakeUserId" | "REVOKED"
    }

    def "Should return all current privileges for #projectId"() {
        given:
        def projectId = "fakeProjectId"
        when:
        def result = service.getAuthorizedUsersList(projectId)

        then:
        1 * repository.findByProjectId(_ as String) >> Optional.of(getSampleResource())
        result.every ({
            it.key == "Fake123" || "fakeUserId"
            if(it.key == "fakeUserId") {
                it.value = "WRITE"
            }
            else if(it.key == "Fake123") {
                it.value = "READ"
            }
            else {
                false
            }
        })
    }

    private SharedProjectResource getSampleResource() {
        def result = new SharedProjectResource("Sample", "fakeProjectId")
        result.priviligesMap = ["Fake123"   : ResourcePrivileges.READ,
                                "fakeUserId": ResourcePrivileges.WRITE]
        return result
    }
}
