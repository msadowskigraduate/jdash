package io.zoran.application.manifest


import io.zoran.domain.manifest.Manifest
import io.zoran.domain.manifest.ResourceType
import io.zoran.domain.manifest.YMLManifest
import io.zoran.domain.resource.ResourceVisibility
import io.zoran.infrastructure.exception.ManifestReaderException
import org.apache.commons.lang.StringUtils
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import java.nio.file.Files
import java.nio.file.Path
/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 08/12/2018.
 *
 * @since
 */
class YMLManifestReaderTest extends Specification {

    ManifestReader yamlReader

    @Shared
    Path fileYML = new File("src/test/resources/test_manifest.yml").toPath()
    @Shared
    Path fileYAML = new File("src/test/resources/test_manifest2.yaml").toPath()
    @Shared
    Path fileInvalid = new File("src/test/resources/invalid.invalid").toPath()

    def setup() {
        yamlReader = new YMLManifestReader()
    }

    @Unroll
    def "should return #expectedResult when file is readable"() {
        when: "check if can read file"
        def result = yamlReader.canRead(path)

        then:
        print(Files.readAllLines(path))
        result == expectedResult

        where:
        path        | expectedResult
        fileInvalid | false
        fileYAML    | true
        fileYML     | true
    }

    @Unroll
    def "should correctly read and parse yaml-based manifests"() {
        when: "reads manifest file"
        Manifest manifest = yamlReader.read(path)

        then:
        with(manifest) {
            manifest instanceof YMLManifest
            manifest.asType(YMLManifest).getName() == "testNameYaml"
            manifest.asType(YMLManifest).getLead() == "This is a test file"
            manifest.asType(YMLManifest).getVersion() == "0.0.1-TEST"
            manifest.asType(YMLManifest).getOwner() == "FakeName FakeSurname"
            manifest.asType(YMLManifest).getPath() == "path/path/path"
            manifest.asType(YMLManifest).getVisibility() == ResourceVisibility.PUBLIC
            manifest.asType(YMLManifest).getType() == ResourceType.CLASS
            manifest.asType(YMLManifest).getTemplate().size() == 2
        }

        where:
        path        | _
        fileYAML    | _
        fileYML     | _
    }

    @Unroll
    def "should correctly read and parse yaml-based manifests from input stream"() {
        when: "reads manifest file"
        InputStream stream = path.newInputStream()
        Manifest manifest = yamlReader.read(stream)

        then:
        with(manifest) {
            manifest instanceof YMLManifest
            manifest.asType(YMLManifest).getName() == "testNameYaml"
            manifest.asType(YMLManifest).getLead() == "This is a test file"
            manifest.asType(YMLManifest).getVersion() == "0.0.1-TEST"
            manifest.asType(YMLManifest).getOwner() == "FakeName FakeSurname"
            manifest.asType(YMLManifest).getPath() == "path/path/path"
            manifest.asType(YMLManifest).getVisibility() == ResourceVisibility.PUBLIC
            manifest.asType(YMLManifest).getType() == ResourceType.CLASS
            manifest.asType(YMLManifest).getTemplate().size() == 2
        }

        where:
        path        | _
        fileYAML    | _
        fileYML     | _
    }

    @Unroll
    def "should correctly read and parse yaml-based manifests from string"() {
        when: "reads manifest file"
        String yml = StringUtils.join(path.readLines(), "\n")
        Manifest manifest = yamlReader.read(yml)

        then:
        with(manifest) {
            manifest instanceof YMLManifest
            manifest.asType(YMLManifest).getName() == "testNameYaml"
            manifest.asType(YMLManifest).getLead() == "This is a test file"
            manifest.asType(YMLManifest).getVersion() == "0.0.1-TEST"
            manifest.asType(YMLManifest).getOwner() == "FakeName FakeSurname"
            manifest.asType(YMLManifest).getPath() == "path/path/path"
            manifest.asType(YMLManifest).getVisibility() == ResourceVisibility.PUBLIC
            manifest.asType(YMLManifest).getType() == ResourceType.CLASS
            manifest.asType(YMLManifest).getDependencies().size() == 3
        }

        where:
        path        | _
        fileYAML    | _
        fileYML     | _
    }

    def "should rise excpetion when trying to parse invalid file"() {
        when: "reads manifest file"
        Manifest manifest = yamlReader.read(fileInvalid)

        then:
        thrown(ManifestReaderException)
    }
}
