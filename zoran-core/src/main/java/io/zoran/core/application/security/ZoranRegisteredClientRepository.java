package io.zoran.core.application.security;

import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 18/11/2018.
 */
@Repository
public interface ZoranRegisteredClientRepository extends ClientRegistrationRepository {

}
