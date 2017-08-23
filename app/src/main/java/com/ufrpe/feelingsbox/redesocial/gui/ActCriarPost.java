package com.ufrpe.feelingsbox.redesocial.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.MultiAutoCompleteTextView;

import com.ufrpe.feelingsbox.R;
import com.ufrpe.feelingsbox.infra.GuiUtil;
import com.ufrpe.feelingsbox.infra.ValidacaoService;
import com.ufrpe.feelingsbox.redesocial.redesocialservices.RedeServices;

import java.util.ArrayList;


public class ActCriarPost extends AppCompatActivity {
    private MultiAutoCompleteTextView edtTexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_criar_post);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayList listaTags = new ArrayList<String>();
        listaTags.add("#mpoo");
        listaTags.add("#duvida");
        listaTags.add("#prazo");
        listaTags.add("#peperone");
        listaTags.add("#profilaxia");
        listaTags.add("#cansado");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, listaTags);
        edtTexto = (MultiAutoCompleteTextView) findViewById(R.id.edtTexto);
        edtTexto.setAdapter(adapter);
        edtTexto.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_act_criar_post, menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_cancelar:
                retornaHome();
                break;
            case R.id.action_postar:
                String texto = edtTexto.getText().toString();

                ValidacaoService validarPost = new ValidacaoService(getApplicationContext());
                boolean postVazio = false;
                if (validarPost.isCampoVazio(texto)){
                    edtTexto.requestFocus();
                    edtTexto.setError(getString(R.string.print_erro_validacao_post_campovazio));
                    postVazio = true;
                }
                if (!postVazio){
                    RedeServices redeServices = new RedeServices(getApplicationContext());
                    redeServices.salvarPost(texto);
                    GuiUtil.myToast(this, getString(R.string.print_msg_postado));
                    retornaHome();
                }
                break;
            case android.R.id.home:
                retornaHome();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        retornaHome();
        super.onBackPressed();
    }

    private void retornaHome(){
        Intent intent = new Intent(this, ActHome.class);
        startActivity(intent);
        finish();

    }
}