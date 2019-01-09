package io.zoran.infrastructure.integrations.license

import io.zoran.application.ResourceTestSpec
import io.zoran.domain.git.License
import io.zoran.infrastructure.configuration.domain.GeneratorProperties
import io.zoran.infrastructure.configuration.domain.Zoran
import io.zoran.infrastructure.integrations.GitService
import spock.lang.Unroll

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 09.01.2019
 */
class LicenseServiceTest extends ResourceTestSpec {
    LicenseService licenseService
    LicenseRepository repository
    Zoran zoran
    GitService gitServiceMock

    def setup() {
        gitServiceMock = Mock(GitService)
        repository = Mock(LicenseRepository)
        zoran = new Zoran()
        licenseService = new LicenseService(repository, gitServiceMock, zoran)
    }

    def "should return all licenses"() {
        when:
        def result = licenseService.getAll()

        then:
        result.size() == 2
        1 * repository.count() >> 2
        1 * repository.findAll() >> [new License(), new License()]
        0 * _
    }

    def "should return all licenses when repository is empty"() {
        given:
        def licenses = [new License(), new License()]
        when:
        def result = licenseService.getAll()

        then:
        result.size() == 2
        1 * repository.count() >> 0
        1 * gitServiceMock.getLicenses() >> licenses
        1 * repository.saveAll(_ as List)
        1 * repository.findAll() >> licenses
        0 * _
    }

    @Unroll
    def "should get license by its key or get default"() {
        given:
        def gen = new GeneratorProperties()
        gen.setDefaultLicense("mit")
        zoran.setGenerator(gen)

        when:
        def result = licenseService.getOrDefault(key)

        then:
        result.key == resultKey
        repository.findByKey("mit") >> Optional.of(getSampleLicenseWithKey("mit"))
        repository.findByKey("apache-2.0") >> Optional.of(getSampleLicenseWithKey("apache-2.0"))
        repository.findByKey(null) >> Optional.empty()

        where:
        key          | resultKey
        "mit"        | "mit"
        null         | "mit"
        "apache-2.0" | "apache-2.0"
    }
}