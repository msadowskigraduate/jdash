package io.zoran.infrastructure.resource


import io.zoran.application.ResourceTestSpec
/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 09.01.2019
 */
class ResourceConverterTest extends ResourceTestSpec {
    def "should correctly convert request to domain object"() {
        given:
        def request = getSampleProjectRequest()
        def license = getSampleLicenseWithKey("mit")

        when:
        def result = ResourceConverter.convert(request, license, "fakeOwner")

        then:
        result != null
        result.getName() == request.getName()
        result.getOwner() == "fakeOwner"
        result.getResourceType() == request.getType()
        result.getVisibility()== request.getResourceVisibility()
        result.getLicense() == license
        result.getProjectDetails().getName() == request.getName()
        result.getProjectDetails().getArtifactId() == request.getArtifactId()
        result.getProjectDetails().getGroupId() == request.getGroupId()
        result.getProjectDetails().getDescription() == request.getDescription()
        result.getProjectDetails().getLead() == request.getLead()
        result.getProjectDetails().getTags() == ["fakeTag", "fakeTag", "fakeTag"].toArray()
        result.getProjectDetails().getVersion() ==  request.getVersion()
    }

    def "should correctly create ResponseResponse object for given domain object"() {
        given:
        def resource = getSampleResource("fakeowner")

        when:
        def response = ResourceConverter.convert(resource)

        then:
        response.getName() == resource.getName()
        response.getOwner() == resource.getOwner()
        response.getType() == resource.getResourceType()
        response.getResourceVisibility()== resource.getVisibility()
        response.getLicense() == resource.getLicense()
        response.getArtifactId() == resource.getProjectDetails().getArtifactId()
        response.getGroupId() == resource.getProjectDetails().getGroupId()
        response.getDescription() == resource.getProjectDetails().getDescription()
        response.getLead() == resource.getProjectDetails().getLead()
        response.getTags() == "fakeTag,fakeTag,fakeTag"
        response.getVersion() ==  resource.getProjectDetails().getVersion()
    }
}
