package com.devgbrl.infrastructure.data.repositories;

import java.util.Optional;

import com.devgbrl.domain.models.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByUsername(String username);

    boolean existsByUsername(String username);

    Optional<Account> findByEmail(String email);
}
