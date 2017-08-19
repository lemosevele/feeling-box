package com.ufrpe.feelingsbox.redesocial.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ufrpe.feelingsbox.R;
import com.ufrpe.feelingsbox.infra.FormataData;
import com.ufrpe.feelingsbox.redesocial.dominio.Sessao;
import com.ufrpe.feelingsbox.redesocial.redesocialservices.RedeServices;
import com.ufrpe.feelingsbox.usuario.dominio.Pessoa;
import com.ufrpe.feelingsbox.usuario.dominio.Usuario;

import static com.ufrpe.feelingsbox.redesocial.dominio.ActEnum.ACT_PERFIL;
import static com.ufrpe.feelingsbox.redesocial.dominio.BundleEnum.SEGUIDORES;
import static com.ufrpe.feelingsbox.redesocial.dominio.BundleEnum.SEGUIDOS;

public class ActPerfil extends AppCompatActivity {
    private TextView txtNome, txtNicK, txtNasc, txtSexo, txtEmail, numSeguidos, numSeguidores;
    private Sessao sessao = Sessao.getInstancia();
    private Pessoa pessoaLogada;
    private Usuario usuarioLogado;

    public ActPerfil() {
        super();
        sessao.addHistorico(ACT_PERFIL);
        pessoaLogada = sessao.getPessoaLogada();
        usuarioLogado = sessao.getUsuarioLogado();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_perfil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.encontrandoItens();
        this.atualizarDados();
    }
    private void encontrandoItens(){
        txtNome = (TextView) findViewById(R.id.txtNome);
        txtNicK = (TextView) findViewById(R.id.txtNick);
        txtNasc = (TextView) findViewById(R.id.txtNasc);
        txtSexo = (TextView) findViewById(R.id.txtSexo);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        numSeguidos = (TextView) findViewById(R.id.txtSeguidosValor);
        numSeguidores = (TextView) findViewById(R.id.txtSeguidoresValor);
    }

    private void atualizarDados(){
        txtNome.setText(pessoaLogada.getNome());
        txtNicK.setText(usuarioLogado.getNick());
        txtNasc.setText(FormataData.formatarDataHoraDataBaseParaExibicao(pessoaLogada.getDataNasc()));
        txtSexo.setText(pessoaLogada.getSexo());
        txtEmail.setText(usuarioLogado.getEmail());

        RedeServices redeServices = new RedeServices(getApplicationContext());
        long longSeguidos = redeServices.qtdSeguidos(usuarioLogado.getId());
        numSeguidos.setText(Long.toString(longSeguidos));

        long longSeguidores = redeServices.qtdSeguidores(usuarioLogado.getId());
        numSeguidores.setText(Long.toString(longSeguidores));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_act_perfil, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_editar:
                Intent intent = new Intent(this, ActEditarPerfil.class);
                startActivity(intent);
                finish();
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

    public void onClickSeguidos(View view){
        Intent intent = new Intent(this, ActSeguidosSeguidores.class);
        sessao.addModo(SEGUIDOS);
        startActivity(intent);
        finish();
    }

    public void onClickSeguidores(View view){
        Intent intent = new Intent(this, ActSeguidosSeguidores.class);
        sessao.addModo(SEGUIDORES);
        startActivity(intent);
        finish();
    }
}