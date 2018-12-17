package io.zoran.application.common.mappers

import io.spring.initializr.metadata.Dependency
import io.zoran.api.domain.ResourceDependencyMetadataModel
import io.zoran.domain.generator.DependencyItem
import spock.lang.Specification

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 14/12/2018.
 *
 * @since
 */
class DependencyItemToModelMapperTest extends Specification {
    def mapper = new DependencyItemToModelMapper()

    def "should correctly map dependencies"() {
        given:
        Dependency dept = new Dependency()
        dept.setId("fakeId")
        dept.setName("fakeName")
        dept.setDescription("fakeDesc")
        dept.setVersion("fakeVersion")
        def dependency = new DependencyItem("fakeGroup", dept)

        when:
        def result = mapper.map(dependency)

        then:
        0 * _
        result instanceof ResourceDependencyMetadataModel
        result.properties.get("id") == dept.getId()
        result.properties.get("name") == dept.getName()
        result.properties.get("description") == dept.getDescription()
        result.properties.get("group") == dependency.group
        result.properties.get("version") == dept.getVersion()
    }
}
