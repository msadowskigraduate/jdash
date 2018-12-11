package io.zoran.core.application

import io.zoran.core.domain.resource.Resource
import io.zoran.core.domain.resource.ResourcePrivileges
import io.zoran.core.domain.resource.ResourceVisibility
import io.zoran.core.domain.resource.project.ProjectResourceImpl
import io.zoran.core.domain.resource.shared.SharingGroup
import spock.lang.Specification
/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 21.11.2
 18
 */
class ResourceTestSpec extends Specification {

    protected String getFakeCurrentUser() {
        "fakeUserId"
    }

    protected String getFakeUnauthorizedCurrentUser() {
        "evenFakerUserId"
    }

    protected SharingGroup getSampleSharingGroup() {
        def result = SharingGroup.builder().projectId("fakeProjectId").sharedResourceId("Sample").build()
        result.priviligesMap = ["Fake123"   : ResourcePrivileges.READ,
                                "fakeUserId": ResourcePrivileges.WRITE]
        return result
    }

    protected Resource getSampleResource(String ownerId) {
        def resource =  ProjectResourceImpl.builder()
                        .resourceId("fakeResourceId")
                        .resourceName("fakeName")
                        .resourceVisibility(ResourceVisibility.PUBLIC)
                        .build()
        resource.ownerId = ownerId
        return resource
    }


}
