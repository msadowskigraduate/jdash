package io.zoran.application.common.mappers

import io.zoran.domain.impl.ZoranUser
import io.zoran.domain.user.UserDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootContextLoader
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification
/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 17.11.2018
 */
@SpringBootTest
@ContextConfiguration(loader = SpringBootContextLoader.class, classes = MapperFactory.class)
class MapperFactoryTest extends Specification {

    @Autowired
    MapperFactory underTest

    def "Should return correct mapper"() {
        given:


        when:
        Mapper mapper = underTest.getMapper(UserDto, ZoranUser)

        then:
        mapper != null
        mapper instanceof UserMapper
    }
}
