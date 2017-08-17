package com.ufrpe.feelingsbox.redesocial.dominio;

import com.ufrpe.feelingsbox.usuario.dominio.Pessoa;
import com.ufrpe.feelingsbox.usuario.dominio.Usuario;

public class Sessao {
    private static Sessao instancia = new Sessao();
    private Pessoa pessoaLogada = null;
    private Usuario usuarioLogado = null;
    private PerfilPublico perfillogado = null;

    public static Sessao getInstancia(){

        return instancia;
    }

    public Pessoa getPessoaLogada(){
        return pessoaLogada;
    }

    public void setPessoaLogada(Pessoa novaPessoa) {
        this.pessoaLogada = novaPessoa;
    }

    public Usuario getUsuarioLogado() { return usuarioLogado; }

    public void setUsuarioLogado(Usuario novoUsuario) {
        this.usuarioLogado = novoUsuario;
    }

    public PerfilPublico getPerfillogado() {
        return perfillogado;
    }

    public void setPerfillogado(PerfilPublico perfillogado) {
        this.perfillogado = perfillogado;
    }

    public void invalidarSessao() {
        pessoaLogada = null;
        usuarioLogado = null;
        perfillogado = null;
    }
}