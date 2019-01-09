package io.zoran.domain.impl;

import io.zoran.domain.user.User;
import io.zoran.domain.user.UserState;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

import static io.zoran.application.common.GitUserConstants.*;
import static io.zoran.domain.user.UserState.*;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 16.11.2018
 */
@Data
@Builder
@Document
public class ZoranUser implements User {
    @Id
    private String id;
    private String name;
    private String login;
    private String email;
    private String avatarUrl;
    private String repoUrl;
    private String htmlUrl;
    private String userType;
    @Builder.Default private UserState state = UserState.ACTIVE;
    private LocalDateTime lastLogin;

    public static ZoranUser from(Map<String, Object> parameters, Collection<? extends GrantedAuthority> authorities) {
        return ZoranUser.builder()
                .id(String.valueOf(parameters.get(ID)))
                .name(String.valueOf(parameters.get(NAME)))
                .login(String.valueOf(parameters.get(LOGIN)))
                .email(String.valueOf(parameters.get(EMAIL)))
                .avatarUrl(String.valueOf(parameters.get(AVATAR_URL)))
                .repoUrl(String.valueOf(parameters.get(REPOS_URL)))
                .htmlUrl(String.valueOf(parameters.get(HTML_URL)))
                .userType(String.valueOf(parameters.get(TYPE)))
                .state(ACTIVE)
                .lastLogin(LocalDateTime.now())
                .build();
    }

    @Override
    public UserState getState() {
        return state;
    }

    private ZoranUser withState(UserState state) {
        this.state = state;
        return this;
    }

    public ZoranUser revokeAccess() {
        return withState(ACCESS_REVOKED);
    }

    public ZoranUser setInactive() {
        return withState(INACTIVE);
    }

    public ZoranUser activate() {
        return withState(ACTIVE);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }
}
