package io.zoran.core.application.user;

import io.zoran.core.domain.impl.ZoranUser;
import io.zoran.core.domain.user.User;
import io.zoran.core.infrastructure.NoSecurity;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

import static io.zoran.core.domain.user.UserState.ACTIVE;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 23.11.2018
 */
@NoSecurity
public class DisabledSecurityUserService implements ZoranUserService {
    @Override
    public User getCurrentUser() {
        return null;
    }

    @Override
    public String authenticateAndGetUserId() {
        return null;
    }

    @Override
    public User authenticateUser(User user) {
        return null;
    }

    @Override
    public User authenticateUser(String userId) {
        return null;
    }

    @Override
    public void revokeAccessFor(User user) {

    }

    @Override
    public void revokeAccessFor(String userId) {

    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        return null;
    }

    private ZoranUser getSuperUser() {
        return ZoranUser.builder()
                        .id("ADMIN")
                        .state(ACTIVE)
                        .userType("ADMIN")
                        .login("ADMIN")
                        .build();
    }
}
