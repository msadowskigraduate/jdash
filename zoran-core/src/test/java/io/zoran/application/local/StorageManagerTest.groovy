package io.zoran.application.local

import io.zoran.infrastructure.configuration.domain.StorageProperties
import io.zoran.infrastructure.configuration.domain.Zoran
import spock.lang.Shared
import spock.lang.Specification

import java.nio.file.Files
import java.nio.file.Path

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 09/12/2018.
 *
 * @since
 */
class StorageManagerTest extends Specification {
    Zoran zoran
    StorageManager storageManager

    @Shared Path modelPath
    @Shared Path localPath

    def setup() {
        zoran = new Zoran()
        zoran.storage = new StorageProperties("models", "local")
        storageManager = new StorageManager(zoran)
    }

    def "should return correct storage path for local"() {
        when: "get local path"
        localPath = storageManager.getLocalStoragePath()

        then:
        print localPath
        Files.exists(localPath)
        noExceptionThrown()
        0 * _
    }

    def "should return correct storage path for model"() {
        when: "get local path"
        modelPath = storageManager.getModelStoragePath()

        then:
        print modelPath
        Files.exists(modelPath)
        noExceptionThrown()
        0 * _
    }
}
