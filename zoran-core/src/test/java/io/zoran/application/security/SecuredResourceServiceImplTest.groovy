package io.zoran.application.security

import io.zoran.application.ResourceTestSpec
import io.zoran.application.resource.ResourceService
import io.zoran.application.resource.SharingGroupService
import io.zoran.application.user.ZoranUserService
import io.zoran.domain.resource.ResourceVisibility
import io.zoran.infrastructure.exception.ResourceNotFoundException
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException
import spock.lang.Title
/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 21.11.2018
 */
@Title("The following test case tests the implementation of serving secured assets.")
class SecuredResourceServiceImplTest extends ResourceTestSpec {
    SecuredResourceServiceImpl service
    ZoranUserService zoranUserService
    ResourceService resourceService
    SharingGroupService sharedResourceService

    def setup() {
        zoranUserService = Mock()
        resourceService = Mock()
        sharedResourceService = Mock()

        service = new SecuredResourceServiceImpl(zoranUserService, resourceService, sharedResourceService)
    }

    def "should return a resource by its id correctly when user is owner"() {
        given:
        def ownerId = "fakeOwner"
        def resourceId = "fakeId"
        def resourceFromService = getSampleResource(ownerId)
        when:
        def resource = service.authoriseResourceRequest(resourceId)

        then:
        1 * zoranUserService.authenticateAndGetUserId() >> ownerId
        1 * resourceService.getResourceById(_ as String) >> resourceFromService

        assert resource != null

        0 * _

    }

    def "should return a resource by its id correctly when user can read/write"() {
        given:
        def ownerId = "fakeOwner"
        def resourceId = "fakeId"
        def currentUserId = getFakeCurrentUser()
        def resourceFromService = getSampleResource(ownerId)

        when:
        def resource = service.authoriseResourceRequest(resourceId)

        then:
        1 * zoranUserService.authenticateAndGetUserId() >> currentUserId
        2 * resourceService.getResourceById(_ as String) >> resourceFromService
        1 * sharedResourceService.getAllForUser(_ as String) >> [getSampleSharingGroup(), getSampleSharingGroup()]
        assert resource != null
        0 * _
    }

    def "should throw an exception when no resource is found for current user"() {
        given:
        def ownerId = "fakeOwner"
        def resourceId = "fakeId"
        def resourceFromService = getSampleResource(ownerId)

        when:
        service.authoriseResourceRequest(resourceId)

        then:
        thrown(ResourceNotFoundException)
        1 * zoranUserService.authenticateAndGetUserId() >> getFakeUnauthorizedCurrentUser()
        1 * resourceService.getResourceById(resourceId) >> resourceFromService
        1 * sharedResourceService.getAllForUser(_ as String) >> [getSampleSharingGroup(), getSampleSharingGroup()]
        0 * _
    }

    def "should return all resources principal can at least read"() {
        given:
        def ownerId = "fakeOwner"
        def resourceFromService = getSampleResource(ownerId)

        when:
        def result = service.authorizedGetAllResourcesConnectedWithPrincipal()

        then:
        result.size() == 2
        1 * zoranUserService.authenticateAndGetUserId() >> getFakeCurrentUser()
        2 * resourceService.getResourceById(_ as String) >> resourceFromService
        1 * sharedResourceService.getAllForUser(_ as String) >> [getSampleSharingGroup(), getSampleSharingGroup()]
        1 * resourceService.getAllResources(_ as ResourceVisibility) >> []
        0 * _
    }

    def "should return sharing group connected with given project id"() {
        given:
        def fakeProjectID = "fakeProjectId"
        def currentUser = getFakeCurrentUser()
        when:
        def result = service.authorizedGetSharingGroupForProject(fakeProjectID)

        then:
        assert result != null
        1 * zoranUserService.authenticateAndGetUserId() >> currentUser
        1 * resourceService.getResourceById(_ as String) >> getSampleResource(currentUser)
        1 * sharedResourceService.getSharingGroupForProject(fakeProjectID) >> getSampleSharingGroup()
        0 * _
    }

    def "should throw exception sharing group connected with given project id"() {
        given:
        def fakeProjectID = "fakeProjectId"
        def priviligedUser = getFakeCurrentUser()
        def currentUser = getFakeUnauthorizedCurrentUser()
        when:
        def result = service.authorizedGetSharingGroupForProject(fakeProjectID)

        then:
        thrown(UnauthorizedUserException)
        1 * zoranUserService.authenticateAndGetUserId() >> currentUser
        1 * resourceService.getResourceById(_ as String) >> getSampleResource(priviligedUser)
        0 * _
    }

    def "should give access for resource to user"() {
        given:
        def currentUser = getFakeCurrentUser()
        def priviligedUser = "newUser"
        def resource = getSampleResource(currentUser)

        when:
        service.authoriseGiveAccessRequest(resource.getId(), priviligedUser, "READ")

        then:
        1 * zoranUserService.authenticateAndGetUserId() >> currentUser
        1 * resourceService.getResourceById(_ as String) >> resource
        1 * sharedResourceService.giveAccess(_ as String, _ as String, _ as String)
        0 * _
    }

    def "should throw exception when trying to give access for not owned resource to user"() {
        given:
        def owner = getFakeCurrentUser()
        def currentUser = getFakeUnauthorizedCurrentUser()
        def resource = getSampleResource(currentUser)

        when:
        service.authoriseGiveAccessRequest(resource.getId(), currentUser, "READ")

        then:
        thrown(UnauthorizedUserException)
        1 * zoranUserService.authenticateAndGetUserId() >> owner
        1 * resourceService.getResourceById(_ as String) >> resource
        0 * _
    }

    def "should revoke access for resource to user"() {
        given:
        def currentUser = getFakeCurrentUser()
        def priviligedUser = "newUser"
        def resource = getSampleResource(currentUser)

        when:
        service.authoriseRevokeAccessForRequest(resource.getId(), priviligedUser)

        then:
        1 * zoranUserService.authenticateAndGetUserId() >> currentUser
        1 * resourceService.getResourceById(_ as String) >> resource
        1 * sharedResourceService.revokeAccessFor(_ as String, _ as String)
        0 * _
    }

    def "should throw exception when trying to revoke access for not owned resource to user"() {
        given:
        def owner = getFakeCurrentUser()
        def currentUser = getFakeUnauthorizedCurrentUser()
        def resource = getSampleResource(currentUser)

        when:
        service.authoriseRevokeAccessForRequest(resource.getId(), currentUser)

        then:
        thrown(UnauthorizedUserException)
        1 * zoranUserService.authenticateAndGetUserId() >> owner
        1 * resourceService.getResourceById(_ as String) >> resource
        0 * _
    }

    def "should return access lists for resource to user"() {
        given:
        def currentUser = getFakeCurrentUser()
        def resource = getSampleResource(currentUser)

        when:
        service.authoriseGetAuthorizedUsersListRequest(resource.getId())

        then:
        1 * zoranUserService.authenticateAndGetUserId() >> currentUser
        1 * resourceService.getResourceById(_ as String) >> resource
        1 * sharedResourceService.getAuthorizedUsersList(_ as String)
        0 * _
    }

    def "should throw exception when trying to get access lists for not owned resource"() {
        given:
        def owner = getFakeCurrentUser()
        def currentUser = getFakeUnauthorizedCurrentUser()
        def resource = getSampleResource(currentUser)

        when:
        service.authoriseGetAuthorizedUsersListRequest(resource.getId())

        then:
        thrown(UnauthorizedUserException)
        1 * zoranUserService.authenticateAndGetUserId() >> owner
        1 * resourceService.getResourceById(_ as String) >> resource
        0 * _
    }

    def "should transfer ownership of a resource from user to user"() {
        given:
        def currentUser = getFakeCurrentUser()
        def recipientUser = "fakeNewUser"
        def resource = getSampleResource(currentUser)

        when:
        service.transferOwnership(resource.getId(), recipientUser)

        then:
        1 * zoranUserService.authenticateAndGetUserId() >> currentUser
        1 * resourceService.getResourceById(_ as String) >> resource
        1 * resourceService.transferOwnership(resource.getId(), recipientUser) >> resource
        0 * _
    }

    def "should throw exception when trying to transfer ownership of a resource from user to user"() {
        given:
        def owner = getFakeCurrentUser()
        def currentUser = getFakeUnauthorizedCurrentUser()
        def resource = getSampleResource(currentUser)

        when:
        service.transferOwnership(resource.getId(), "fakeNewRecipient")

        then:
        thrown(UnauthorizedUserException)
        1 * zoranUserService.authenticateAndGetUserId() >> owner
        1 * resourceService.getResourceById(_ as String) >> resource
        0 * _
    }

    def "should delete a resource"() {
        given:
        def currentUser = getFakeCurrentUser()
        def resource = getSampleResource(currentUser)

        when:
        service.deleteResource(resource.getId())

        then:
        1 * zoranUserService.authenticateAndGetUserId() >> currentUser
        1 * resourceService.getResourceById(_ as String) >> resource
        1 * resourceService.deleteResource(resource.getId()) >> resource
        0 * _
    }

    def "should throw exception when trying to delete a resource"() {
        given:
        def owner = getFakeCurrentUser()
        def currentUser = getFakeUnauthorizedCurrentUser()
        def resource = getSampleResource(currentUser)

        when:
        service.deleteResource(resource.getId())

        then:
        thrown(UnauthorizedUserException)
        1 * zoranUserService.authenticateAndGetUserId() >> owner
        1 * resourceService.getResourceById(_ as String) >> resource
        0 * _
    }
}
