package io.zoran.api;

import io.zoran.application.common.mappers.BiMapper;
import io.zoran.application.common.mappers.UserMapper;
import io.zoran.application.user.ZoranUserService;
import io.zoran.domain.impl.ZoranUser;
import io.zoran.domain.user.User;
import io.zoran.domain.user.UserDto;
import io.zoran.infrastructure.SecurityEnabled;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 17.11.2018
 */
@Slf4j
@RestController
@RequiredArgsConstructor
class AuthenticationController {

    private final BiMapper<OAuth2User, OAuth2AccessToken, UserDto> dtoMapper;
    private final ZoranUserService zoranUserService;
    private final UserMapper mapper;

    @SecurityEnabled
    @GetMapping(value = "/me", produces = APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody UserDto index(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient,
                                       @AuthenticationPrincipal OAuth2User oauth2User) {
        return dtoMapper.map(oauth2User, authorizedClient.getAccessToken());
    }

    @GetMapping(value = "/userinfo", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserDto> index() {
        return ResponseEntity.ok(mapper.map((ZoranUser) zoranUserService.getCurrentUser()));
    }

    @GetMapping(value = "/banme", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity revokeAccessForMe() {
        User user = zoranUserService.getCurrentUser();
        zoranUserService.revokeAccessFor(user.getId());
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/iaccept", produces = APPLICATION_JSON_UTF8_VALUE)
    public UserDto acceptedTOS() {
        User user = zoranUserService.activateUser();
        return mapper.map((ZoranUser) user);
    }
}
