package com.ufrpe.feelingsbox.redesocial.gui;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.ufrpe.feelingsbox.R;
import com.ufrpe.feelingsbox.infra.adapter.post.PostFragment;
import com.ufrpe.feelingsbox.redesocial.dominio.Sessao;
import com.ufrpe.feelingsbox.redesocial.redesocialservices.RedeServices;
import com.ufrpe.feelingsbox.usuario.dominio.Usuario;

import static com.ufrpe.feelingsbox.redesocial.dominio.BundleEnum.MAIN_FRAG;


public class ActHome extends AppCompatActivity {
    private Sessao sessao = Sessao.getInstancia();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        Usuario usuarioLogado = sessao.getUsuarioLogado();
        toolbar.setTitle(usuarioLogado.getNick());
        setSupportActionBar(toolbar);

        //Botão Flutuante
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActHome.this, ActCriarPost.class);
                startActivity(intent);
                finish();
            }
        });

        //Fragment
        PostFragment frag = (PostFragment) getSupportFragmentManager().findFragmentByTag(MAIN_FRAG.getValor());
        if(frag == null) {
            frag = new PostFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.rl_fragment_container, frag, MAIN_FRAG.getValor());
            ft.commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_act_home, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView;
        MenuItem item = menu.findItem(R.id.action_pesquisar);

        searchView = (SearchView) item.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        //searchView.setQueryHint("Pesquisar post");

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_perfil:
                mudarTela(ActPerfil.class);
                break;
            case R.id.action_meus_post:
                mudarTela(ActPerfilPost.class);
                break;
            case R.id.action_sair:
                RedeServices redeServices = new RedeServices(getApplicationContext());
                redeServices.finalizarSessao();
                finish();
                break;
            
        }
        return super.onOptionsItemSelected(item);
    }
    private void mudarTela(Class novaTela){
        Intent it = new Intent(ActHome.this, novaTela);
        startActivity(it);
        finish();
    }
}