package com.ufrpe.feelingsbox.feelingsbox.controle.dominio.usuario;

/**
 * Created by evele on 18/07/2017.
 */

public class Usuario {
    private String email;
    private String senha;
    private String nick;
    private int id;
    private Pessoa pessoa;

    public String getEmail(){

        return this.email;
    }

    public void setEmail(String novoEmail){

        this.email = novoEmail;
    }

    public String getSenha(){
        return this.senha;
    }

    public void setSenha(String novaSenha){

        this.senha = novaSenha;
    }

    public String getNick(){

        return this.nick;
    }

    public void setNick(String novoNick){
        this.nick = novoNick;
    }

    public int getId(){

        return this.id;
    }

    public void setId(int novoId){

        this.id = novoId;
    }

    public Pessoa getPessoa(){

        return this.pessoa;
    }

    public void setPessoa(Pessoa novaPessoa){

        this.pessoa = novaPessoa;
    }
}
