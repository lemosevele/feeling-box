package com.ufrpe.feelingsbox.usuario.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ufrpe.feelingsbox.R;
import com.ufrpe.feelingsbox.infra.ValidacaoService;
import com.ufrpe.feelingsbox.redesocial.gui.ActHome;
import com.ufrpe.feelingsbox.redesocial.redesocialservices.RedeServices;
import com.ufrpe.feelingsbox.usuario.dominio.Pessoa;
import com.ufrpe.feelingsbox.usuario.dominio.Usuario;
import com.ufrpe.feelingsbox.usuario.gui.ActLogin;
import com.ufrpe.feelingsbox.usuario.usuarioservices.UsuarioService;

/**
 * Classe responsável pela Tela de Carregamento Inicial
 */

public class ActSplash extends AppCompatActivity {
    private UsuarioService usuarioService;
    private static final int SLEEP = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_splash);

        /**
         * Thread que parará a aplicação um curto periódo para exibição da Tela de Boas-Vindas
         * e logo após chamará método que analisará se há uma sessão no banco para logar
         * automaticamente.
         */

        Thread timeThread = new Thread(){
            @Override
            public void run() {
                try{
                    sleep(SLEEP);
                } catch (InterruptedException e){
                    Log.d("Thread Tela Splash", e.getMessage());
                } finally {
                    RedeServices redeServices = new RedeServices(getApplicationContext());
                    Pessoa pessoa = redeServices.verificarSessao();
                    if (pessoa != null){
                        usuarioService = new UsuarioService(getBaseContext());
                        Usuario usuario = usuarioService.buscarUsuario(pessoa.getIdUsuario());
                        chamarValidacao(usuario);
                    } else {
                        mudarTelaLogin();
                    }
                }
            }
        };
        timeThread.start();
    }

    /**
     * Método que muda da tela atual para a Tela de Login @see {@link ActLogin}
     */

    private void mudarTelaLogin(){
        Intent it = new Intent(getBaseContext(), ActLogin.class);
        startActivity(it);
        finish();
    }

    /**
     * Método que tentará logar automáticamente caso haja uma sessão no banco, caso contrário,
     * chamará o método @see mudarTelaLogin
     * @param usuario - Objeto do tipo usuário @see {@link Usuario}
     */

    private void chamarValidacao(Usuario usuario){
        String login = usuario.getNick();
        String senha = usuario.getSenha();

        try {
            usuarioService.autoLogarNick(login, senha);
            Intent it = new Intent(this, ActHome.class);
            startActivity(it);
            finish();
        }
        catch (Exception e) {
            mudarTelaLogin();
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        finish();
    }
}
