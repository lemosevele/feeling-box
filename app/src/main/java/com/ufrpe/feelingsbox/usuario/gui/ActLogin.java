package com.ufrpe.feelingsbox.usuario.gui;
/*
 * Tela de Login
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.ufrpe.feelingsbox.R;
import com.ufrpe.feelingsbox.infra.GuiUtil;
import com.ufrpe.feelingsbox.redesocial.gui.ActHome;
import com.ufrpe.feelingsbox.usuario.usuarioservices.UsuarioService;
import com.ufrpe.feelingsbox.infra.ValidacaoService;

public class ActLogin extends AppCompatActivity {

    //Declarando os Elementos da Tela(activity)
    private EditText edtLogin, edtSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);


        //Encontrando Elemento da Tela(activity)
        edtLogin = (EditText)findViewById(R.id.edtLogin);
        edtSenha = (EditText)findViewById(R.id.edtSenha);

    }

    //Ação ao Clicar no botão Cadastrar
    public void cadastrarUsuario(View view){
        //Trocando para a Tela de Cadastro
        Intent it = new Intent(ActLogin.this, ActSignUp.class);
        startActivity(it);
    }

    //Ação ao Clicar no botão Entrar
    public void efetuarLogin(View view){

        String login = edtLogin.getText().toString();
        String senha = edtSenha.getText().toString();

        ValidacaoService validacaoLogin = new ValidacaoService();
        boolean vazio = false;
        if (validacaoLogin.isCampoVazio(senha)){
            edtSenha.requestFocus();
            edtSenha.setError("O campo senha está vazio.");
            vazio = true;
        }
        if (validacaoLogin.isCampoVazio(login)){
            edtLogin.requestFocus();
            edtLogin.setError("O campo login está vazio.");
            vazio = true;
        }

        if (!vazio) {
            UsuarioService buscarValidar = new UsuarioService(getApplicationContext());
            if (!validacaoLogin.isEmail(login)) {
                try {
                    buscarValidar.logarNick(login, senha);
                    //Trocando para a Tela de Home
                    Intent it = new Intent(ActLogin.this, ActHome.class);
                    startActivity(it);
                    finish();
                }
                catch (Exception e) {
                    GuiUtil.myToast(this, "Login ou senha incorretos.");
                }
            }
            else {
                try {
                    buscarValidar.logarEmail(login, senha);
                    //Trocando para a Tela de Home
                    Intent it = new Intent(ActLogin.this, ActHome.class);
                    startActivity(it);
                    finish();
                }
                catch (Exception e) {
                    GuiUtil.myToast(this, "Login ou senha incorretos.");
                }
            }
        }
    }
}