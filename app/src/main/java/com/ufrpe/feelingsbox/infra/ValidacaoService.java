package com.ufrpe.feelingsbox.infra;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;

import com.ufrpe.feelingsbox.usuario.persistencia.UsuarioDAO;


public class ValidacaoService {
    private UsuarioDAO usuarioDAO;
    private static final int TAMANHO_DATA = 10;

    public ValidacaoService(Context context){
        this.usuarioDAO = new UsuarioDAO(context);
    }

    public boolean isCampoVazio(String campo) {
         return (TextUtils.isEmpty(campo) || campo.trim().isEmpty());
    }

    public boolean isEmail(String campo){
        return (Patterns.EMAIL_ADDRESS.matcher(campo).matches());
    }

    public boolean isNickValido(String nick) {
        return  (!isCampoVazio(nick) && !isEmail(nick) && (usuarioDAO.getUsuarioEmail(nick) == null));
    }

    public boolean isEmailValido(String email) {
        return (!isCampoVazio(email) && isEmail(email) && (usuarioDAO.getUsuarioEmail(email) == null));
    }

    public boolean isNascValido(String nasc) {
        return(FormataData.dataExiste(nasc) && FormataData.dataMenorOuIgualQueAtual(nasc) && nasc.length() == TAMANHO_DATA);
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