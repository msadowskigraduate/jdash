package io.zoran.infrastructure.resource

import io.zoran.application.ResourceTestSpec
import io.zoran.infrastructure.integrations.license.LicenseService
/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 09.01.2019
 */
class ResourceMapperTest extends ResourceTestSpec {
    ResourceMapper mapper
    LicenseService licenseService

    def setup() {
        licenseService = Mock(LicenseService)
        mapper = new ResourceMapper(licenseService)
    }


    def "should correctly map Manifest to resource object"() {
        given:
        def sampleManifest = getSampleManifest()
        when:
        def result = mapper.map(sampleManifest)

        then:
        noExceptionThrown()
        result != null
        1 * licenseService.getOrDefault("mit") >> getSampleLicenseWithKey('mit')
        0 * _
    }
}