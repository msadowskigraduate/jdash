package io.zoran.infrastructure.initializr;

import io.spring.initializr.generator.version.Version;
import lombok.experimental.UtilityClass;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 05.03.2019
 */
@UtilityClass
public class InitializrConst {
    public static final Version VERSION_1_5_0 = Version.parse("1.5.0.RELEASE");
    public static final Version VERSION_2_0_0 = Version.parse("2.0.0.RELEASE");
}
