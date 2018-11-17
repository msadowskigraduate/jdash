package io.zoran.core.domain.impl;

import io.zoran.core.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.time.LocalDateTime;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 16.11.2018
 */
@Document
@RequiredArgsConstructor
public class ZoranUser implements User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final String id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String login;
    private final LocalDateTime lastLogin;
}
