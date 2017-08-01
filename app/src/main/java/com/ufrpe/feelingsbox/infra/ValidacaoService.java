package com.ufrpe.feelingsbox.infra;

import android.text.TextUtils;
import android.util.Patterns;


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
        return(FormataData.dataExtiste(nasc) && FormataData.dataMenorOuIgualQueAtual(nasc));
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