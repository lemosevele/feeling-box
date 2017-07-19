package com.ufrpe.projeto.feelingsbox.dominio.usuario;

import java.util.Date;

/**
 * Created by evele on 18/07/2017.
 */

public class Pessoa {
    private String nome;
    private String sexo;
    private Date dataNasc;
    private int id;

    public void setNome(String novoNome){

        this.nome = novoNome;
    }

    public String getNome(){

        return this.nome;
    }

    public void setDataNasc(Date novaData){

        this.dataNasc = novaData;
    }

    public Date getDataNasc(){

        return this.dataNasc;
    }

    public void setSexo(String novoSexo){

        this.sexo = novoSexo;
    }

    public String getSexo(){

        return this.sexo;
    }

    public int getId(){

        return this.id;
    }

    public void setId(int novoId){

        this.id = novoId;
    }

}
