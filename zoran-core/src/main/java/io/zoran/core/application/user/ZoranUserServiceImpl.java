package io.zoran.core.application.user;

import io.zoran.core.domain.user.User;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 17/11/2018.
 */
public class ZoranUserServiceImpl implements ZoranUserService {

    @Override
    public User getCurrentUser() {
        return null;
    }

    @Override
    public User authenticateUser() {
        return null;
    }

    @Override
    public void revokeAccessFor(User user) {

    }

    @Override
    public void revokeAccessFor(String userId) {

    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        return null;
    }
}
