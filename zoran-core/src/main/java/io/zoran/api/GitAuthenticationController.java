package io.zoran.api;

import io.zoran.infrastructure.git.OAuthAuthorizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 29/12/2018.
 */
@Slf4j
@Controller
@RequiredArgsConstructor
class GitAuthenticationController {

    private final OAuthAuthorizationService service;

    @GetMapping(value = "/login")
    public void method(HttpServletResponse httpServletResponse) {
        httpServletResponse.setHeader("Location", service.gitAuthorizeAppRedirect());
        httpServletResponse.setStatus(302);
    }

    @GetMapping(value = "/login/oauth2", produces = APPLICATION_JSON_UTF8_VALUE)
    void getAccessCode(@RequestParam("code") String code) {
        log.info("Access code: " + code);
        service.exchangeAccessCodeForToken(code);
    }
}
