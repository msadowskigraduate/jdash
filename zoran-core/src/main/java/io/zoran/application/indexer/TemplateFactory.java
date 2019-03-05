package io.zoran.application.indexer;

import io.zoran.domain.manifest.Manifest;
import io.zoran.domain.manifest.Template;

import java.util.List;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 19.02.2019
 */
public interface TemplateFactory {

    /**
     * Map template identifier slug to path in local store.
     * @param usedTemplate
     * @return path to where template is stored
     */
    Manifest getManifestForTemplateUsed(String usedTemplate);

    /**
     * Map template identifier slug to path in local store.
     * @param usedTemplate
     * @return path to where template is stored
     */
    Template getTemplateForSlug(String usedTemplate);

    /**
     * Map template identifier slug to path in local store.
     * @return path to where template is stored
     */
    List<Manifest> getAllTemplateData();
}
