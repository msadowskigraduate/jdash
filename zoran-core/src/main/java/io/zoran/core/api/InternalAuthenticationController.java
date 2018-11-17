package io.zoran.core.api;

import io.zoran.core.domain.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 17.11.2018
 *
 * Used for Intra-(zoran internal) authentication.
 *
 */
@Controller
class InternalAuthenticationController {

    @GetMapping(value = {"/me","/user"})
    Mono<User> getCurrentUserData() {
        return Mono.empty();
    }
}
