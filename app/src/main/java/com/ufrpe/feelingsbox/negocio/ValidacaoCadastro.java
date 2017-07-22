package com.ufrpe.feelingsbox.negocio;

import android.app.AlertDialog;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;

import com.ufrpe.feelingsbox.gui.ActSignUp;
import com.ufrpe.feelingsbox.persistencia.usuariodao.UsuarioDAO;

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
        else
            if (res = !isNickValido(edtNick.getText().toString())) {
                edtNick.requestFocus();
            }
            else
                if (res = !isEmailValido(edtEmail.getText().toString())) {
                    edtEmail.requestFocus();
                }
                else
                    if (res = !isNascValido(edtNasc.getText().toString())) {
                        edtNasc.requestFocus();
                    }
                    else
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
        boolean resultado = (TextUtils.isEmpty(campo) || campo.trim().isEmpty());
        return resultado;
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
        if (isCampoVazio(nasc)) {
            return false;
        } else {
            String rex = "([0]{1}[1-9]{1}|[1-2]{1}[0-9]{1}|[3]{1}[0-1]{1})+" +
                    "\\/([0]{1}[1-9]{1}|[1]{1}[0-2]{2})+" +
                    "\\/([1]{1}[9]{1}[5-9]{1}[0-9]{1}|[2]{1}[0]{1}([0-4]{1}+ " +
                    "[0-9]{1}|[5]{1}[0]{1}))";
            return (nasc.matches(rex));
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
