package com.devgbrl.domain.services;

import com.devgbrl.domain.models.dtos.SignUpDTO;
import com.devgbrl.domain.models.entities.Account;

public interface AuthService {

    void signUp(SignUpDTO signUpDTO);
    Account register(SignUpDTO signUpDTO);

    Account getCurrentUser();

}