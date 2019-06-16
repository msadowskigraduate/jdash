package io.zoran.application.indexer;

import io.zoran.domain.indexer.Node;
import io.zoran.domain.indexer.Tree;
import io.zoran.domain.manifest.Manifest;
import io.zoran.domain.manifest.Template;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

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
            Optional<Node> node = getNodeForTemplate(usedTemplate, t);
            if(node.isPresent()) {
                return node.get().getManifest();
            }
        }
        return null;
    }

    @Override
    public List<Manifest> getManifestsForTemplateUsed(String usedTemplate) {
        ModelRepository<Tree> modelRepository = modelFactory.getDefaultStore();
        List<Tree> allTrees = modelRepository.getAll();

        for (Tree t : allTrees) {
            Optional<Node> node = getNodeForTemplate(usedTemplate, t);

            if (node.isPresent()) {
                return Stream.concat(Stream.of(node.get()), node.get().getChildren().stream())
                        .map(Node::getManifest)
                        .filter(Objects::nonNull)
                        .collect(toList());
            }
        }
        return null;
    }

    private Optional<Node> getNodeForTemplate(String usedTemplate, Tree tree) {
        List<Node> node = tree.getNodesWithManifests();
        if (!node.isEmpty()) {
            return node.stream()
                    .filter(x -> x.getManifest().getTemplate().stream().anyMatch(y ->   y.getName().equals(usedTemplate)))
                    .findFirst();
        }
        return Optional.empty();
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

    @Override
    public List<Manifest> getAllTemplateData() {
        ModelRepository<Tree> modelRepository = modelFactory.getDefaultStore();
        List<Tree> allTrees = modelRepository.getAll();

        return allTrees.stream()
                .map(x -> x.getAllManifests(manifest -> true))
                .flatMap(Collection::stream)
                .collect(toList());
    }
}
