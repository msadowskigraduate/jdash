package io.zoran.domain.git;

import lombok.Data;

import java.util.List;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 23/12/2018.
 */
@Data(staticConstructor = "of")
public class LicenseResponse {
    private final List<License> licenseList;
}
