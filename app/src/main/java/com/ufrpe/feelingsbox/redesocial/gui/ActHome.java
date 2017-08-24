package com.ufrpe.feelingsbox.redesocial.gui;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.ufrpe.feelingsbox.R;
import com.ufrpe.feelingsbox.infra.adapter.tabs.TabsAdapter;
import com.ufrpe.feelingsbox.redesocial.dominio.Sessao;
import com.ufrpe.feelingsbox.redesocial.redesocialservices.RedeServices;
import com.ufrpe.feelingsbox.usuario.dominio.Usuario;
import com.ufrpe.feelingsbox.usuario.gui.ActLogin;

import static com.ufrpe.feelingsbox.redesocial.dominio.ActEnum.ACT_HOME;
import static com.ufrpe.feelingsbox.redesocial.dominio.BundleEnum.POST;


public class ActHome extends AppCompatActivity {
    private Sessao sessao = Sessao.getInstancia();
    private Usuario usuarioLogado;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    public ActHome() {
        super();
        sessao.limparHistoricoUsuariosModosPosts();
        sessao.addHistorico(ACT_HOME);
        usuarioLogado = sessao.getUsuarioLogado();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle(usuarioLogado.getNick());
        setSupportActionBar(toolbar);

        //Botão Flutuante
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessao.addModo(POST);
                mudarTela(ActCriarPostComentario.class);
            }
        });
        this.encontrandoElementos();
        this.atualizarElementos();
    }

    private void encontrandoElementos(){
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.vp_tabs);
    }
    private void atualizarElementos(){
        viewPager.setAdapter(new TabsAdapter(getSupportFragmentManager(), this));
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(sessao.getTabAtiva());
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

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_conta:
                sessao.addUsuario(usuarioLogado);
                mudarTela(ActPerfil.class);
                break;
            case R.id.action_perfil:
                sessao.addUsuario(usuarioLogado);
                mudarTela(ActPerfilPost.class);
                break;
            case R.id.action_sair:
                RedeServices redeServices = new RedeServices(getApplicationContext());
                redeServices.finalizarSessao();
                mudarTela(ActLogin.class);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void mudarTela(Class novaTela){
        Intent it = new Intent(this, novaTela);
        startActivity(it);
        finish();
    }
}