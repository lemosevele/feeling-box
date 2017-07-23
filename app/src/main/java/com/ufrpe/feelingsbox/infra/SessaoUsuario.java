package com.ufrpe.feelingsbox.infra;

import com.ufrpe.feelingsbox.usuario.dominio.Pessoa;
import com.ufrpe.feelingsbox.usuario.dominio.Usuario;

/**
 * Criada para manter o status do usuário
 */

public class SessaoUsuario {
    private static SessaoUsuario instancia = new SessaoUsuario();

    private Usuario usuarioLogado = null;
    private Pessoa pessoaLogada = null;

    private SessaoUsuario(){}

    public static SessaoUsuario getInstancia(){
        return instancia;
    }

    public Pessoa getPessoaLogada(){
        return pessoaLogada;
    }

    public void setPessoaLogada(Pessoa novaPessoa){
        this.pessoaLogada = novaPessoa;
    }

    public Usuario getUsuarioLogado(){
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario novoUsuario){
        this.usuarioLogado = novoUsuario;
    }

    public void invalidarSessao(){
        usuarioLogado = null;
        pessoaLogada = null;
    }
}
