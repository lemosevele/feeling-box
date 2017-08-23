package com.ufrpe.feelingsbox.redesocial.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.MultiAutoCompleteTextView;

import com.ufrpe.feelingsbox.R;
import com.ufrpe.feelingsbox.infra.GuiUtil;
import com.ufrpe.feelingsbox.infra.SpaceTokenizer;
import com.ufrpe.feelingsbox.infra.ValidacaoService;
import com.ufrpe.feelingsbox.redesocial.dominio.BundleEnum;
import com.ufrpe.feelingsbox.redesocial.dominio.Sessao;
import com.ufrpe.feelingsbox.redesocial.redesocialservices.RedeServices;

import java.util.ArrayList;

public class ActCriarPostComentario extends AppCompatActivity {
    private MultiAutoCompleteTextView edtComentario;
    private Long idPost;
    private Sessao sessao = Sessao.getInstancia();
    private BundleEnum modo;

    public ActCriarPostComentario() {
        modo = sessao.getUltimoModo();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_criar_post_comentario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(modo == BundleEnum.POST){
            toolbar.setTitle(getString(R.string.title_activity_act_criar_post));
        } else if(modo == BundleEnum.COMENTARIO){
            toolbar.setTitle(getString(R.string.title_activity_act_criar_comentario));
        } else {
            toolbar.setTitle("Modo é igual a " + modo);
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Encontrando elementos
        ArrayList listaTags = new ArrayList<String>();
        listaTags.add("#mpoo");
        listaTags.add("#duvida");
        listaTags.add("#prazo");
        listaTags.add("#peperone");
        listaTags.add("#profilaxia");
        listaTags.add("#cansado");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, listaTags);
        edtComentario = (MultiAutoCompleteTextView) findViewById(R.id.edtComentario);
        edtComentario.setAdapter(adapter);
        edtComentario.setTokenizer(new SpaceTokenizer());
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

    public void onClickPostarComentar(View view){
        if(modo == BundleEnum.POST){
            this.registrarPost();
        } else if(modo == BundleEnum.COMENTARIO){
            this.registrarComentario();
        } else {
            GuiUtil.myAlertDialog(this, "Modo é igual a " + modo);
        }
    }

    private void registrarComentario(){
        String texto = edtComentario.getText().toString();

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            idPost = extras.getLong("idPost");
        }

        ValidacaoService validarComentario = new ValidacaoService(getApplicationContext());
        boolean comentarioVazio = false;
        if (validarComentario.isCampoVazio(texto)){
            edtComentario.requestFocus();
            edtComentario.setError(getString(R.string.print_erro_validacao_comentario_campovazio));
            comentarioVazio = true;
        }
        if (!comentarioVazio){
            RedeServices redeServices = new RedeServices(getApplicationContext());
            redeServices.salvarComentario(texto, idPost);
            GuiUtil.myToast(this, getString(R.string.print_msg_comentado));
            retornarHome();
        }
    }

    private void registrarPost(){
        String texto = edtComentario.getText().toString();

        ValidacaoService validarPost = new ValidacaoService(getApplicationContext());
        boolean postVazio = false;
        if (validarPost.isCampoVazio(texto)){
            edtComentario.requestFocus();
            edtComentario.setError(getString(R.string.print_erro_validacao_post_campovazio));
            postVazio = true;
        }
        if (!postVazio){
            RedeServices redeServices = new RedeServices(getApplicationContext());
            redeServices.salvarPost(texto);
            GuiUtil.myToast(this, getString(R.string.print_msg_postado));
            retornarHome();
        }
    }

    @Override
    public void onBackPressed() {
        retornarHome();
        super.onBackPressed();
    }

    private void retornarHome(){
        sessao.popModo();
        Intent intent = new Intent(this, sessao.popHistorico().getValor());
        startActivity(intent);
        finish();
    }
}