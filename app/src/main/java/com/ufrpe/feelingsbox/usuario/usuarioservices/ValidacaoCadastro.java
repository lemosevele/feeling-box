package com.ufrpe.feelingsbox.usuario.usuarioservices;

import android.app.AlertDialog;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;

import com.ufrpe.feelingsbox.usuario.gui.ActSignUp;
import com.ufrpe.feelingsbox.usuario.persistencia.usuariodao.UsuarioDAO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ValidacaoCadastro {
    private final EditText edtNome;
    private final EditText edtNick;
    private final EditText edtEmail;
    private final EditText edtNasc;
    private final EditText edtSenha;
    private final ActSignUp activity;
    private UsuarioDAO usuarioDAO;


    public ValidacaoCadastro(ActSignUp activity, EditText nome, EditText nick, EditText email, EditText nasc, EditText senha) {
        this.activity = activity;
        this.edtNome = nome;
        this.edtNick = nick;
        this.edtEmail = email;
        this.edtNasc = nasc;
        this.edtSenha = senha;

    }

    public boolean validarCampos() {
        boolean res = false;

        if (res = isCampoVazio(edtNome.getText().toString())) {
            edtNome.requestFocus();
        }
        if (res = !isNickValido(edtNick.getText().toString())) {
            edtNick.requestFocus();
        }
        if (res = !isEmailValido(edtEmail.getText().toString())) {
            edtEmail.requestFocus();
        }
        if (res = !isNascValido(edtNasc.getText().toString())) {
            edtNasc.requestFocus();
        }
        if (res = !isSenhaValida(edtSenha.getText().toString())) {
            edtSenha.requestFocus();
        }
        if (res){
            AlertDialog.Builder dlg = new AlertDialog.Builder(activity);
            dlg.setTitle("Aviso");
            dlg.setMessage("Há campos inválidos ou em branco.");
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }
        return !res;
    }


    private boolean isCampoVazio(String campo) {
         return (TextUtils.isEmpty(campo) || campo.trim().isEmpty());
        }

    private boolean isNickValido(String nick) {
        if (usuarioDAO.getUsuario(nick) != null) {
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
