package io.zoran.domain.git;

import lombok.Data;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 23/12/2018.
 */
@Data
public class License {
    private String key;
    private String name;
    private String spdxID;
    private String url;
    private String nodeID;
}
