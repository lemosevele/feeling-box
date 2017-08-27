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
import com.ufrpe.feelingsbox.infra.FormataData;
import com.ufrpe.feelingsbox.infra.GuiUtil;
import com.ufrpe.feelingsbox.infra.Mask;
import com.ufrpe.feelingsbox.infra.ValidacaoService;
import com.ufrpe.feelingsbox.redesocial.dominio.Sessao;
import com.ufrpe.feelingsbox.usuario.dominio.Pessoa;
import com.ufrpe.feelingsbox.usuario.dominio.Usuario;
import com.ufrpe.feelingsbox.usuario.usuarioservices.UsuarioService;

import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import static com.ufrpe.feelingsbox.usuario.dominio.SexoEnum.sexoEnumLista;

/**
 * Classe responsável pela Tela de Edição de Perfil.
 */

public class ActEditarPerfil extends AppCompatActivity {
    private EditText edtNomePerfil, edtNickPerfil, edtEmailPerfil, edtNascPerfil, edtSenhaPerfil;
    private Spinner spnSexoPerfil;
    private Sessao sessao = Sessao.getInstancia();

    //Lista para por no Spinner
    private String[] listaSexo = sexoEnumLista();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_editar_perfil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        encontrandoItens();
        encontrandoSpinner();
    }

    /**
     * Método que declara os tipos dos atributos que referenciam os Campos do @see {@link EditText}
     * e o @see {@link Spinner} na Tela.
     */

    private void encontrandoItens(){
        edtNomePerfil  = (EditText) findViewById(R.id.edtNomePerfil);
        edtNickPerfil  = (EditText) findViewById(R.id.edtNickPerfil);
        edtEmailPerfil = (EditText) findViewById(R.id.edtEmailPerfil);
        edtNascPerfil  = (EditText) findViewById(R.id.edtNascPerfil);
        edtSenhaPerfil = (EditText) findViewById(R.id.edtSenhaPerfil);
        edtNascPerfil.addTextChangedListener(Mask.insert("##/##/####", edtNascPerfil));
    }

    /**
     * Método que define os valores a serem exibidos no @see {@link Spinner}, onde o valor
     * da seleção inicial é definido com base no Sexo do usuário (@see {@link Pessoa}) logado.
     */

    private void encontrandoSpinner(){
        //ArrayAdapter é usado preparar a lista da por no Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listaSexo);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Encontrando o Spinner e colocando a lista adaptada
        spnSexoPerfil = (Spinner)findViewById(R.id.spnSexoPerfil);
        spnSexoPerfil.setAdapter(adapter);
        //Setando o valor inicial do Spinner
        String stringSexo = sessao.getPessoaLogada().getSexo();
        spnSexoPerfil.setSelection(adapter.getPosition(stringSexo));
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
        String nome  = edtNomePerfil.getText().toString();
        String nick  = edtNickPerfil.getText().toString();
        String email = edtEmailPerfil.getText().toString();
        String nasc  = edtNascPerfil.getText().toString();
        String senha = edtSenhaPerfil.getText().toString();
        String sexo  = (String) spnSexoPerfil.getSelectedItem();

        switch (item.getItemId()){
            case R.id.action_salvar:
                editaPerfil(nome, nick, email, nasc, senha, sexo);
                break;
            case R.id.action_cancelar:
                retornaPerfil();
                break;
            case android.R.id.home:
                retornaPerfil();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        retornaPerfil();
        super.onBackPressed();
    }

    /**
     * Método retorna para a @see {@link ActPerfil}
     */

    private void retornaPerfil(){
        Intent intent = new Intent(this, ActPerfil.class);
        startActivity(intent);
        finish();
    }

    /**
     * Método envia os dados digitados para serem validados pela Classe @see {@link ValidacaoService}.
     * e caso a validação seja positiva, envia os dados para serem gravados no banco de dados. @see {@link UsuarioService}.
     * @param nome - Nome @see {@link Pessoa}.
     * @param nick - Apelido @see {@link Usuario}.
     * @param email - E-mail do @see {@link Usuario}.
     * @param nasc - Data de Nascimento @see {@link Pessoa}.
     * @param senha - Senha @see {@link Usuario}.
     * @param sexo - Sexo @see {@link Pessoa}.
     */

    private void editaPerfil(String nome, String nick, String email, String nasc, String senha, String sexo){
        Pessoa pessoaLogada = sessao.getPessoaLogada();
        Usuario usuarioLogado = sessao.getUsuarioLogado();
        ValidacaoService validaEdt = new ValidacaoService(getApplicationContext());
        boolean alteracao = false;
        if (!Objects.equals(sexo, pessoaLogada.getSexo())){
            pessoaLogada.setSexo(sexo);
            alteracao = true;
        }
        if (!validaEdt.isCampoVazio(senha)){
            alteracao = validaSenha(senha, usuarioLogado, validaEdt);
        }
        if (!validaEdt.isCampoVazio(nasc)){
            alteracao = validaNasc(nasc, pessoaLogada, validaEdt);
        }
        if (!validaEdt.isCampoVazio(email)) {
            alteracao = validaEmail(email, usuarioLogado, validaEdt);
        }
        if (!validaEdt.isCampoVazio(nick)) {
            alteracao = validaNick(nick, usuarioLogado, validaEdt);
        }
        if (!validaEdt.isCampoVazio(nome)){
            pessoaLogada.setNome(nome);
            alteracao = true;
        }

        if (alteracao){
            UsuarioService usuarioService = new UsuarioService(getApplicationContext());
            usuarioService.editarPerfil(pessoaLogada, usuarioLogado);
            GuiUtil.myToast(this, getString(R.string.print_msg_alteracoes_salva));
            retornaPerfil();
        }
    }

    /**
     * Método envia a senha para ser Criptografada @see {@link Criptografia}.
     * @param senha - Senha digitada.
     * @param usuarioLogado - @see {@link Usuario} logado.
     * @param validaEdt - Instância de @see {@link ValidacaoService}.
     * @return - Verdadeiro quando a senha é criptografada com sucesso.
     */

    private boolean validaSenha(String senha, Usuario usuarioLogado, ValidacaoService validaEdt){
        if (validaEdt.isSenhaValida(senha)){
            Criptografia criptografia = new Criptografia();
            String senhaCriptografada = null;
            try {
                senhaCriptografada = criptografia.criptografarSenha(senha);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            usuarioLogado.setSenha(senhaCriptografada);
            return true;
        }
        else{
            edtSenhaPerfil.requestFocus();
            edtSenhaPerfil.setError(getString(R.string.print_erro_validacao_edt_senha_invalida));
            return false;
        }
    }

    /**
     * Método envia a Data para ser formatada @see {@link FormataData}.
     * @param nasc - Data de Nascimento @see {@link Pessoa}.
     * @param pessoaLogada - @see {@link Pessoa} logada.
     * @param validaEdt - Instância de @see {@link ValidacaoService}.
     * @return - Retorna verdadeiro quando a Data digitada está no formato correto.
     */

    private boolean validaNasc(String nasc, Pessoa pessoaLogada, ValidacaoService validaEdt) {
        if (validaEdt.isNascValido(nasc)) {
            pessoaLogada.setDataNasc(FormataData.americano(nasc));
            return true;
        } else {
            edtNascPerfil.requestFocus();
            edtNascPerfil.setError(getString(R.string.print_erro_validacao_edt_data_invalida));
            return false;
        }
    }

    /**
     * Método envia o E-mail para ser validado @see {@link ValidacaoService}.
     * @param email - E-mail @see {@link Usuario}.
     * @param usuarioLogado - @see {@link Usuario} logado.
     * @param validaEdt  - Instância de @see {@link ValidacaoService}.
     * @return - Retorna verdadeiro quando o E-mail digitado está no formato correto.
     */

    private boolean validaEmail(String email, Usuario usuarioLogado, ValidacaoService validaEdt){
        if (validaEdt.isEmailValido(email)) {
            usuarioLogado.setEmail(email);
            return true;
        }
        else {
            edtEmailPerfil.requestFocus();
            edtEmailPerfil.setError(getString(R.string.print_erro_validacao_edt_email_invalido));
            return false;
        }
    }

    /**
     * Método envia o apelido para ser validado. @see {@link ValidacaoService}.
     * @param nick - Apelido @see {@link Usuario}.
     * @param usuarioLogado - @see {@link Usuario} logado.
     * @param validaEdt  - Instância de @see {@link ValidacaoService}.
     * @return - Retorna verdadeiro quando o E-mail digitado é válido.
     */

    private boolean validaNick(String nick, Usuario usuarioLogado, ValidacaoService validaEdt){
        if (validaEdt.isNickValido(nick)){
            usuarioLogado.setNick(nick);
            return true;
        }
        else {
            edtNickPerfil.requestFocus();
            edtNickPerfil.setError(getString(R.string.print_erro_validacao_edt_nick_invalido));
            return false;
        }
    }
}