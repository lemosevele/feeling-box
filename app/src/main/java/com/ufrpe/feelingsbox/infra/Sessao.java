package com.ufrpe.feelingsbox.infra;

import com.ufrpe.feelingsbox.usuario.dominio.Pessoa;
import com.ufrpe.feelingsbox.usuario.dominio.Usuario;

/**
 * Criada para manter o status do usuário
 */

public class Sessao {
    private static Sessao instancia = new Sessao();

    private Pessoa pessoaLogada = null;

    private Sessao(){

    }

    public static Sessao getInstancia(){

        return instancia;
    }

    public Pessoa getPessoaLogada(){
        return pessoaLogada;
    }

    public void setPessoaLogada(Pessoa novaPessoa) {
        this.pessoaLogada = novaPessoa;
    }

    public void invalidarSessao() {
        pessoaLogada = null;
    }
}
