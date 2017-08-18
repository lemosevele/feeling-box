package com.ufrpe.feelingsbox.redesocial.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.ufrpe.feelingsbox.R;
import com.ufrpe.feelingsbox.infra.adapter.usuario.UserFragment;

import static com.ufrpe.feelingsbox.redesocial.dominio.ActEnum.ACT_PERFIL_POST;
import static com.ufrpe.feelingsbox.redesocial.dominio.BundleEnum.ID_USUARIO;
import static com.ufrpe.feelingsbox.redesocial.dominio.BundleEnum.MAIN_FRAG;
import static com.ufrpe.feelingsbox.redesocial.dominio.BundleEnum.MODO;
import static com.ufrpe.feelingsbox.redesocial.dominio.BundleEnum.RETORNO;

public class ActSeguidosSeguidores extends AppCompatActivity {
    private String modo;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_seguidos_seguidores);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        bundle = getIntent().getExtras();
        if(bundle != null){
            modo = bundle.getString(MODO.getValor());
            toolbar.setTitle(modo);
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Fragment
        UserFragment frag = (UserFragment) getSupportFragmentManager().findFragmentByTag(MAIN_FRAG.getValor());
        if(frag == null) {
            frag = new UserFragment();
            if(bundle != null){
                frag.setArguments(bundle);
            }
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

    public void retornarTela(){
        Intent intent;
        String retorno = "";
        Long idUser = null;
        if(bundle != null){
            retorno = bundle.getString(RETORNO.getValor());
            idUser = bundle.getLong(ID_USUARIO.getValor());
        }
        if(retorno.equals(ACT_PERFIL_POST.getValor())){
            intent = new Intent(this, ActPerfilPost.class);
            intent.putExtra(ID_USUARIO.getValor(), idUser);
        } else {
            intent = new Intent(this, ActPerfil.class);
        }
        startActivity(intent);
        finish();
    }
}
