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
import com.ufrpe.feelingsbox.redesocial.dominio.Sessao;
import com.ufrpe.feelingsbox.redesocial.redesocialservices.RedeServices;

import java.util.ArrayList;

public class ActCriarComentario extends AppCompatActivity {
    private MultiAutoCompleteTextView edtComentario;
    private Long idPost;
    private Sessao sessao = Sessao.getInstancia();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_criar_comentario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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

    public void onClickComentar(View view){
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

    @Override
    public void onBackPressed() {
        retornarHome();
        super.onBackPressed();
    }
    private void retornarHome(){
        Intent intent = new Intent(this, sessao.popHistorico().getValor());
        startActivity(intent);
        finish();
    }
}