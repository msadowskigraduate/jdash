package io.zoran.application.template.processors

import io.zoran.application.ResourceTestSpec
import io.zoran.application.template.TemplateProcessor
import io.zoran.application.template.context.TemplateClassContext
import io.zoran.application.template.context.TemplateContextTuple
import io.zoran.application.template.resolvers.OutputPathResolver
import io.zoran.domain.manifest.Location
import io.zoran.domain.manifest.Template
import io.zoran.domain.resource.Resource
import org.apache.tomcat.util.http.fileupload.FileUtils

import java.nio.file.Paths
/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 19.02.2019
 */
class MustacheTemplateProcessorTest extends ResourceTestSpec {
    TemplateProcessor mustacheProcessor
    TemplateClassContext context

    def "should compile template to console correctly"() {
        given:
        Resource resource = getSampleResource("fakeId")
        Template t = Template.builder().preferredLocation(Location.SERVICES).build();
        String outputPath = OutputPathResolver.resolve("io.zoran", resource, "java", t.getPreferredLocation())
        mustacheProcessor = new MustacheTemplateProcessor()
        context = TemplateClassContext.builder()
                                      .projectName(null)
                                      .packageName("io.zoran")
                                      .templateFile(Paths.get("src/test/resources/templateTest.java.mustache"))
                                      .outputPath(outputPath)
                                      .additional(TemplateContextTuple.of("param1_name", "testVar1"),
                                                  TemplateContextTuple.of("param2_name", "testVar2"))
                                      .build()

        when:
        mustacheProcessor.compile(context)

        then:
        noExceptionThrown()
    }

    def setup() {
        FileUtils.deleteDirectory(Paths.get("fakeProjectNAme").toFile())
    }

    def cleanup() {
        FileUtils.deleteDirectory(Paths.get("fakeProjectNAme").toFile())
    }
}
