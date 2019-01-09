package io.zoran.api;

import io.zoran.application.common.mappers.Mapper;
import io.zoran.application.common.mappers.MapperFactory;
import io.zoran.application.user.ZoranUserService;
import io.zoran.domain.impl.ZoranUser;
import io.zoran.domain.user.User;
import io.zoran.domain.user.UserDto;
import io.zoran.infrastructure.SecuredBlock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
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

    private final MapperFactory mapperFactory;
    private final ZoranUserService zoranUserService;

    @SecuredBlock
    @GetMapping(value = "/me", produces = APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody UserDto index(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient,
                                       @AuthenticationPrincipal OAuth2User oauth2User) {
        Mapper<OAuth2User, UserDto> map = mapperFactory.getMapper(UserDto.class, OAuth2User.class);
        return map.map(oauth2User);
    }

    @GetMapping(value = "/userinfo", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserDto> index() {
        Mapper<ZoranUser, UserDto> map = mapperFactory.getMapper(UserDto.class, ZoranUser.class);
        return ResponseEntity.ok(map.map((ZoranUser) zoranUserService.getCurrentUser()));
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
        Mapper<ZoranUser, UserDto> map = mapperFactory.getMapper(UserDto.class, ZoranUser.class);
        return map.map((ZoranUser) user);
    }

//    @GetMapping(value = "/gitUser")
//    String getU(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient) {
//        log.info(authorizedClient.toString());
//        return gitProxyService.getAuthResponse(TokenAppender.getRequestHeaderToken(authorizedClient.getAccessToken()));
//    }
}
