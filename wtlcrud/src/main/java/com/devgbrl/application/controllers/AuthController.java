package com.devgbrl.application.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.devgbrl.domain.exceptions.BadArgumentException;
import com.devgbrl.domain.models.dtos.SignUpDTO;
import com.devgbrl.domain.services.AuthService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @RequestMapping("sign-in")
    public String signIn(
        @RequestParam(defaultValue = "false") Boolean error, 
        @RequestParam(defaultValue = "false") Boolean registered,
        Model model
        ) {
        model.addAttribute("error", error);
        model.addAttribute("registered", registered);
        return "auth/sign-in";
    }

    @GetMapping("sign-up")
    public String getSignUp(SignUpDTO signUpDTO) {
        
        return "auth/sign-up";
    }

    @RequestMapping("sign-up")
    public String signUp(SignUpDTO signUpDTO, Model model) {
        try {
            authService.signUp(signUpDTO);
            return "redirect:/auth/sign-in?registered=1";
        } catch (BadArgumentException ex) {
            model.addAttribute("error", ex.getMessage());
        } catch (Exception ex) {
            model.addAttribute("error", "Algo realmente grave aconteceu...");
            return "redirect:/error";
        }
        
        model.addAttribute(signUpDTO);
        return "auth/sign-up";
    }

}
