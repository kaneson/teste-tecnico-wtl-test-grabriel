package com.devgbrl.domain.services;

import java.util.Optional;

import com.devgbrl.domain.models.entities.Account;

public interface AccountService {

    boolean existsByUsername(String username);

    Account save(Account acountUser);

    Optional<Account> findByUsername(String username);

}