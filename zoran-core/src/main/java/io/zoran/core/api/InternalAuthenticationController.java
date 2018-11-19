package io.zoran.core.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 17.11.2018
 *
 * Used for Intra-(zoran internal) authentication.
 *
 */
@Controller
public class InternalAuthenticationController {

    @GetMapping
    void authorize() {

    }
}
