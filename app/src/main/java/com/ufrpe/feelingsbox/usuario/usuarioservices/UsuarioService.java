package com.ufrpe.feelingsbox.usuario.usuarioservices;

import android.content.Context;

import com.ufrpe.feelingsbox.infra.Criptografia;
import com.ufrpe.feelingsbox.usuario.persistencia.PessoaDAO;
import com.ufrpe.feelingsbox.usuario.persistencia.UsuarioDAO;
import com.ufrpe.feelingsbox.usuario.dominio.Usuario;
import com.ufrpe.feelingsbox.usuario.dominio.Pessoa;
import com.ufrpe.feelingsbox.infra.SessaoUsuario;

// Classe faz comunicação com a classe UsuarioDAO e validações, faz pesquisas no banco
//

public class UsuarioService {
    private SessaoUsuario sessao = SessaoUsuario.getInstancia();
    private PessoaDAO pessoaDAO;
    private UsuarioDAO usuarioDAO;
    private Criptografia criptografia = new Criptografia();
    private Usuario usuario;
    private Pessoa pessoa;

    public UsuarioService(Context context) {
        pessoaDAO = new PessoaDAO(context);
        usuarioDAO = new UsuarioDAO(context);
    }

    public void cadastrar(String nome, String sexo, String DataNasc, String nick, String email, String senha) throws Exception {
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
            pessoa.setDataNasc(DataNasc);
            pessoa.setUsuario(usuario);
            long idPessoa = pessoaDAO.inserirPessoa(pessoa);
            pessoa.setId(idPessoa);

            sessao.setUsuarioLogado(usuario);
            sessao.setPessoaLogada(pessoa);
        }
    }

    public void logarNick(String nick, String senha) throws Exception {
        String senhaCriptografada = criptografia.criptografarSenha(senha);
        Usuario nickValido = usuarioDAO.getUsuarioNickSenha(nick, senhaCriptografada);
        if (nickValido == null) {
            throw new Exception("Usuário ou senha inválidos");
        } else {
            Usuario usuario = usuarioDAO.getUsuarioNick(nick);
            sessao.setUsuarioLogado(usuario);
            Pessoa pessoa = pessoaDAO.getPessoa(usuario);
            sessao.setPessoaLogada(pessoa);
        }
    }

    public void logarEmail(String email, String senha) throws Exception {
        String senhaCriptografada = criptografia.criptografarSenha(senha);
        Usuario emailValido = usuarioDAO.getUsuarioEmailSenha(email, senhaCriptografada);

        if (emailValido == null) {
            throw new Exception("Usuário ou senha inválidos");
        } else {
            Usuario usuario = usuarioDAO.getUsuarioEmail(email);
            sessao.setUsuarioLogado(usuario);
            Pessoa pessoa = pessoaDAO.getPessoa(usuario);
            sessao.setPessoaLogada(pessoa);
        }
    }
}