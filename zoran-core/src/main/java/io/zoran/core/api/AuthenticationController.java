package io.zoran.core.api;

import io.zoran.application.common.mappers.Mapper;
import io.zoran.application.common.mappers.MapperFactory;
import io.zoran.core.application.user.ZoranUserService;
import io.zoran.core.domain.impl.ZoranUser;
import io.zoran.core.domain.user.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 17.11.2018
 */
@Slf4j
@Controller
@RequiredArgsConstructor
class AuthenticationController {

    private final MapperFactory mapperFactory;
    private final ZoranUserService zoranUserService;

    @GetMapping(value = "/me", produces = APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody UserDto index(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient,
                                       @AuthenticationPrincipal OAuth2User oauth2User) {
        Mapper<OAuth2User, UserDto> map = mapperFactory.getMapper(OAuth2User.class, UserDto.class);
        return map.map(oauth2User);
    }

    @GetMapping(value = "/userinfo", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserDto> index() {
        Mapper<ZoranUser, UserDto> map = mapperFactory.getMapper(UserDto.class, ZoranUser.class);
        return ResponseEntity.ok(map.map((ZoranUser) zoranUserService.getCurrentUser()));
    }
}
