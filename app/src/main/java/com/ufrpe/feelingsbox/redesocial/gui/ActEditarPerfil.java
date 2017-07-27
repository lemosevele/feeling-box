package com.ufrpe.feelingsbox.redesocial.gui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.ufrpe.feelingsbox.R;
import com.ufrpe.feelingsbox.infra.GuiUtil;
import com.ufrpe.feelingsbox.infra.Mask;
import com.ufrpe.feelingsbox.infra.Sessao;
import com.ufrpe.feelingsbox.infra.ValidacaoService;
import com.ufrpe.feelingsbox.usuario.dominio.Pessoa;
import com.ufrpe.feelingsbox.usuario.persistencia.PessoaDAO;

import static com.ufrpe.feelingsbox.usuario.dominio.SexoEnum.SexoEnumLista;

public class ActEditarPerfil extends AppCompatActivity {
    private EditText edtNomePerfil, edtNickPerfil, edtEmailPerfil, edtNascPerfil, edtSenhaPerfil;
    private Spinner spnSexoPerfil;

    //Lista para por no Spinner
    private String[] listaSexo = SexoEnumLista();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_editar_perfil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        edtNomePerfil = (EditText) findViewById(R.id.edtNomePerfil);
        edtNickPerfil = (EditText) findViewById(R.id.edtNickPerfil);
        edtEmailPerfil = (EditText) findViewById(R.id.edtEmailPerfil);
        edtNascPerfil = (EditText) findViewById(R.id.edtNascPerfil);
        edtSenhaPerfil = (EditText) findViewById(R.id.edtSenhaPerfil);
        edtNascPerfil.addTextChangedListener(Mask.insert("##/##/####", edtNascPerfil));

        //ArrayAdapter é usado preparar a lista da por no Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listaSexo);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Encontrando o Spinner e colocando a lista adaptada
        spnSexoPerfil = (Spinner)findViewById(R.id.spnSexoPerfil);
        spnSexoPerfil.setAdapter(adapter);
        //Setando o valor inicial do Spinner
        spnSexoPerfil.setSelection(adapter.getPosition("Masculino"));

        //Metodo para quando um elemento do Spinner é selecionado()
        spnSexoPerfil.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_act_editar_perfil, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_salvar:
                String nome = edtNomePerfil.getText().toString();
                String nasc = edtNascPerfil.getText().toString();
                String sexo = (String) spnSexoPerfil.getSelectedItem();
                ValidacaoService validaEdt = new ValidacaoService();
                boolean valid = true;
                if (validaEdt.isNascValido(nasc)){
                    edtNascPerfil.requestFocus();
                    edtNascPerfil.setError("Data de nascimento inválida.");
                    valid = false;
                }
                if (validaEdt.isCampoVazio(nome)){
                    edtNomePerfil.requestFocus();
                    edtNomePerfil.setError("Campo vazio.");
                    valid = false;
                }
                if (valid){
                    PessoaDAO pessoaDAO = new PessoaDAO(getApplicationContext());
                    Sessao sessao = new Sessao();
                    Pessoa pessoaLogada = sessao.getPessoaLogada();
                    pessoaLogada.setNome(nome);
                    pessoaLogada.setDataNasc(nasc);
                    pessoaLogada.setSexo(sexo);
                    pessoaDAO.atualizarPessoa(pessoaLogada);

                }

                break;
            case R.id.action_cancelar:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
