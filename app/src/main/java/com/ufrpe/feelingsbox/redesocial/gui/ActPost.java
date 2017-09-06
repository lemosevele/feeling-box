package com.ufrpe.feelingsbox.redesocial.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ufrpe.feelingsbox.R;
import com.ufrpe.feelingsbox.infra.FormataData;
import com.ufrpe.feelingsbox.infra.GuiUtil;
import com.ufrpe.feelingsbox.infra.adapter.comentario.ComentarioFragment;
import com.ufrpe.feelingsbox.redesocial.dominio.Post;
import com.ufrpe.feelingsbox.redesocial.dominio.Sessao;
import com.ufrpe.feelingsbox.redesocial.redesocialservices.RedeServices;
import com.ufrpe.feelingsbox.usuario.dominio.Usuario;

import static com.ufrpe.feelingsbox.redesocial.dominio.ActEnum.ACT_POST;
import static com.ufrpe.feelingsbox.redesocial.dominio.BundleEnum.COMENTARIO;
import static com.ufrpe.feelingsbox.redesocial.dominio.BundleEnum.ID_POST;
import static com.ufrpe.feelingsbox.redesocial.dominio.BundleEnum.MAIN_FRAG;

/**
 * Classe responsável pela Tela de Postagem
 */

public class ActPost extends AppCompatActivity {
    private Sessao sessao = Sessao.getInstancia();
    private TextView txtDonoPost, txtData, txtPostagem, numComentario;
    private Post postDonoTela;
    private Usuario usuarioDonoTela;
    private ImageView ivUser;
    private RedeServices redeServices;

    /**
     * Construtor - Envia para a pilha de histórico de Telas uma referência da própria classe. @see {@link Sessao}.
     * E define o @see {@link Usuario} logado e o @see {@link Post} como atributo da Classe.
     */

    public ActPost() {
        super();
        sessao.addHistorico(ACT_POST);
        //Try para evitar que o aplicativo feche quando voltar o segundo plano
        // e alguma das pilhas da instância de sessão forem desfeitas.
        postDonoTela = sessao.getUltimoPost();
        usuarioDonoTela = sessao.getUltimoUsuario();
        if(postDonoTela == null){
            Log.d("ActPost()", getString(R.string.erro_msg_postdonotela_null));
            GuiUtil.myToast(this, getString(R.string.erro_msg_postdonotela_null));
            Intent intent = new Intent(this, ActHome.class);
            startActivity(intent);
            finish();
        } else if (usuarioDonoTela == null){
            Log.d("ActPost()", getString(R.string.erro_msg_usuariodonotela_null));
            GuiUtil.myToast(this, getString(R.string.erro_msg_usuariodonotela_null));
            Intent intent = new Intent(this, ActHome.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_post);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        encontrandoItens();
        atualizarPostagem();
        iniciarFragment();
    }

    /**
     * Método que declara os tipos dos atributos que referenciam os @see {@link TextView} que serão
     * modificados com base nos dados do @see {@link Post} a serem exibido.
     */

    private void encontrandoItens(){
        txtDonoPost = (TextView) findViewById(R.id.txtDonoPost);
        txtData     = (TextView) findViewById(R.id.txtData);
        txtPostagem = (TextView) findViewById(R.id.txtPostagem);
        numComentario = (TextView) findViewById(R.id.numComentario);
        ivUser      = (ImageView) findViewById(R.id.ivUser);
    }

    /**
     * Exibe os valores nos textos com base nos dados do @see {@link Post}.
     */

    private void atualizarPostagem(){
        redeServices = new RedeServices(getApplicationContext());
        txtDonoPost.setText(usuarioDonoTela.getNick());
        txtData.setText(FormataData.tempoParaMostrarEmPost(postDonoTela.getDataHora()));
        txtPostagem.setText(postDonoTela.getTexto());
        long qtnComentario = redeServices.qtdComentariosPost(postDonoTela.getId());
        numComentario.setText(qtnComentario < 2 ? qtnComentario + " comentário" : qtnComentario + " comentários");

        if(redeServices.verificacaoSeguidor(sessao.getUsuarioLogado().getId(), usuarioDonoTela.getId())){
            txtDonoPost.setTextColor(getResources().getColor(R.color.colorUserFontFavorite));
        } else {
            txtDonoPost.setTextColor(getResources().getColor(R.color.colorUserFont));
        }
    }

    /**
     * Método inicia o @see {@link ComentarioFragment} que exibirá a listagem de @see {@link com.ufrpe.feelingsbox.redesocial.dominio.Comentario}.
     */

    private void iniciarFragment(){
        ComentarioFragment frag = (ComentarioFragment) getSupportFragmentManager().findFragmentByTag(MAIN_FRAG.getValor());
        if(frag == null) {
            frag = new ComentarioFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.rl_fragment_container, frag, MAIN_FRAG.getValor());
            ft.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_act_post, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                retornarTela();
                break;
            case R.id.action_home:
                Intent intent = new Intent(this, ActHome.class);
                startActivity(intent);
                finish();
                break;
        }
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
        Intent intent;
        //Try para evitar que o aplicativo feche quando voltar o segundo plano
        // e alguma das pilhas da instância de sessão forem desfeitas.
        try {
            sessao.popHistorico();
            sessao.popPost();
            sessao.popUsuario();
            intent = new Intent(this, sessao.popHistorico().getValor());
        } catch (Exception e){
            Log.d("retornarTela-ActPost", e.getMessage());
            GuiUtil.myToast(this, e);
            intent = new Intent(this, ActHome.class);
        }
        startActivity(intent);
        finish();
    }

    /**
     * Método muda a tela para @see {@link ActPerfilPost}.
     * @param view - Referência ao Botão Foto de Perfil @see {@link View} e @see {@link com.ufrpe.feelingsbox.R.layout}.
     */

    public void onCLickFotoPost(View view){
        sessao.addUsuario(usuarioDonoTela);
        Intent intent = new Intent(this, ActPerfilPost.class);
        startActivity(intent);
        finish();
    }

    /**
     * Método muda a tela para @see {@link ActCriarPostComentario}.
     * @param view - Referência ao Botão Comentar @see {@link View} e @see {@link com.ufrpe.feelingsbox.R.layout}.
     */

    public void onClickComentar(View view){
        sessao.addModo(COMENTARIO);
        Intent intent = new Intent(view.getContext(), ActCriarPostComentario.class);
        intent.putExtra(ID_POST.getValor(), postDonoTela.getId());
        startActivity(intent);
        finish();
    }
}
