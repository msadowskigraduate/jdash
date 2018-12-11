package io.zoran.infrastructure.configuration.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 09/12/2018.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class StorageProperties {
    private String path;
    private String local;
}
