package com.ufrpe.feelingsbox.usuario.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ufrpe.feelingsbox.R;
import com.ufrpe.feelingsbox.infra.ValidacaoService;
import com.ufrpe.feelingsbox.redesocial.gui.ActHome;
import com.ufrpe.feelingsbox.redesocial.redesocialservices.RedeServices;
import com.ufrpe.feelingsbox.usuario.dominio.Pessoa;
import com.ufrpe.feelingsbox.usuario.dominio.Usuario;
import com.ufrpe.feelingsbox.usuario.gui.ActLogin;
import com.ufrpe.feelingsbox.usuario.usuarioservices.UsuarioService;

public class ActSplash extends AppCompatActivity {
    private UsuarioService usuarioService;
    private static final int SLEEP = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_splash);

        Thread timeThread = new Thread(){
            @Override
            public void run() {
                try{
                    sleep(SLEEP);
                } catch (InterruptedException e){
                    e.printStackTrace();
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

    private void mudarTelaLogin(){
        Intent it = new Intent(getBaseContext(), ActLogin.class);
        startActivity(it);
        finish();
    }
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

    public void onPause(){
        super.onPause();
        finish();
    }
}
