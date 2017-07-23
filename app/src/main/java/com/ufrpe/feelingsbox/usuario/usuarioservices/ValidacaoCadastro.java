package com.ufrpe.feelingsbox.usuario.usuarioservices;

import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ufrpe.feelingsbox.R;
import com.ufrpe.feelingsbox.usuario.persistencia.UsuarioDAO;

import static com.ufrpe.feelingsbox.R.id.edtNome;

public class ValidacaoCadastro {

    private UsuarioDAO usuarioDAO;

    public boolean isCampoVazio(String campo) {
         return (TextUtils.isEmpty(campo) || campo.trim().isEmpty());
        }

    public boolean isNickValido(String nick) {
        if (usuarioDAO.getUsuarioNick(nick) != null) {
            return true;
        }
        boolean resultado = (!isCampoVazio(nick) && !Patterns.EMAIL_ADDRESS.matcher(nick).matches());
        return resultado;
    }

    public boolean isEmailValido(String email) {
        if (usuarioDAO.getUsuarioEmail(email) != null){
            return true;
        }
        return (!isCampoVazio(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches())&&(usuarioDAO.getUsuarioEmail(email) != null);

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
