package com.devgbrl.application.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    private static final int[] cpfWeight = { 11, 10, 9, 8, 7, 6, 5, 4, 3, 2 };

    public static boolean isValidEmailAddress(String email) {
        Pattern pattern = Pattern.compile("^.+@.+\\..+$");
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }


    private static int calcularDigito(String str, int[] peso) {
        int soma = 0;
        for (int indice = str.length() - 1, digito; indice >= 0; indice--) {
            digito = Integer.parseInt(str.substring(indice, indice + 1));
            soma += digito * peso[peso.length - str.length() + indice];
        }
        soma = 11 - soma % 11;
        return soma > 9 ? 0 : soma;
    }

    public static boolean isValidCPF(String cpf) {
        if ((cpf == null) || (cpf.length() != 11))
            return false;

        Integer firstDigit = calcularDigito(cpf.substring(0, 9), cpfWeight);
        Integer lastDigit = calcularDigito(cpf.substring(0, 9) + firstDigit, cpfWeight);
        return cpf.equals(cpf.substring(0, 9) + firstDigit.toString() + lastDigit.toString());
    }

}
