package io.zoran.application.user;

import io.zoran.domain.impl.ZoranUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 19.11.2018
 */
@Repository
public interface UserStore extends MongoRepository<ZoranUser, String> {
    ZoranUser getByName(String name);
    ZoranUser findByLogin(String login);
}
