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
import com.ufrpe.feelingsbox.infra.adapter.post.PostFragment;
import com.ufrpe.feelingsbox.redesocial.dominio.Sessao;
import com.ufrpe.feelingsbox.redesocial.redesocialservices.RedeServices;
import com.ufrpe.feelingsbox.usuario.dominio.Usuario;

import static com.ufrpe.feelingsbox.redesocial.dominio.ActEnum.ACT_PERFIL_POST;
import static com.ufrpe.feelingsbox.redesocial.dominio.BundleEnum.MAIN_FRAG;
import static com.ufrpe.feelingsbox.redesocial.dominio.BundleEnum.SEGUIDORES;
import static com.ufrpe.feelingsbox.redesocial.dominio.BundleEnum.SEGUIDOS;

/**
 * Classe responsável pela Tela de Perfil Público.
 */
public class ActPerfilPost extends AppCompatActivity {
    private Sessao sessao = Sessao.getInstancia();
    private MenuItem actionFollow;
    private MenuItem actionUnfollow;
    private Usuario usuarioDonoTela;
    private TextView numSeguidos, numSeguidores;
    private RedeServices redeServices;
    private long longSeguidos, longSeguidores;

    /**
     Construtor - Envia para a pilha de histórico de Telas uma referência da própria classe. @see {@link Sessao}.
     * E define o @see {@link Usuario} logado como atributo da Classe.
     */

    public ActPerfilPost() {
        super();
        sessao.addHistorico(ACT_PERFIL_POST);
        usuarioDonoTela = sessao.getUltimoUsuario();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_perfil_post);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle(usuarioDonoTela.getNick());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        redeServices = new RedeServices(getApplicationContext());
        this.encontrandoItens();
        this.atualizarNumSeguidos();
        this.atualizarNumSeguidores();
        this.iniciarFragment();
    }

    /**
     * Método que declara os tipos dos atributos que referenciam os @see {@link TextView} que serão
     * modificados com base nos dados do @see {@link Usuario} a serem exibido.
     */

    private void encontrandoItens(){
        numSeguidos = (TextView) findViewById(R.id.txtSeguidosValor);
        numSeguidores = (TextView) findViewById(R.id.txtSeguidoresValor);
    }

    private void atualizarNumSeguidos(){
        longSeguidos = redeServices.qtdSeguidos(usuarioDonoTela.getId());
        numSeguidos.setText(Long.toString(longSeguidos));
    }

    private void atualizarNumSeguidores(){
        longSeguidores = redeServices.qtdSeguidores(usuarioDonoTela.getId());
        numSeguidores.setText(Long.toString(longSeguidores));
    }

    private void iniciarFragment(){
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
        inflater.inflate(R.menu.menu_act_perfil_post, menu);

        actionFollow = menu.findItem(R.id.action_follow);
        actionUnfollow = menu.findItem(R.id.action_unfollow);

        if(usuarioDonoTela.getNick().equals(sessao.getUsuarioLogado().getNick())){
            actionFollow.setVisible(false);
            actionUnfollow.setVisible(false);
        } else if(redeServices.verificacaoSeguidor(sessao.getUsuarioLogado().getId(), usuarioDonoTela.getId())){
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
                retornarTela();
                break;
            case R.id.action_home:
                Intent intent = new Intent(this, ActHome.class);
                startActivity(intent);
                finish();
                break;
            case R.id.action_follow:
                redeServices.seguirUser(sessao.getUsuarioLogado().getId(), usuarioDonoTela.getId());
                actionFollow.setVisible(false);
                actionUnfollow.setVisible(true);
                break;
            case R.id.action_unfollow:
                redeServices.deletarUser(sessao.getUsuarioLogado().getId(), usuarioDonoTela.getId());
                actionFollow.setVisible(true);
                actionUnfollow.setVisible(false);
                break;

        }
        this.atualizarNumSeguidores();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        retornarTela();
        super.onBackPressed();
    }

    /**
     * Método retorna para a Tela anterior com base no penúltimo resgistro na pilha de histórico na
     * instância Classe @see {@link Sessao}´, pois o último é uma auto referência.
     */

    private void retornarTela(){
        sessao.popHistorico();
        sessao.popUsuario();
        Intent intent = new Intent(this, sessao.popHistorico().getValor());
        startActivity(intent);
        finish();
    }

    /**
     * Método que muda a tela para a Tela de Seguindo @see {@link ActSeguidosSeguidores}..
     * @param view - Referência ao Botão Seguindo @see {@link View} e {@link com.ufrpe.feelingsbox.R.layout}.
     */

    public void onClickSeguidos(View view){
        Intent intent = new Intent(this, ActSeguidosSeguidores.class);
        sessao.addModo(SEGUIDOS);
        startActivity(intent);
        finish();
    }

    /**
     * Método que muda a tela para a Tela de Seguidores @see {@link ActSeguidosSeguidores}..
     * @param view - Referência ao Botão Seguidores @see {@link View} e {@link com.ufrpe.feelingsbox.R.layout}.
     */

    public void onClickSeguidores(View view){
        Intent intent = new Intent(this, ActSeguidosSeguidores.class);
        sessao.addModo(SEGUIDORES);
        startActivity(intent);
        finish();
    }

}