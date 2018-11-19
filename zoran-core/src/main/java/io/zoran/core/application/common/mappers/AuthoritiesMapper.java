package io.zoran.core.application.common.mappers;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 17/11/2018.
 */
@MapperDefinition(input = GrantedAuthority.class, output = String.class)
public class AuthoritiesMapper implements Mapper<GrantedAuthority, String> {

    @Override
    public String map(GrantedAuthority grantedAuthority) {
        return grantedAuthority.getAuthority();
    }
}
