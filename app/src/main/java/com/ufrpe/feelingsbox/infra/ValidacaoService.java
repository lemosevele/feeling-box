package com.ufrpe.feelingsbox.infra;

import android.text.TextUtils;
import android.util.Patterns;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ValidacaoService {

    public boolean isCampoVazio(String campo) {
         return (TextUtils.isEmpty(campo) || campo.trim().isEmpty());
        }
    public boolean isEmail(String campo){
        return (Patterns.EMAIL_ADDRESS.matcher(campo).matches());
    }

    public boolean isNickValido(String nick) {
        return  (!isCampoVazio(nick) && !isEmail(nick));
    }

    public boolean isEmailValido(String email) {
        return (!isCampoVazio(email) && isEmail(email));
    }

    public boolean isNascValido(String nasc) {
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        try {

            Date date = formatador.parse(nasc);
            return true;

        }
        catch (ParseException e) {
            return false;
        }
    }

    public boolean isSenhaValida(String senha){
        if (isCampoVazio(senha)) {
            return false;
        } else {
            String rex = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,12})";
            return (senha.matches(rex));
        }
    }
}