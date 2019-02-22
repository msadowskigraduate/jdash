package io.zoran.infrastructure.services;

import io.zoran.domain.manifest.Manifest;
import lombok.experimental.UtilityClass;

import java.util.function.Predicate;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 19.02.2019
 */
@UtilityClass
public class ManifestUtils {
    public static Predicate<Manifest> getAllManifests() {
        return x -> true;
    }
}
