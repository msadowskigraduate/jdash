package io.zoran.application.dependencies

import io.zoran.application.indexer.IndexerService
import io.zoran.core.domain.resource.ResourceVisibility
import io.zoran.domain.indexer.Tree
import io.zoran.domain.manifest.Location
import io.zoran.domain.manifest.ResourceType
import io.zoran.domain.manifest.YMLManifest
import spock.lang.Specification
import spock.lang.Unroll

import java.util.function.Predicate

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 07/01/2019.
 *
 * @since
 */
class IndexedDependencyServiceTest extends Specification {
    IndexedDependencyService service
    IndexerService indexerService

    def setup() {
        indexerService = Mock(IndexerService)
        service = new IndexedDependencyService(indexerService)
    }

    def "should return correct identifier"() {
        def result
        when:
        result = service.getIdentifier()
        then:
        result == 'indexer'
    }

    @Unroll
    def "should return dependencies from template trees for #identifier"() {
        when:
        service.getDependenciesForVersion(identifier)

        then:
        1 * indexerService.indexedResults >> []
        0 * _

        where:
        identifier | _
        null       | _
        "1.0"      | _
        ""         | _
        " "        | _
    }

    @Unroll
    def "should return dependencies from template trees for #identifier for non null indexed"() {
        given:
        def t = Mock(Tree)

        when:
        def result = service.getDependenciesForVersion(identifier)

        then:
        !result.isEmpty()
        1 * indexerService.indexedResults >> [t, createEmptySampleTree()]
        1 * t.getAllManifests(_ as Predicate) >> [new YMLManifest(
                "id",
                "name",
                "slug",
                "lead",
                "1.0",
                "Admin",
                null,
                null,
                ResourceVisibility.PUBLIC,
                ResourceType.CLASS,
                Location.API,
                ["dep1", "dep2"])]
        0 * _

        where:
        identifier | _
        null       | _
        "1.0"      | _
        ""         | _
        " "        | _
    }

    private Tree createEmptySampleTree() {
        return Tree.emptyTree()
    }
}
