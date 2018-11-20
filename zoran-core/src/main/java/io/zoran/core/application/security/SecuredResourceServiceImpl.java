package io.zoran.core.application.security;

import io.zoran.core.application.resource.ResourceService;
import io.zoran.core.application.user.ZoranUserService;
import io.zoran.core.domain.resource.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 20.11.2018
 *
 * Validates if current user is allowed to to the requested resources. Serves them if necessary.
 *
 */
@Service
@RequiredArgsConstructor
public class SecuredResourceServiceImpl implements SecurityResourceService {
    private final ZoranUserService zoranUserService;
    private final ResourceService resourceService;
}
