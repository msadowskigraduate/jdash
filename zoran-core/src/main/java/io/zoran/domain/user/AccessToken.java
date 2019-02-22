package io.zoran.domain.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

import java.time.Instant;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 15.02.2019
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessToken {
    private OAuth2AccessToken.TokenType tokenType;
    private String tokenValue;
    private Instant issuedAt;
    private Instant expiresAt;
}
