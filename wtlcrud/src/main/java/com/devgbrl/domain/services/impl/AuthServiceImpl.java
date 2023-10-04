package com.devgbrl.domain.services.impl;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.devgbrl.domain.services.AccountService;
import com.devgbrl.domain.services.AuthService;
import com.devgbrl.application.utils.Validator;
import com.devgbrl.domain.exceptions.BadArgumentException;
import com.devgbrl.domain.exceptions.NotAuthorizedException;
import com.devgbrl.domain.models.dtos.SignUpDTO;
import com.devgbrl.domain.models.entities.Account;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
class AuthServiceImpl implements AuthService {

    private final AccountService accountService;
    private final BCryptPasswordEncoder passwordEncoder;
    
    @Override
    public void signUp(SignUpDTO signUpDTO) {
        if (! signUpDTO.getPassword().equals(signUpDTO.getPasswordConfirm())) {
            throw new BadArgumentException("As senhas não coincidem");
        }

        if (signUpDTO.getUsername().length() < 4) {
            throw new BadArgumentException("O nome de usuário deve conter pelo menos 4 caracteres.");
        }

        if (signUpDTO.getPassword().length() < 6) {
            throw new BadArgumentException("A senha deve conter pelo menos 6 caracteres.");
        }

        if (accountService.existsByUsername(signUpDTO.getUsername())) {
            throw new BadArgumentException("Nome de usuário indisponível");
        }

        if (! Validator.isValidCPF(signUpDTO.getCpf())) {
            throw new BadArgumentException("CPF inválido");
        }

        if (! Validator.isValidEmailAddress(signUpDTO.getEmail())) {
            throw new BadArgumentException("Email inválido");
        }
        
        register(signUpDTO);
    }

    public Account register(SignUpDTO signUpDTO) {
        Account acountUser = signUpDTO.toEntity();
        acountUser.setPassword(passwordEncoder.encode(acountUser.getPassword()));

        return accountService.save(acountUser);
    }

    @Override
    public Account getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (Objects.isNull(authentication)) {
            throw new NotAuthorizedException("Você não tem permissão para realizar esta operação");
        }

        String currentPrincipalName = authentication.getName();
        return accountService.findByUsername(currentPrincipalName).orElseThrow(() -> new NotAuthorizedException("Você não tem permissão para realizar esta operação"));
    }

}