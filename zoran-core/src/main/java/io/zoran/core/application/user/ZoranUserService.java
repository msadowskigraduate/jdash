package io.zoran.core.application.user;

import io.zoran.core.domain.user.User;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 17.11.2018
 */
public interface ZoranUserService {
    User getCurrentUser();
    User authenticateUser();
    void revokeAccessFor(User user);
    void revokeAccessFor(String userId);
}
