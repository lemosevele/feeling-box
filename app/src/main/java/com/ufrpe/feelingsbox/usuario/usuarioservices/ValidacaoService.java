package com.ufrpe.feelingsbox.usuario.usuarioservices;

import android.text.TextUtils;
import android.util.Patterns;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ufrpe.feelingsbox.usuario.persistencia.UsuarioDAO;

public class ValidacaoCadastro {

    public boolean isCampoVazio(String campo) {
         return (TextUtils.isEmpty(campo) || campo.trim().isEmpty());
        }

    public boolean isNickValido(String nick) {
        return  (!isCampoVazio(nick) && !Patterns.EMAIL_ADDRESS.matcher(nick).matches());
    }

    public boolean isEmailValido(String email) {
        return (!isCampoVazio(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());

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
            String rex = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,10})";
            return (senha.matches(rex));
        }

    }

}
