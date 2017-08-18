package com.ufrpe.feelingsbox.usuario.dominio;


public class Pessoa {
    private String nome;
    private String sexo;
    private String dataNasc;
    private long id;
    private long idUsuario;

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

    public long getIdUsuario(){

        return this.idUsuario;
    }

    public void setIdUsuario(long novoIdUsuario){

        this.idUsuario = novoIdUsuario;
    }
}