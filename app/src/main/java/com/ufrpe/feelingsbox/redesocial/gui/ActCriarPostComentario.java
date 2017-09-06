package com.ufrpe.feelingsbox.redesocial.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

/**
 * Classe responsável pela Tela de Criação de Postagens e Comentários.
 */

public class ActCriarPostComentario extends AppCompatActivity {
    private MultiAutoCompleteTextView edtComentario;
    private Long idPost;
    private Sessao sessao = Sessao.getInstancia();
    private BundleEnum modo;

    /**
     * Construtor - Recebe um @see {@link BundleEnum} da Classe @see {@link Sessao} que indicará
     * se a Tela será de Postagem ou Comentário.
     */

    public ActCriarPostComentario() {
        modo = sessao.getUltimoModo();
        if(modo == null) {
            Log.d("ActCriarPostComentari()", getString(R.string.erro_msg_mododonotela_null));
            GuiUtil.myToast(this, getString(R.string.erro_msg_mododonotela_null));
            Intent intent = new Intent(this, ActHome.class);
            startActivity(intent);
            finish();
        }

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
        RedeServices redeServices = new RedeServices(getApplicationContext());
        redeServices.listaTags();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, redeServices.listaTags());
        edtComentario = (MultiAutoCompleteTextView) findViewById(R.id.edtComentario);
        edtComentario.setAdapter(adapter);
        edtComentario.setTokenizer(new SpaceTokenizer());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                retornarTela();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Método chamará @see registrarComentario @see registrarPost com base valor recebido no Construtor.
     * @param view - Referência ao Botão Enviar @see {@link View} e @see {@link com.ufrpe.feelingsbox.R.layout}.
     */

    public void onClickPostarComentar(View view){
        if(modo == BundleEnum.POST){
            this.registrarPost();
        } else if(modo == BundleEnum.COMENTARIO){
            this.registrarComentario();
        } else {
            GuiUtil.myAlertDialog(this, "Modo é igual a " + modo);
            retornarTela();
        }
    }

    /**
     * Método registra o Comentário digitado no banco de dados.
     * @see {@link RedeServices}.
     */

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
            retornarTela();
        }
    }

    /**
     * Método registra a Postagem (@see {@link com.ufrpe.feelingsbox.redesocial.dominio.Post}) digitada no banco de dados.
     * @see {@link RedeServices}.
     */

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
            retornarTela();
        }
    }

    @Override
    public void onBackPressed() {
        retornarTela();
        super.onBackPressed();
    }

    /**
     * Método retorna para a Tela anterior com base no último resgistro na pilha de histórico na
     * instância Classe @see {@link Sessao}.
     */

    private void retornarTela(){
        Intent intent;
        //Try para evitar que o aplicativo feche quando voltar o segundo plano
        // e alguma das pilhas da instância de sessão forem desfeitas.
        try {
            sessao.popModo();
            intent = new Intent(this, sessao.popHistorico().getValor());
        } catch (Exception e){
            Log.d("retornarTela-ActCPComen", e.getMessage());
            GuiUtil.myToast(this, e);
            intent = new Intent(this, ActHome.class);
        }
        startActivity(intent);
        finish();
    }
}