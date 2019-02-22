package io.zoran.domain.git;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 23/12/2018.
 */
@Data
public class License {
    @Id
    @GeneratedValue
    private String key;
    private String name;
    private String spdxID;
    private String url;
}
