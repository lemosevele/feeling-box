package com.ufrpe.feelingsbox.redesocial.gui;

import android.content.Intent;
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
import com.ufrpe.feelingsbox.infra.Criptografia;
import com.ufrpe.feelingsbox.infra.GuiUtil;
import com.ufrpe.feelingsbox.infra.Mask;
import com.ufrpe.feelingsbox.infra.Sessao;
import com.ufrpe.feelingsbox.infra.ValidacaoService;
import com.ufrpe.feelingsbox.usuario.dominio.Pessoa;
import com.ufrpe.feelingsbox.usuario.dominio.Usuario;
import com.ufrpe.feelingsbox.usuario.persistencia.PessoaDAO;
import com.ufrpe.feelingsbox.usuario.persistencia.UsuarioDAO;

import java.security.NoSuchAlgorithmException;

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        String nome = edtNomePerfil.getText().toString();
        String nick = edtNickPerfil.getText().toString();
        String email = edtEmailPerfil.getText().toString();
        String nasc = edtNascPerfil.getText().toString();
        String senha = edtSenhaPerfil.getText().toString();
        String sexo = (String) spnSexoPerfil.getSelectedItem();

        switch (item.getItemId()){
            case R.id.action_salvar:
                PessoaDAO pessoaDAO = new PessoaDAO(getApplicationContext());
                UsuarioDAO usuarioDAO = new UsuarioDAO(getApplicationContext());
                Sessao sessao = Sessao.getInstancia();
                Pessoa pessoaLogada = sessao.getPessoaLogada();
                Usuario usuarioLogado = sessao.getUsuarioLogado();
                ValidacaoService validaEdt = new ValidacaoService();
                boolean valid = true;
                boolean alteracao = false;
                pessoaLogada.setSexo(sexo);
                if (!validaEdt.isCampoVazio(senha)){
                    if (validaEdt.isSenhaValida(senha)){
                        Criptografia criptografia = new Criptografia();
                        String senhaCriptografada = null;
                        try {
                            senhaCriptografada = criptografia.criptografarSenha(senha);
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        }
                        usuarioLogado.setSenha(senhaCriptografada);
                        alteracao = true;
                    }
                    else{
                        edtSenhaPerfil.requestFocus();
                        edtSenhaPerfil.setError("Senha inválida.");
                        valid = false;
                    }
                }
                if (!validaEdt.isCampoVazio(nasc)){
                    if (validaEdt.isNascValido(nasc)){
                        pessoaLogada.setDataNasc(nasc);
                        alteracao = true;
                    }
                    else{
                        edtNascPerfil.requestFocus();
                        edtNascPerfil.setError("Data inválida.");
                        valid = false;
                    }
                }
                if (!validaEdt.isCampoVazio(email)) {
                    if (validaEdt.isEmailValido(email) && (usuarioDAO.getUsuarioEmail(email)==null)) {
                        usuarioLogado.setEmail(email);
                        alteracao = true;
                    }
                    else {
                        edtEmailPerfil.requestFocus();
                        edtEmailPerfil.setError("Email inválido.");
                        valid = false;
                    }
                }
                if (!validaEdt.isCampoVazio(nick)) {
                    if (validaEdt.isNickValido(nick) && (usuarioDAO.getUsuarioNick(nick)==null)) {
                        usuarioLogado.setNick(nick);
                        alteracao = true;
                    }
                    else {
                        edtNickPerfil.requestFocus();
                        edtNickPerfil.setError("Nick inválido.");
                        valid = false;
                    }
                }
                if (!validaEdt.isCampoVazio(nome)){
                    pessoaLogada.setNome(nome);
                    alteracao = true;
                }

                if (valid && alteracao){
                    usuarioDAO.atualizarUsuario(usuarioLogado);
                    pessoaDAO.atualizarPessoa(pessoaLogada);
                    GuiUtil.myToast(this, "Alterações salvas.");
                    Intent intent = new Intent(ActEditarPerfil.this, ActPerfil.class);
                    startActivity(intent);
                }

                break;
            case R.id.action_cancelar:
                finish();
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}