//<<<<<<< HEAD
package com.ufrpe.feelingsbox.redesocial.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ufrpe.feelingsbox.R;
import com.ufrpe.feelingsbox.infra.adapter.post.PostFragmentPerfil;
import com.ufrpe.feelingsbox.redesocial.dominio.Sessao;
import com.ufrpe.feelingsbox.redesocial.redesocialservices.RedeServices;
import com.ufrpe.feelingsbox.usuario.dominio.Usuario;
import com.ufrpe.feelingsbox.usuario.usuarioservices.UsuarioService;
import com.ufrpe.feelingsbox.redesocial.persistencia.RelacaoSegDAO;

import static android.R.attr.action;
import static com.ufrpe.feelingsbox.redesocial.dominio.ActEnum.ACT_PERFIL_POST;
import static com.ufrpe.feelingsbox.redesocial.dominio.BundleEnum.ID_USUARIO;
import static com.ufrpe.feelingsbox.redesocial.dominio.BundleEnum.MAIN_FRAG;
import static com.ufrpe.feelingsbox.redesocial.dominio.BundleEnum.MODO;
import static com.ufrpe.feelingsbox.redesocial.dominio.BundleEnum.RETORNO;
import static com.ufrpe.feelingsbox.redesocial.dominio.BundleEnum.SEGUIDORES;
import static com.ufrpe.feelingsbox.redesocial.dominio.BundleEnum.SEGUIDOS;

public class ActPerfilPost extends AppCompatActivity {
    private Sessao sessao = Sessao.getInstancia();
    private MenuItem actionFollow;
    private MenuItem actionUnfollow;
    private Usuario usuarioPost;
    private RelacaoSegDAO relacaoSegDAO;
    private TextView numSeguidos, numSeguidores;
    private RedeServices redeServices;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_perfil_post);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        bundle = getIntent().getExtras();

        if(bundle != null){
            UsuarioService usuarioService = new UsuarioService(this);
            usuarioPost = usuarioService.buscarUsuario(bundle.getLong(ID_USUARIO.getValor()));
        } else {
            usuarioPost = sessao.getUsuarioLogado();
        }
        toolbar.setTitle(usuarioPost.getNick());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        redeServices = new RedeServices(getApplicationContext());
        this.encontrandoItens();
        this.atualizarNumSeguidos();
        this.atualizarNumSeguidores();
        this.iniciarFragment();
    }

    private void encontrandoItens(){
        numSeguidos = (TextView) findViewById(R.id.txtSeguindoValor);
        numSeguidores = (TextView) findViewById(R.id.txtSeguidoresValor);
    }

    private void atualizarNumSeguidos(){
        int intSeguidos = redeServices.listarSeguidos(usuarioPost.getId()).size();
        numSeguidos.setText(Integer.toString(intSeguidos));
    }

    private void atualizarNumSeguidores(){
        int intSeguidores = redeServices.listarSeguidores(usuarioPost.getId()).size();
        numSeguidores.setText(Integer.toString(intSeguidores));
    }

    private void iniciarFragment(){
        PostFragmentPerfil frag = (PostFragmentPerfil) getSupportFragmentManager().findFragmentByTag(MAIN_FRAG.getValor());
        if(frag == null) {
            frag = new PostFragmentPerfil();
            if(bundle != null){
                frag.setArguments(bundle);
            }
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.rl_fragment_container, frag, MAIN_FRAG.getValor());
            ft.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_act_perfil_post, menu);
        relacaoSegDAO = new RelacaoSegDAO(this);

        actionFollow = menu.findItem(R.id.action_follow);
        actionUnfollow = menu.findItem(R.id.action_unfollow);

        if(usuarioPost.getNick().equals(sessao.getUsuarioLogado().getNick())){
            actionFollow.setVisible(false);
            actionUnfollow.setVisible(false);
        } else if(relacaoSegDAO.verificaSeguidor(sessao.getUsuarioLogado().getId(), usuarioPost.getId())){
            actionFollow.setVisible(false);
            actionUnfollow.setVisible(true);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        RedeServices redeServices = new RedeServices(getApplicationContext());
        switch (item.getItemId()){
            case android.R.id.home:
                retornarHome();
                break;
            case R.id.action_follow:
                redeServices.seguirUser(sessao.getUsuarioLogado().getId(), usuarioPost.getId());
                actionFollow.setVisible(false);
                actionUnfollow.setVisible(true);
                break;
            case R.id.action_unfollow:
                redeServices.deletarUser(sessao.getUsuarioLogado().getId(), usuarioPost.getId());
                actionFollow.setVisible(true);
                actionUnfollow.setVisible(false);
                break;
        }
        this.atualizarNumSeguidores();
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

    public void onClickSeguidos(View view){
        Intent intent = new Intent(this, ActSeguidosSeguidores.class);
        intent.putExtra(ID_USUARIO.getValor(), usuarioPost.getId());
        intent.putExtra(RETORNO.getValor(), ACT_PERFIL_POST.getValor());
        intent.putExtra(MODO.getValor(), SEGUIDOS.getValor());
        startActivity(intent);
        finish();
    }

    public void onClickSeguidores(View view){
        Intent intent = new Intent(this, ActSeguidosSeguidores.class);
        intent.putExtra(ID_USUARIO.getValor(), usuarioPost.getId());
        intent.putExtra(RETORNO.getValor(), ACT_PERFIL_POST.getValor());
        intent.putExtra(MODO.getValor(), SEGUIDORES.getValor());
        startActivity(intent);
        finish();
    }

}