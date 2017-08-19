package com.ufrpe.feelingsbox.redesocial.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.ufrpe.feelingsbox.R;
import com.ufrpe.feelingsbox.infra.GuiUtil;
import com.ufrpe.feelingsbox.infra.adapter.usuario.UserFragment;
import com.ufrpe.feelingsbox.redesocial.dominio.Sessao;

import static com.ufrpe.feelingsbox.redesocial.dominio.ActEnum.ACT_SEGUIDOS_SEGUIDORES;
import static com.ufrpe.feelingsbox.redesocial.dominio.BundleEnum.MAIN_FRAG;

public class ActSeguidosSeguidores extends AppCompatActivity {
    private Sessao sessao = Sessao.getInstancia();

    public ActSeguidosSeguidores() {
        super();
        sessao.addHistorico(ACT_SEGUIDOS_SEGUIDORES);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_seguidos_seguidores);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        try{
            String texto = sessao.getUltimoModo().getValor();
            toolbar.setTitle(texto);
        } catch (Exception e){
            GuiUtil.myAlertDialog(this, e.getMessage() + sessao.getUltimoModo());
        }

        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.iniciarFragment();
    }

    private void iniciarFragment(){
        UserFragment frag = (UserFragment) getSupportFragmentManager().findFragmentByTag(MAIN_FRAG.getValor());
        if(frag == null) {
            frag = new UserFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.rl_fragment_container, frag, MAIN_FRAG.getValor());
            ft.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_act_seguidos_seguidores, menu);

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

    public void retornarTela(){
        sessao.popHistorico();
        sessao.popModo();
        Intent intent = new Intent(this, sessao.popHistorico().getValor());
        startActivity(intent);
        finish();
    }

}
