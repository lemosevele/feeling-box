package com.ufrpe.feelingsbox.usuario.dominio;


public class Pessoa {
    private String nome;
    private String sexo;
    private String dataNasc;
    private long id;
    private Usuario usuario;

    public void setNome(String novoNome){

        this.nome = novoNome;
    }

    public String getNome(){

        return this.nome;
    }

    public void setDataNasc(String novaData){

        this.dataNasc = novaData;
    }

    public String getDataNasc(){

        return this.dataNasc;
    }

    public void setSexo(String novoSexo){

        this.sexo = novoSexo;
    }

    public String getSexo(){

        return this.sexo;
    }

    public long getId(){

        return this.id;
    }

    public void setId(long novoId){

        this.id = novoId;
    }

    public Usuario getUsuario(){

        return this.usuario;
    }

    public void setUsuario(Usuario novoUsuario){

        this.usuario = novoUsuario;
    }

}
