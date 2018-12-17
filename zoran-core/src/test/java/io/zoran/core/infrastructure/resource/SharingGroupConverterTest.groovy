package io.zoran.core.infrastructure.resource

import io.zoran.core.application.ResourceTestSpec
/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 14/12/2018.
 *
 * @since
 */
class SharingGroupConverterTest extends ResourceTestSpec {
    def "should correctly convert Sharing group to dto"() {
        when:
        def result = SharingGroupConverter.convert(getSampleSharingGroup())

        then:
        result.projectId == "fakeProjectId"
        result.priviligesMap.get("Fake123") == "READ"
        result.priviligesMap.get("fakeUserId") == "WRITE"
    }
}
