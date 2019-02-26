package io.zoran.application

import io.zoran.api.domain.ProjectResourceRequest
import io.zoran.domain.git.License
import io.zoran.domain.manifest.ResourceType
import io.zoran.domain.manifest.YMLManifest
import io.zoran.domain.resource.Resource
import io.zoran.domain.resource.ResourcePrivileges
import io.zoran.domain.resource.ResourceVisibility
import io.zoran.domain.resource.project.ProjectDetails
import io.zoran.domain.resource.project.ProjectResource
import io.zoran.domain.resource.shared.SharingGroup
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
        def resource = ProjectResource.builder()
                                      .id("fakeResourceId")
                                      .name("fakeName")
                                      .projectDetails(getSampleProjectDetails())
                                      .resourceType(ResourceType.CLASS)
                                      .visibility(ResourceVisibility.PUBLIC)
                                      .build()
        resource.owner = ownerId
        return resource
    }

    protected ProjectDetails getSampleProjectDetails() {
        return ProjectDetails.builder()
                             .name("fakename")
                             .projectLanguage("fakeLang")
                             .projectName("fakeProjectNAme")
                             .artifactId("fakeArtifactId")
                             .groupId("fakeGroupId")
                             .description("fakeDesc")
                             .lead("fakeLead")
                             .version("fakeVersion")
                             .tags("fakeTag fakeTag fakeTag".split(" "))
                             .build()
    }

    protected License getSampleLicenseWithKey(String key) {
        def result = new License()
        result.setKey(key)
        return result
    }

    protected ProjectResourceRequest getSampleProjectRequest() {
        return new ProjectResourceRequest(
                "fakeName",
                "projectLanguage",
                "groupId",
                "artifactId",
                ResourceType.CLASS,
                ResourceVisibility.PUBLIC,
                "v1",
                "fakeLead",
                "fakeDesc",
                "fakeTag fakeTag fakeTag",
                [],
                [],
                "mit",
                "", "",""
        )
    }

    protected YMLManifest getSampleManifest() {
        return new YMLManifest(
                "fakeName",
                "fakeLead",
                "v1",
                "fakeOwner",
                "/fake/path/toSource",
                "fakeDesc",
                "mit",
                "projectLanguage",
                ["fakeTag","fakeTag","fakeTag"].toArray(new String[0]),
                ["fakeDep1", "fakeDep2"],
                ResourceType.CLASS,
                ResourceVisibility.PUBLIC,
                [],
               new HashMap<String, String>()
        )
    }
}