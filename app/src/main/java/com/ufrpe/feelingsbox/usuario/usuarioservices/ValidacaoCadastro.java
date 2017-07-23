package com.ufrpe.feelingsbox.usuario.usuarioservices;

import android.text.TextUtils;
import android.util.Patterns;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.ufrpe.feelingsbox.usuario.persistencia.UsuarioDAO;

public class ValidacaoCadastro {

    private UsuarioDAO usuarioDAO;

    public boolean validarCampos(String nome, String nick, String email, String nasc, String senha ) {
        boolean res = false;

        if (res = isCampoVazio(nome));{
        }
        if (res = !isNickValido(nick)); {
        }
        if (res = !isEmailValido(email)); {
        }
        if (res = !isNascValido(nasc)); {
        }
        if (res = !isSenhaValida(senha)); {
        }
        return !res;
    }


    private boolean isCampoVazio(String campo) {
         return (TextUtils.isEmpty(campo) || campo.trim().isEmpty());
        }

    private boolean isNickValido(String nick) {
        if (usuarioDAO.getUsuarioNick(nick) != null) {
            return true;
        }
        boolean resultado = (!isCampoVazio(nick) && !Patterns.EMAIL_ADDRESS.matcher(nick).matches());
        return resultado;
    }

    private boolean isEmailValido(String email) {
        if (usuarioDAO.getUsuarioEmail(email) != null){
            return true;
        }
        boolean resultado = (!isCampoVazio(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
        return resultado;
    }

    private boolean isNascValido(String nasc) {
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        try {

            Date date = formatador.parse(nasc);
            return true;

        }
        catch (ParseException e) {
            return false;
        }

    }

    private boolean isSenhaValida(String senha){
        if (isCampoVazio(senha)) {
            return false;
        } else {
            String rex = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,10})";
            return (senha.matches(rex));
        }

    }

}
