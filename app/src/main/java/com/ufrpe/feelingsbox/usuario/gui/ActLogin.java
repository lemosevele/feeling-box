package com.ufrpe.feelingsbox.usuario.gui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.ufrpe.feelingsbox.R;
import com.ufrpe.feelingsbox.infra.Animacao;
import com.ufrpe.feelingsbox.infra.GuiUtil;
import com.ufrpe.feelingsbox.infra.ValidacaoService;
import com.ufrpe.feelingsbox.redesocial.gui.ActHome;
import com.ufrpe.feelingsbox.usuario.usuarioservices.UsuarioService;

/**
 * Classe responsável pela Tela de Login
 */

public class ActLogin extends AppCompatActivity {
    private EditText edtLogin, edtSenha;
    private UsuarioService usuarioService;
    private ValidacaoService validacaoService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
        this.encontrandoItens();
        Animacao.animacaoZoomIn(findViewById(R.id.mainLayoutLogin));
    }

    /**
     * Método que declara os tipos dos atributos que referenciam os campos Login e Senha que serão
     * mostrados na Tela.
     */

    private void encontrandoItens(){
        edtLogin = (EditText)findViewById(R.id.edtLogin);
        edtSenha = (EditText)findViewById(R.id.edtSenha);
    }

    /**
     * Método que muda da tela atual para a Tela de Cadastro @see {@link ActSignUp}
     * @param view - Referência ao Botão Cadastrar @see {@link View} e {@link com.ufrpe.feelingsbox.R.layout}
     */

    public void cadastrarUsuario(View view){
        Intent it = new Intent(ActLogin.this, ActSignUp.class);
        startActivity(it);
    }

    /**
     * Método que passará texto dos campos Login e Senha para serem analisados pelo método isCampoVazio
     * @see {@link ValidacaoService}, que verificará se os campos estão vazios, Caso não estejam
     * chamará método (@link chamarValidacao)
     * @param view - Referência ao Botão Logar @see {@link View} e {@link com.ufrpe.feelingsbox.R.layout}
     */

    public void efetuarLogin(View view){
        String login = edtLogin.getText().toString();
        String senha = edtSenha.getText().toString();
        validacaoService = new ValidacaoService(getApplicationContext());

        boolean vazio = false;
        if (validacaoService.isCampoVazio(senha)){
            edtSenha.requestFocus();
            edtSenha.setError("O campo senha está vazio.");
            vazio = true;
        }
        if (validacaoService.isCampoVazio(login)){
            edtLogin.requestFocus();
            edtLogin.setError("O campo login está vazio.");
            vazio = true;
        }

        if (!vazio) {
            this.chamarValidacao(login, senha);
        }
    }

    /**
     * Método que passará o texto digitado nos Campos Login e Senha para a Classe @see {@link UsuarioService}
     * as devida validações junto ao banco. Dependendo do retorno dado pela Classe @link {@link UsuarioService}
     * este método chamará o método @see irTelaHome ou apresentará uma mensagem de erro para o usuário.
     * @param login - Texto do Campo Login
     * @param senha - Texto do Campo Senha
     */

    private void chamarValidacao(String login, String senha){
        usuarioService = new UsuarioService(getApplicationContext());
        if (!validacaoService.isEmail(login)) {
            try {
                usuarioService.logarNick(login, senha);
                irTelaHome();
            }
            catch (Exception e) {
                GuiUtil.myToast(this, getString(R.string.msg_login_senha_incorreto));
            }
        }

        else {
            try {
                usuarioService.logarEmail(login, senha);
                irTelaHome();
            }
            catch (Exception e) {
                GuiUtil.myToast(this, getString(R.string.msg_login_senha_incorreto));
            }
        }
    }

    /**
     * Método que muda da tela atual para a Tela Principal @see {@link ActHome}
     */

    private void irTelaHome(){
        Intent it = new Intent(ActLogin.this, ActHome.class);
        startActivity(it);
        finish();
    }
}