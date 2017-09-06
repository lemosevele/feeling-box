package com.ufrpe.feelingsbox.usuario.usuarioservices;

import android.content.Context;

import com.ufrpe.feelingsbox.infra.Criptografia;
import com.ufrpe.feelingsbox.infra.FormataData;
import com.ufrpe.feelingsbox.redesocial.dominio.Sessao;
import com.ufrpe.feelingsbox.redesocial.persistencia.SessaoDAO;
import com.ufrpe.feelingsbox.usuario.persistencia.PessoaDAO;
import com.ufrpe.feelingsbox.usuario.persistencia.UsuarioDAO;
import com.ufrpe.feelingsbox.usuario.dominio.Usuario;
import com.ufrpe.feelingsbox.usuario.dominio.Pessoa;

/**
 * Classe faz comunicação com a classe UsuarioDAO e validações, faz pesquisas no banco
 */

public class UsuarioService {
    private Sessao sessao = Sessao.getInstancia();
    private PessoaDAO pessoaDAO;
    private UsuarioDAO usuarioDAO;
    private Criptografia criptografia = new Criptografia();
    private Usuario usuario;
    private Pessoa pessoa;
    private SessaoDAO sessaoDAO;

    /**
     * Constructor
     * @param context
     */

    public UsuarioService(Context context) {
        pessoaDAO = new PessoaDAO(context);
        usuarioDAO = new UsuarioDAO(context);
        sessaoDAO = new SessaoDAO(context);
    }

    /**
     * Método que recebe dados de um usuário a ser cadastrado, valida os dados e solicita seu cadastro
     * @param nome Nome da Pessoa
     * @param sexo Sexo da Pessoa
     * @param nasc Data de nascimento da Pessoa
     * @param nick Nick do Usuario
     * @param email Email do Usuario
     * @param senha Senha do Usuario
     * @throws Exception Se existir um Nick ou Email já cadastrado
     * @see PessoaDAO
     * @see UsuarioDAO
     * @see Pessoa
     * @see Usuario
     * @see Criptografia
     */

    public void cadastrar(String nome, String sexo, String nasc, String nick, String email, String senha) throws Exception {
        Usuario verificarEmail = usuarioDAO.getUsuarioEmail(email);
        Usuario verificarNick = usuarioDAO.getUsuarioNick(nick);
        if (verificarEmail != null || verificarNick != null) {
            throw new Exception("Email ou Nick já cadastrado");
        } else {
            String senhaCriptografada = criptografia.criptografarSenha(senha);
            usuario = new Usuario();
            usuario.setSenha(senhaCriptografada);
            usuario.setEmail(email);
            usuario.setNick(nick);
            long idUsuario = usuarioDAO.inserirUsuario(usuario);
            usuario.setId(idUsuario);

            pessoa = new Pessoa();
            pessoa.setNome(nome);
            pessoa.setSexo(sexo);
            pessoa.setDataNasc(FormataData.americano(nasc));
            pessoa.setIdUsuario(idUsuario);
            long idPessoa = pessoaDAO.inserirPessoa(pessoa);
            pessoa.setId(idPessoa);

        }
    }

    /**
     * Método utilizado para logar usuario no sistema
     * @param nick Nick do Usuario a ser logado
     * @param senha Senha do Usuario a ser logado
     * @throws Exception Caso os dados não estejam corretos
     * @see Criptografia
     * @see UsuarioDAO
     * @see SessaoDAO
     */

    public void logarNick(String nick, String senha) throws Exception {
        String senhaCriptografada = criptografia.criptografarSenha(senha);
        Usuario nickValido = usuarioDAO.getUsuarioNickSenha(nick, senhaCriptografada);

        if (nickValido == null) {
            throw new Exception("Usuário ou senha inválidos");
        } else {
            Usuario usuario = usuarioDAO.getUsuarioNick(nick);
            Pessoa pessoa = pessoaDAO.getPessoa(usuario);
            sessao.setPessoaLogada(pessoa);
            sessao.setUsuarioLogado(usuario);
            sessaoDAO.inserirIdPessoa(sessao);
        }
    }

    /**
     * Método utilizado para logar automaticamente o usuario no sistema
     * @param nick Nick do usuario a ser logado
     * @param senha senha do usuari a ser logado
     */

    public void autoLogarNick(String nick, String senha) throws Exception {
        Usuario usuario = usuarioDAO.getUsuarioNick(nick);
        Pessoa pessoa = pessoaDAO.getPessoa(usuario);
        sessao.setPessoaLogada(pessoa);
        sessao.setUsuarioLogado(usuario);
        }

    /**
     * Método utilizado para logar usuario no sistema
     * @param email Email do Usuario a ser logado
     * @param senha Senha do Usuario logado
     * @see PessoaDAO
     * @see Criptografia
     * @see UsuarioDAO
     * @see SessaoDAO
     * @throws Exception Caso sejam fornecidos dados incorretos
     */

    public void logarEmail(String email, String senha) throws Exception {
        String senhaCriptografada = criptografia.criptografarSenha(senha);
        Usuario emailValido = usuarioDAO.getUsuarioEmailSenha(email, senhaCriptografada);

        if (emailValido == null) {
            throw new Exception("Usuário ou senha inválidos");
        }
        else {
            Usuario usuario = usuarioDAO.getUsuarioEmail(email);
            Pessoa pessoa = pessoaDAO.getPessoa(usuario);
            sessao.setPessoaLogada(pessoa);
            sessao.setUsuarioLogado(usuario);
            sessaoDAO.inserirIdPessoa(sessao);
        }
    }

    /**
     * Método que solicita a edição de perfil de um usuario
     * @param pessoaLogada Pessoa a ter seus dados editados
     * @param usuarioLogado Usuario a ter seus dados editados
     * @see PessoaDAO
     * @see UsuarioDAO
     */

    public void editarPerfil(Pessoa pessoaLogada, Usuario usuarioLogado){
        pessoaDAO.atualizarPessoa(pessoaLogada);
        usuarioDAO.atualizarUsuario(usuarioLogado);
    }

    /**
     * Método que retorna o nick do usuario
     * @param id Recebe id do Usuario a ter seu nick pesquisado
     * @return Nick do Usuario
     * @see UsuarioDAO
     */

    public String buscarNick(long id){
       usuario = usuarioDAO.getUsuarioId(id);
        return usuario.getNick();
    }

    /**
     * Busca um Usuario no banco de dados
     * @param id Recebe id do usuario a ser buscado
     * @return Rertorna Usuario pesquisado
     * @see UsuarioDAO
     */

    public Usuario buscarUsuario(long id){
        return usuarioDAO.getUsuarioId(id); }
}