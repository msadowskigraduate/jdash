package io.zoran.application.indexer;

import io.zoran.domain.indexer.Node;
import io.zoran.domain.indexer.Tree;
import io.zoran.domain.manifest.Manifest;
import io.zoran.domain.manifest.Template;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 19.02.2019
 */
@Service
@RequiredArgsConstructor
public class DefaultTemplateFactory implements TemplateFactory {
    private final ModelFactory modelFactory;

    @Override
    public Manifest getManifestForTemplateUsed(String usedTemplate) {
        ModelRepository<Tree> modelRepository = modelFactory.getDefaultStore();
        List<Tree> allTrees = modelRepository.getAll();

        for (Tree t : allTrees) {
            Optional<Node> node = t.getNodeById(usedTemplate);

            if (node.isPresent()) {
                return node.get().getManifest();
            }
        }
        return null;
    }

    @Override
    public Template getTemplateForSlug(String usedTemplate) {
        ModelRepository<Tree> modelRepository = modelFactory.getDefaultStore();
        List<Tree> allTrees = modelRepository.getAll();

        for (Tree t : allTrees) {
            return t.getAllManifests(manifest -> true)
                    .stream()
                    .map(Manifest::getTemplate)
                    .flatMap(Collection::stream)
                    .filter(x -> x.getName().equals(usedTemplate))
                    .findFirst()
                    .orElseGet(() -> null);
        }
        return null;
    }
}
