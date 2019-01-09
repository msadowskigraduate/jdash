package io.zoran.application.user;

import io.zoran.domain.user.User;
import io.zoran.infrastructure.NoSecurity;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

import static io.zoran.application.user.SpecialUserMapper.getSuperUser;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 23.11.2018
 */
@NoSecurity
public class DisabledSecurityUserService extends DefaultOAuth2UserService implements ZoranUserService {
    @Override
    public User getCurrentUser() {
        return getSuperUser();
    }

    @Override
    public String authenticateAndGetUserId() {
        return getCurrentUser().getId();
    }

    @Override
    public User authenticateUser(User user) {
        return getCurrentUser();
    }

    @Override
    public User authenticateUser(String userId) {
        return getCurrentUser();
    }

    @Override
    public void revokeAccessFor(User user) {

    }

    @Override
    public void revokeAccessFor(String userId) {

    }

    @Override
    public User activateUser() {
        return getCurrentUser();
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        return null;
    }


}
