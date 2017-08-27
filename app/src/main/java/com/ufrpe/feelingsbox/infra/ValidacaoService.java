package com.ufrpe.feelingsbox.infra;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;

import com.ufrpe.feelingsbox.usuario.persistencia.UsuarioDAO;

/**
 * Classe responsável pela validação de @see {@link android.widget.EditText}.
 */

public class ValidacaoService {
    private UsuarioDAO usuarioDAO;
    private static final int TAMANHO_DATA = 10;

    /**
     * Construtor - instancia @see {@link UsuarioDAO} como atributo da Classe.
     * @param context - Contexto da aplicação
     */

    public ValidacaoService(Context context){
        this.usuarioDAO = new UsuarioDAO(context);
    }

    /**
     * Método verifica se o texto recebido está vazio.
     * @param campo - Texto da @see {@link android.widget.EditText}
     * @return - Verdadeiro para um texto vazio.
     */
    public boolean isCampoVazio(String campo) {
         return (TextUtils.isEmpty(campo) || campo.trim().isEmpty());
    }

    /**
     * Método verifica se um E-mail foi recebido.
     * @param campo - Texto da @see {@link android.widget.EditText}
     * @return - Verdadeiro para um E-mail.
     */

    public boolean isEmail(String campo){
        return (Patterns.EMAIL_ADDRESS.matcher(campo).matches());
    }

    /**
     * Método verifica se o apelido não existe no banco de dados
     * @param nick - Texto da @see {@link android.widget.EditText}
     * @return - Verdadeiro caso o apelido não exista no banco de dados.
     */

    public boolean isNickValido(String nick) {
        return  (!isCampoVazio(nick) && !isEmail(nick) && (usuarioDAO.getUsuarioEmail(nick) == null));
    }

    /**
     * Método verifica se o E-mail não existe no banco de dados
     * @param email - Texto da @see {@link android.widget.EditText}
     * @return - Verdadeiro caso o E-mail não exista no banco de dados.
     */

    public boolean isEmailValido(String email) {
        return (!isCampoVazio(email) && isEmail(email) && (usuarioDAO.getUsuarioEmail(email) == null));
    }

    /**
     * Método verifica se a data é válida.
     * @param nasc - Texto da @see {@link android.widget.EditText}
     * @return - Verdadeiro caso a data esteja no formato dd/mm/yyyy e seja menor que a data atual.
     */

    public boolean isNascValido(String nasc) {
        return(FormataData.dataExiste(nasc) && FormataData.dataMenorOuIgualQueAtual(nasc) && nasc.length() == TAMANHO_DATA);
    }

    /**
     * Método verifica se a senha contém entre 6 e 12 caracteres, onde ao menos uma letra seja minúscula, maiúscula e um número.
     * @param senha - Texto com a Senha.
     * @return - Retorna verdadeiro caso a senha esteja no formato válido.
     */

    public boolean isSenhaValida(String senha){
        if (isCampoVazio(senha)) {
            return false;
        } else {
            String rex = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,12})";
            return (senha.matches(rex));
        }
    }

    /**
     * Método verifica se o primeiro caracter do texto contém um #.
     * @param textoTag - Texto com a tag digitada.
     * @return - Retorna verdadeiro caso a tag esteja no formato válido.
     */

    public boolean verificarTagPesquisada(String textoTag){
      return textoTag.matches("(#\\w+)");
    }
}