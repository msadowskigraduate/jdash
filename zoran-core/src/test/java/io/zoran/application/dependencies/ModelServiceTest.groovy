package io.zoran.application.dependencies

import io.zoran.api.domain.DependencyRequest
import io.zoran.api.domain.ResourceDependencyMetadata
import io.zoran.api.domain.ResourceDependencyMetadataModel
import spock.lang.Specification
import spock.lang.Unroll

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 11.12.2018
 */
class ModelServiceTest extends Specification {

    ModelService service

    def setup() {
        service = new ModelService(getDependencyList())
    }

    @Unroll
    def "should get all dependencies for #id"() {
        when:
        def result = service.getAllDependenciesFor(new DependencyRequest(id, null))

        then:
        result.dependencies.size() == size
        print result
        0 * _

        where:
        id       | size
        "spring" | 1
        "python" | 1
        null     | 2
    }

    private static List getDependencyList() {
        [new DependencyService() {
            @Override
            String getIdentifier() {
                return "spring"
            }

            @Override
            List<ResourceDependencyMetadata> getDependenciesForVersion(String version) {
                return [ResourceDependencyMetadataModel.of("fakeParentId", "fakeId", "fakeName", "fakeDesc",
                        "fakegroup", "fakeVersion")]
            }
        },
         new DependencyService() {
             @Override
             String getIdentifier() {
                 return "python"
             }

             @Override
             List<ResourceDependencyMetadata> getDependenciesForVersion(String version) {
                 return [ResourceDependencyMetadataModel.
                                 of("pythonfakeParentId", "pythonfakeId", "pythonfakeName", "pythonfakeDesc",
                                         "pythonfakegroup", "pythonfakeVersion")]
             }
         },
         new DependencyService() { //inject fault on purpose
             @Override
             String getIdentifier() {
                 return null
             }

             @Override
             List<ResourceDependencyMetadata> getDependenciesForVersion(String version) {
                 return null
             }
         }
        ]
    }
}
