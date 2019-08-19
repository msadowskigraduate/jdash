package io.zoran.infrastructure.configuration.metadata;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 16.06.2019
 */
@Component
@RequiredArgsConstructor
public class MetadataService {
    private final List<MetadataProvider> providers;
    private Map<String, List<SingleCapability>> allCapabilities;

    @PostConstruct
    public void initialLoad() {
        this.allCapabilities = new HashMap<>();
        for(MetadataProvider provider : providers) {
            allCapabilities.put(provider.getLoaderId(), provider.loadCapabilities());
        }
    }

    public List<SingleCapability> getCapabilitiesForId(String id){
        if(id == null) {
            return this.getAllCapabilities()
                       .values()
                       .stream()
                       .flatMap(Collection::stream)
                       .collect(toList());
        }
        return this.allCapabilities.get(id);
    }

    public Map<String, List<SingleCapability>> getAllCapabilities() {
        return this.allCapabilities;
    }
}
