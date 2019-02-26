package io.zoran.application.resource

import io.zoran.application.ResourceTestSpec
import io.zoran.application.indexer.TemplateFactory
import io.zoran.domain.resource.Resource
import io.zoran.infrastructure.integrations.license.LicenseService
import io.zoran.infrastructure.resource.ResourceRepository


/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 22.11.2018
 */
class ResourceServiceImplTest extends ResourceTestSpec {
    ResourceRepository resourceRepository
    SharingGroupService sharingGroupService
    ResourceServiceImpl resourceService
    LicenseService licenseService
    TemplateFactory templateFactory

    def setup() {
        resourceRepository = Mock()
        sharingGroupService = Mock()
        licenseService = Mock()
        templateFactory = Mock()
        resourceService = new ResourceServiceImpl(resourceRepository, licenseService, sharingGroupService, templateFactory)
    }

    def "should get list of projectIds for of found sharing groups"() {
        given:
        def userid = getFakeCurrentUser()

        when:
        def idList = resourceService.getAllSharedResources(userid)

        then:
        1 * sharingGroupService.getAllForUser(_ as String) >> [getSampleSharingGroup(), getSampleSharingGroup()]
        idList.every() {it == "fakeProjectId"}
        0 * _
    }

    def "should transfer ownership of project from one user to another"() {
        given:
        def resource = getSampleResource(getFakeCurrentUser())
        def resourceId = resource.getId()
        def receipientId = "fakeReceipientId"

        when:
        def changedResource = resourceService.transferOwnership(resourceId, receipientId)

        then:
        changedResource.getOwner() == receipientId
        1 * resourceRepository.findById(_ as String) >> Optional.of(resource)
        1 * resourceRepository.deleteById(_ as String)
        1 * resourceRepository.save(_ as Resource) >> resource.transferOwnership(receipientId)
        0 * _
    }
}
