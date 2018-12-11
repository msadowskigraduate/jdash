package io.zoran.core.application.user;

import io.zoran.core.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 23.11.2018
 */
@RequiredArgsConstructor
class UserNameIdMapper {
    private final UserStore userStore;

    String getNameForId(String id) {
        User user = userStore.findById(id).orElseThrow(() -> new UsernameNotFoundException("No such user."));
        return user.getName();
    }

    String getIdForLogin(String login) {
        User user = userStore.findByLogin(login);
        if(user == null) {
            throw new UsernameNotFoundException("No such user.");
        }
        return user.getName();
    }

    String getIdForName(String id) {
        User user = userStore.getByName(id);
        if(user == null) {
            throw new UsernameNotFoundException("No such user.");
        }
        return user.getName();
    }
}