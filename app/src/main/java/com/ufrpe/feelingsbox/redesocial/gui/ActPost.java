package com.ufrpe.feelingsbox.redesocial.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.ufrpe.feelingsbox.R;
import com.ufrpe.feelingsbox.infra.FormataData;
import com.ufrpe.feelingsbox.infra.adapter.comentario.ComentarioFragment;
import com.ufrpe.feelingsbox.redesocial.dominio.Post;
import com.ufrpe.feelingsbox.redesocial.dominio.Sessao;
import com.ufrpe.feelingsbox.usuario.usuarioservices.UsuarioService;

import static com.ufrpe.feelingsbox.redesocial.dominio.ActEnum.ACT_POST;
import static com.ufrpe.feelingsbox.redesocial.dominio.BundleEnum.MAIN_FRAG;

public class ActPost extends AppCompatActivity {
    private Sessao sessao = Sessao.getInstancia();
    private TextView txtDonoPost, txtData, txtPostagem;
    private Post postDonoTela;

    public ActPost() {
        super();
        sessao.addHistorico(ACT_POST);
        postDonoTela = sessao.getUltimoPost();
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

    private void encontrandoItens(){
        txtDonoPost = (TextView) findViewById(R.id.txtDonoPost);
        txtData = (TextView) findViewById(R.id.txtData);
        txtPostagem = (TextView) findViewById(R.id.txtPostagem);
    }

    private void atualizarPostagem(){
        UsuarioService usuarioService = new UsuarioService(this);
        txtDonoPost.setText(usuarioService.buscarNick(postDonoTela.getIdUsuario()));
        txtData.setText(FormataData.tempoParaMostrarEmPost(postDonoTela.getDataHora()));
        txtPostagem.setText(postDonoTela.getTexto());
    }

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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                retornarTela();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        retornarTela();
        super.onBackPressed();
    }

    private void retornarTela(){
        sessao.popHistorico();
        sessao.popPost();
        //sessao.popUsuario();
        Intent intent = new Intent(this, sessao.popHistorico().getValor());
        startActivity(intent);
        finish();
    }

}
