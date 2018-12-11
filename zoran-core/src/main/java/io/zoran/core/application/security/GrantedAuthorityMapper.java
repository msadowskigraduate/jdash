package io.zoran.core.application.security;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.GrantedAuthority;

import java.util.*;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 19.11.2018
 */
@UtilityClass
public class GrantedAuthorityMapper {
    public List<String> map(Collection<? extends GrantedAuthority> authorities) {
        List<String> authoritiesSet = new ArrayList<>();
        for(GrantedAuthority ga : authorities) {
            authoritiesSet.add(ga.getAuthority());
        }
        return authoritiesSet;
    }
}
