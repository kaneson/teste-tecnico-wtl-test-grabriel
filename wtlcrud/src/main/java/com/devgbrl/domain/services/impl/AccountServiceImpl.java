package com.devgbrl.domain.services.impl;

import com.devgbrl.domain.models.entities.Account;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.devgbrl.domain.services.AccountService;
import com.devgbrl.infrastructure.data.repositories.AccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService, UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = findByEmail(username).orElse(null);

        if (account == null) {
            account = findByUsername(username).orElseThrow(() -> new IllegalArgumentException("Usuário ou senha inválida."));
        }

        return account;
    }

    @Override
    public boolean existsByUsername(String username) {
        return accountRepository.existsByUsername(username);
    }

    @Override
    public Account save(Account acountUser) {
        return accountRepository.save(acountUser);
    }

    @Override
    public Optional<Account> findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    public Optional<Account> findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

}
