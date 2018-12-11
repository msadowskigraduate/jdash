package io.zoran.application.indexer

import io.zoran.application.manifest.ManifestReader
import io.zoran.application.manifest.YMLManifestReader
import io.zoran.domain.indexer.Indexer
import io.zoran.domain.indexer.Tree
import spock.lang.Specification
/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 09/12/2018.
 *
 * @since
 */
class FileIndexerTest extends Specification {

    ManifestReader reader
    Indexer<Tree> index

    def setup() {
        reader = new YMLManifestReader()
        index = new FileIndexer(reader)
    }

    def "should correctly index test folder"() {
        when:
        Tree t = index.index(new File("src/test").toPath())

        then:
        t.nodeList != null
        t.nodeList.size() > 0
        t.rootNode != null

        print t.toString()
    }
}
