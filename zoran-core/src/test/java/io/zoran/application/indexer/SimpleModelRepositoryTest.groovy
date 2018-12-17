package io.zoran.application.indexer

import io.zoran.domain.indexer.Tree
import spock.lang.Specification

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 17/12/2018.
 *
 * @since
 */
class SimpleModelRepositoryTest extends Specification {
    SimpleModelRepository storeTest

    def setup() {
        storeTest = SimpleModelRepository.newSimpleStore()
    }

    def "should create new simple store"() {
        when:
        def store = SimpleModelRepository.newSimpleStore()
        store.getAll()
        then:
        noExceptionThrown()
        0 * _
    }

    def "should add new tree to store"() {
        when:
        storeTest.addNewTree(Tree.newTree())

        then:
        storeTest.getAll().size() == 1
        noExceptionThrown()
        0 * _
    }

    def "should return tree by its id"() {
        given:
        def tree = Tree.newTree()
        storeTest.addNewTree(tree)

        when:
        def result = storeTest.getTreeById(tree.getId())

        then:
        result == tree
        noExceptionThrown()
        0 * _
    }

    def "should evict by id"() {
        given:
        def tree = Tree.newTree()
        storeTest.addNewTree(tree)

        when:
        def result = storeTest.evict(tree.getId())

        then:
        storeTest.getAll().size() == 0
        noExceptionThrown()
        0 * _
    }

    def "should evict all records"() {
        given:
        def tree = Tree.newTree()
        storeTest.addNewTree(tree)

        when:
        def result = storeTest.evict()

        then:
        storeTest.getAll().size() == 0
        noExceptionThrown()
        0 * _
    }

    def "should return all saved trees"() {
        given:
        def tree = Tree.newTree()
        storeTest.addNewTree(tree)

        when:
        def result = storeTest.getAll()

        then:
        result.size() == 1
        noExceptionThrown()
        0 * _
    }

    def "should return false if store is not empty"() {
        given:
        def tree = Tree.newTree()
        storeTest.addNewTree(tree)

        when:
        def result = storeTest.isEmpty()

        then:
        !result
        noExceptionThrown()
        0 * _
    }

    def "should return true if the store is empty"() {
        when:
        def result = storeTest.isEmpty()

        then:
        result
        noExceptionThrown()
        0 * _
    }
}
