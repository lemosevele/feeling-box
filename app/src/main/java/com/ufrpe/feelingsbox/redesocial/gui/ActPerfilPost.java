package com.ufrpe.feelingsbox.redesocial.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.ufrpe.feelingsbox.R;
import com.ufrpe.feelingsbox.infra.adapter.post.PostFragmentPerfil;
import com.ufrpe.feelingsbox.redesocial.dominio.Sessao;
import com.ufrpe.feelingsbox.usuario.dominio.Usuario;

public class ActPerfilPost extends AppCompatActivity {
    private Sessao sessao = Sessao.getInstancia();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_perfil_post);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        Usuario usuarioLogado = sessao.getUsuarioLogado();
        toolbar.setTitle(usuarioLogado.getNick());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Fragment
        PostFragmentPerfil frag = (PostFragmentPerfil) getSupportFragmentManager().findFragmentByTag("mainFrag");
        if(frag == null) {
            frag = new PostFragmentPerfil();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.rl_fragment_container, frag, "mainFrag");
            ft.commit();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                retornarHome();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        retornarHome();
        super.onBackPressed();
    }

    private void retornarHome(){
        Intent intent = new Intent(this, ActHome.class);
        startActivity(intent);
        finish();
    }

}
