package com.ufrpe.feelingsbox.usuario.gui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.ufrpe.feelingsbox.R;
import com.ufrpe.feelingsbox.infra.GuiUtil;
import com.ufrpe.feelingsbox.infra.Mask;
import com.ufrpe.feelingsbox.infra.ValidacaoService;
import com.ufrpe.feelingsbox.usuario.usuarioservices.UsuarioService;

import static com.ufrpe.feelingsbox.usuario.dominio.SexoEnum.sexoEnumLista;

/**
 * Classe responsável pela Tela de Cadastro.
 */

public class ActSignUp extends AppCompatActivity {
    private EditText edtNome, edtNick, edtEmail, edtNasc, edtSenha;
    private Spinner spinner;
    private String[] listaSexo = sexoEnumLista();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_sign_up);

        edtNome = (EditText)findViewById(R.id.edtNome);
        edtNick = (EditText)findViewById(R.id.edtNick);
        edtEmail = (EditText)findViewById(R.id.edtEmail);
        edtNasc = (EditText)findViewById(R.id.edtNasc);
        edtNasc.addTextChangedListener(Mask.insert("##/##/####", edtNasc));
        edtSenha = (EditText)findViewById(R.id.edtSenha);

        //ArrayAdapter é usado para preparar a lista que será usada no Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listaSexo);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner = (Spinner)findViewById(R.id.spnSexo);
        spinner.setAdapter(adapter);

        //Metodo para quando um elemento do Spinner é selecionado()
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * Método passará os dados digitados pelo usuário para serem validados pela Classe @see {@link ValidacaoService},
     * após uma válidação positiva, os dados serão enviados para a Classe @see {@link UsuarioService}
     * para serem gravadas no banco de dados. Mensagens de erro serão mostradas caso a validação ou o registro
     * no banco de dados falhem.
     * @param view - Referência ao Botão Cadastrar @see {@link View} e {@link com.ufrpe.feelingsbox.R.layout}.
     */

    public void validarCadastrar(View view){
        String nome     = edtNome.getText().toString();
        String nick     = edtNick.getText().toString();
        String email    = edtEmail.getText().toString();
        String nasc     = edtNasc.getText().toString();
        String senha    = edtSenha.getText().toString();
        String sexoTexto = (String) spinner.getSelectedItem();

        ValidacaoService validacaoCadastro = new ValidacaoService(getApplicationContext());
        boolean valid = true;
        if (!validacaoCadastro.isSenhaValida(senha)){
            edtSenha.requestFocus();
            edtSenha.setError(getString(R.string.msg_senha_fora_padrão));
            valid = false;
        }
        if (!validacaoCadastro.isNascValido(nasc)){
            edtNasc.requestFocus();
            edtNasc.setError(getString(R.string.msg_data_invalida));
            valid = false;
        }
        if (!validacaoCadastro.isEmailValido(email)){
            edtEmail.requestFocus();
            edtEmail.setError(getString(R.string.msg_email_invalido));
            valid = false;
        }
        if (!validacaoCadastro.isNickValido(nick)){
            edtNick.requestFocus();
            edtNick.setError(getString(R.string.msg_nick_invalido));
            valid = false;
        }

        if (validacaoCadastro.isCampoVazio(nome)){
            edtNome.requestFocus();
            edtNome.setError(getString(R.string.msg_nome_invalido));
            valid = false;
        }

        if (valid) {
            UsuarioService service = new UsuarioService(getApplicationContext());
            try {
                service.cadastrar(nome, sexoTexto, nasc, nick, email, senha);
                GuiUtil.myToast(this, getString(R.string.msg_cadastro_sucesso));
                this.retornarTela();
            } catch (Exception e) {
                GuiUtil.myToast(this, e);
            }
        }
    }

    /**
     * Retorna para a @see {@link ActLogin}.
     * @param view - Referência ao Botão Cancelar @see {@link View} e {@link com.ufrpe.feelingsbox.R.layout}.
     */

    public void cancelarCadastro(View view){
        this.retornarTela();
    }

    private void retornarTela(){
        Intent intent = new Intent(this, ActLogin.class);
        startActivity(intent);
        finish();
    }
}