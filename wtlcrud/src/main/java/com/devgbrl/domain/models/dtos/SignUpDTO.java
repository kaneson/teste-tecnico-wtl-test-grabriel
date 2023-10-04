package com.devgbrl.domain.models.dtos;

import com.devgbrl.domain.models.entities.Account;

import lombok.Builder;
import org.modelmapper.ModelMapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpDTO {

    private String name;
    private String cpf;
    private String email;
    private String username;
    private String password;
    private String passwordConfirm;
    
    public Account toEntity() {
        return new ModelMapper().map(this, Account.class);
    }
}
