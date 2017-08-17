package com.ufrpe.feelingsbox.redesocial.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.ufrpe.feelingsbox.R;
import com.ufrpe.feelingsbox.infra.GuiUtil;
import com.ufrpe.feelingsbox.infra.ValidacaoService;
import com.ufrpe.feelingsbox.redesocial.redesocialservices.RedeServices;

public class ActCriarComentario extends AppCompatActivity {
    private EditText edtComentario;
    private Long idPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_criar_comentario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Encontrando elementos
        edtComentario = (EditText) findViewById(R.id.edtComentario);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_act_criar_comentario, menu);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            idPost = extras.getLong("idPost");
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_comentar:
                String texto = edtComentario.getText().toString();
                long idpost = idPost;

                ValidacaoService validarComentario = new ValidacaoService(getApplicationContext());
                boolean comentarioVazio = false;
                if (validarComentario.isCampoVazio(texto)){
                    edtComentario.requestFocus();
                    edtComentario.setError(getString(R.string.print_erro_validacao_post_campovazio));
                    comentarioVazio = true;
                }

                if (!comentarioVazio){
                    RedeServices redeServices = new RedeServices(getApplicationContext());
                    redeServices.salvarComentario(texto, idpost);
                    GuiUtil.myToast(this, getString(R.string.print_msg_postado));
                    retornarHome();
                }
                break;
            case R.id.action_cancelar:
                retornarHome();
                break;
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