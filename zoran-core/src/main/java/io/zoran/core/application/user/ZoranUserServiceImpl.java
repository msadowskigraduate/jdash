package io.zoran.core.application.user;

import io.zoran.application.common.mappers.Mapper;
import io.zoran.application.common.mappers.MapperFactory;
import io.zoran.core.application.SecurityConstants;
import io.zoran.core.domain.impl.ZoranUser;
import io.zoran.core.domain.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import static io.zoran.core.application.SecurityConstants.UNAUTHORIZED_MESSAGE;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 17/11/2018.
 */
@Slf4j
@RequiredArgsConstructor
public class ZoranUserServiceImpl extends DefaultOAuth2UserService implements ZoranUserService {

    private final MapperFactory mapperFactory;
    private final UserStore userStore;

    @Override
    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null) {
            Object p = auth.getPrincipal();
            if(p != null && p instanceof OAuth2User) {
                Mapper<OAuth2User, ZoranUser> mapper =  mapperFactory.getMapper(ZoranUser.class, OAuth2User.class);
                return mapper.map((OAuth2User) p);
            }
        }
        throw new UnauthorizedUserException(UNAUTHORIZED_MESSAGE);
    }

    @Override
    public User authenticateUser(User user) {
        return authenticateUser(user.getId());
    }

    @Override
    public User authenticateUser(String userId) {
        ZoranUser zoranUser = userStore.findById(userId).orElseThrow(() -> new UnauthorizedUserException(UNAUTHORIZED_MESSAGE));
        zoranUser.setLastLogin(LocalDateTime.now());
        if(zoranUser.getState())
    }

    @Override
    public void revokeAccessFor(User user) {

    }

    @Override
    public void revokeAccessFor(String userId) {

    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        Objects.requireNonNull(user);
        ZoranUser zUser = (ZoranUser) mapperFactory.getMapper(ZoranUser.class, OAuth2User.class).map(user);
        return zUser;
    }
}
