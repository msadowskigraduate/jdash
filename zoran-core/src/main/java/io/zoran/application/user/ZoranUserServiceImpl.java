package io.zoran.application.user;

import io.zoran.application.audit.Audited;
import io.zoran.domain.audit.AuditAction;
import io.zoran.domain.impl.ZoranUser;
import io.zoran.domain.user.User;
import io.zoran.domain.user.UserState;
import io.zoran.infrastructure.SecuredBlock;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.security.oauth2.common.exceptions.UserDeniedAuthorizationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import static io.zoran.application.common.GitUserConstants.ID;
import static io.zoran.application.user.SpecialUserMapper.getAnonymousUser;
import static io.zoran.domain.user.UserState.ACCESS_REVOKED;
import static io.zoran.domain.user.UserState.ACTIVE;
import static io.zoran.infrastructure.exception.ExceptionMessageConstants.UNAUTHORIZED_MESSAGE;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 17/11/2018.
 */
@SecuredBlock
@Slf4j
@RequiredArgsConstructor
public class ZoranUserServiceImpl extends DefaultOAuth2UserService implements ZoranUserService {
    private final UserStore userStore;

    @Override
    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null) {
            Object p = auth.getPrincipal();
            if(p != null && p instanceof ZoranUser) {
                String id = ((ZoranUser) p).getId();
                return userStore.findById(id).orElseThrow(() -> new UnauthorizedUserException(UNAUTHORIZED_MESSAGE));
            }
            if(p instanceof String && p.equals("anonymousUser")) {
                return getAnonymousUser();
            }
        }
        throw new UnauthorizedUserException(UNAUTHORIZED_MESSAGE);
    }

    @Override
    public String authenticateAndGetUserId() {
        User user = getCurrentUser();
        return authenticateUser(user.getId()).getId();
    }

    @Override
    public User authenticateUser(User user) {
        return authenticateUser(user.getId());
    }

    @Override
    public User authenticateUser(@NonNull String userId) {
        ZoranUser zoranUser = userStore.findById(userId).orElseThrow(() -> new UnauthorizedUserException(UNAUTHORIZED_MESSAGE));
        zoranUser.setLastLogin(LocalDateTime.now());
        if(zoranUser.getState().equals(ACTIVE)) {
            return zoranUser;
        }
        throw new UnauthorizedUserException(UNAUTHORIZED_MESSAGE);
    }

    @Override
    public void revokeAccessFor(User user) {
        revokeAccessFor(user.getId());
    }

    @Override
    @Audited(AuditAction.UDER_ACCESS_REVOKED)
    public void revokeAccessFor(@NonNull String userId) {
        ZoranUser zoranUser = userStore.findById(userId).orElseThrow(() -> new UnauthorizedUserException(UNAUTHORIZED_MESSAGE));
        zoranUser.setLastLogin(LocalDateTime.now());
        upSertUser(zoranUser.revokeAccess());
    }

    @Override
    public User activateUser() {
        ZoranUser u = (ZoranUser) getCurrentUser();
        if(u.getState() != UserState.ACCESS_REVOKED) {
            u = u.activate();
            upSertUser(u);
        }
        return u;
    }

    @Override
    @Audited(AuditAction.UDER_LOGIN)
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        Objects.requireNonNull(user);
        return authorizeOAuthUser(user);
    }

    private ZoranUser authorizeOAuthUser(OAuth2User user) {
        String id = String.valueOf(user.getAttributes().get(ID));
        ZoranUser zoranUser = getUserById(id).orElseGet(() -> newUser(user));

        if(ACCESS_REVOKED.equals(zoranUser.getState())) {
            throw new UserDeniedAuthorizationException(UNAUTHORIZED_MESSAGE);
        }

        return upSertUser(zoranUser);
    }

    private Optional<ZoranUser> getUserById(String id) {
        return userStore.findById(id);
    }

    private ZoranUser newUser(OAuth2User user) {
        ZoranUser zoranUser = ZoranUser.from(user.getAttributes(), user.getAuthorities());
        return userStore.save(zoranUser);
    }

    private ZoranUser upSertUser(ZoranUser user) {
        userStore.deleteById(user.getId());
        user.setLastLogin(LocalDateTime.now());
        return userStore.save(user);
    }
}
