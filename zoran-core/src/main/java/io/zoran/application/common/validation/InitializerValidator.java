package io.zoran.application.common.validation;

import io.spring.initializr.generator.version.Version;
import io.spring.initializr.metadata.DefaultMetadataElement;
import io.spring.initializr.metadata.InitializrMetadata;
import io.spring.initializr.metadata.Type;
import io.zoran.infrastructure.exception.ZoranValidationException;
import lombok.experimental.UtilityClass;

import static io.zoran.infrastructure.initializr.InitializrConst.VERSION_1_5_0;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 05.03.2019
 */
@UtilityClass
class InitializerValidator {

    boolean validateSpringBootVersion(String sbVersion) {
        //Not every project needs to be sb-based.
        if(sbVersion != null) {
            Version bootVersion = Version.safeParse(sbVersion);
            if (bootVersion != null && bootVersion.compareTo(VERSION_1_5_0) < 0) {
                throw new ZoranValidationException("Invalid Spring Boot version "
                        + bootVersion + " must be 1.5.0 or higher");
            }
            return true;
        }
        return true;
    }

    boolean validateType(String type, InitializrMetadata metadata) {
        if (type != null) {
            Type typeFromMetadata = metadata.getTypes().get(type);
            if (typeFromMetadata == null) {
                throw new ZoranValidationException(
                        "Unknown type '" + type + "' check project metadata");
            }
            if (!typeFromMetadata.getTags().containsKey("build")) {
                throw new ZoranValidationException("Invalid type '" + type
                        + "' (missing build tag) check project metadata");
            }

            return true;
        }
        return false;
    }

    boolean validateLanguage(String language, InitializrMetadata metadata) {
        if (language != null) {
            language = language.toLowerCase();
            DefaultMetadataElement languageFromMetadata = metadata.getLanguages()
                                                                  .get(language);
            if (languageFromMetadata == null) {
                throw new ZoranValidationException(
                        "Unknown language '" + language + "' check project metadata");
            }

            return true;
        }
        return false;
    }
}