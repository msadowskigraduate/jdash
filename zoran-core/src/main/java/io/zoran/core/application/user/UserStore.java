package io.zoran.core.application.user;

import io.zoran.core.domain.impl.ZoranUser;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 19.11.2018
 */
interface UserStore extends MongoRepository<ZoranUser, String> {
}
