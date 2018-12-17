package io.zoran.core.application.resource

import io.zoran.core.application.ResourceTestSpec
import io.zoran.core.domain.resource.ResourcePrivileges
import io.zoran.core.domain.resource.shared.SharingGroup
import io.zoran.core.infrastructure.resource.SharingGroupRepository
import spock.lang.Unroll
/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 20.11.2018
 */
class SharingGroupServiceImplTest extends ResourceTestSpec {
    SharingGroupRepository repository
    SharingGroupServiceImpl service

    def setup() {
        repository = Mock()
        service = new SharingGroupServiceImpl(repository)
    }

    def "should create new sharing group if it does not exists for project"() {
        when:
        service.getAccessPrivilegeFor("fakeProjectId", "fakeUserId")
        then:
        1 * repository.findByProjectId(_ as String) >> Optional.empty()
        1 * repository.save(_ as SharingGroup) >> getSampleSharingGroup()
        0 * _
    }

    @Unroll
    def "should return #result type for given user #userId in given project"() {
        when:
        def string = service.getAccessPrivilegeFor("fakeProjectId", userId)
        then:
        assert string.equals(result)
        1 * repository.findByProjectId(_ as String) >> Optional.of(getSampleSharingGroup())
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
        def resource = getSampleSharingGroup()

        when:
        service.giveAccess("fakeProjectId", userId, result)

        then:
        resource.getAccessFor(userId).name() == result
        1 * repository.findByProjectId(_ as String) >> Optional.of(resource)
        1 * repository.deleteById(_ as String)
        1 * repository.save(_ as SharingGroup)
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
        def resource = getSampleSharingGroup()

        when:
        service.revokeAccessFor("fakeProjectId", userId)

        then:
        resource.getAccessFor(userId) == ResourcePrivileges.REVOKED
        1 * repository.findByProjectId(_ as String) >> Optional.of(resource)
        1 * repository.deleteById(_ as String)
        1 * repository.save(_ as SharingGroup)
        0 * _

        where:
        userId              | result
        "fakeUserId"        | "WRITE"
        "Fake123"           | "READ"
        "invalidfakeUserId" | "REVOKED"
    }

    def "Should return all current privileges for fakeProjectId"() {
        given:
        def projectId = "fakeProjectId"
        when:
        def result = service.getAuthorizedUsersList(projectId)

        then:
        1 * repository.findByProjectId(_ as String) >> Optional.of(getSampleSharingGroup())
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
}
