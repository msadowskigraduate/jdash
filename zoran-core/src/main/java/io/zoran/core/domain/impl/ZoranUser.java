package io.zoran.core.domain.impl;

import io.zoran.core.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.time.LocalDateTime;
import java.util.*;

import static io.zoran.core.application.common.GitConstants.*;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 16.11.2018
 */
@Builder
@Document
@Getter
public class ZoranUser implements User {
    @Id
    private String id;
    private String name;
    private String login;
    private String email;

    @Setter
    private LocalDateTime lastLogin;

    private Map<String, Object> attributes;
    private List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Map<String, Object> getAttributes() {
        if (this.attributes == null) {
            this.attributes = new HashMap<>();
            this.attributes.put(ID, this.getId());
            this.attributes.put(NAME, this.getName());
            this.attributes.put(LOGIN, this.getLogin());
            this.attributes.put(EMAIL, this.getEmail());
        }
        return attributes;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public static ZoranUser from(Map<String, Object> parameters, Collection<? extends GrantedAuthority> authorities) {
        return ZoranUser.builder()
                .id(String.valueOf(parameters.get(ID)))
                .name(String.valueOf(parameters.get(NAME)))
                .login(String.valueOf(parameters.get(LOGIN)))
                .email(String.valueOf(parameters.get(EMAIL)))
                .attributes(parameters)
                .authorities(new ArrayList<>(authorities))
                .lastLogin(LocalDateTime.now())
                .build();
    }
}
