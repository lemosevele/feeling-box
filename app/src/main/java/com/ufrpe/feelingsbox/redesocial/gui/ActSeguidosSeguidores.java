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

import com.ufrpe.feelingsbox.R;
import com.ufrpe.feelingsbox.infra.GuiUtil;
import com.ufrpe.feelingsbox.infra.adapter.usuario.UserFragment;
import com.ufrpe.feelingsbox.redesocial.dominio.Sessao;

import static com.ufrpe.feelingsbox.redesocial.dominio.ActEnum.ACT_SEGUIDOS_SEGUIDORES;
import static com.ufrpe.feelingsbox.redesocial.dominio.BundleEnum.MAIN_FRAG;

/**
 * Classe responsável pela Tela de Seguindo e Seguidores.
 */

public class ActSeguidosSeguidores extends AppCompatActivity {
    private Sessao sessao = Sessao.getInstancia();

    /**
     * Construtor - Envia para a pilha de histórico de Telas uma referência da própria classe. @see {@link Sessao}.
     */

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
            Log.d("onCreate-ActSSeguidores", e.getMessage() + " - " + sessao.getUltimoModo());
            GuiUtil.myAlertDialog(this, e.getMessage() + " - " + sessao.getUltimoModo());
        }

        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.iniciarFragment();
    }

    /**
     * Método inicia o @see {@link UserFragment} que exibirá a listagem de seguindo ou seguidores.
     */

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

    /**
     * Método retorna para a Tela anterior com base no penúltimo resgistro na pilha de histórico na
     * instância Classe @see {@link Sessao}´, pois o último é uma auto referência.
     */

    public void retornarTela(){
        Intent intent;
        //Try para evitar que o aplicativo feche quando voltar o segundo plano
        // e alguma das pilhas da instância de sessão forem desfeitas.
        try{
            sessao.popHistorico();
            sessao.popModo();
            intent = new Intent(this, sessao.popHistorico().getValor());
        } catch (Exception e){
            Log.d("retornarTela-ActSSeguid", e.getMessage());
            GuiUtil.myToast(this, e);
            intent = new Intent(this, ActHome.class);
        }
        startActivity(intent);
        finish();
    }
}
