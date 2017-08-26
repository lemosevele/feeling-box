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

    public UsuarioService(Context context) {
        pessoaDAO = new PessoaDAO(context);
        usuarioDAO = new UsuarioDAO(context);
        sessaoDAO = new SessaoDAO(context);
    }

    /**
     * Método que faz verificações e comunicação com o banco para cadastrar um usuário;
     * @param nome
     * @param sexo
     * @param nasc
     * @param nick
     * @param email
     * @param senha
     * @throws Exception
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

    public void autoLogarNick(String nick, String senha) throws Exception {
        Usuario usuario = usuarioDAO.getUsuarioNick(nick);
        Pessoa pessoa = pessoaDAO.getPessoa(usuario);
        sessao.setPessoaLogada(pessoa);
        sessao.setUsuarioLogado(usuario);
        sessaoDAO.inserirIdPessoa(sessao);
        }

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

    public void editarPerfil(Pessoa pessoaLogada, Usuario usuarioLogado){
        pessoaDAO.atualizarPessoa(pessoaLogada);
        usuarioDAO.atualizarUsuario(usuarioLogado);
    }

    public String buscarNick(long id){
       usuario = usuarioDAO.getUsuarioId(id);
        return usuario.getNick();
    }

    public Usuario buscarUsuario(long id){ return usuarioDAO.getUsuarioId(id); }
}