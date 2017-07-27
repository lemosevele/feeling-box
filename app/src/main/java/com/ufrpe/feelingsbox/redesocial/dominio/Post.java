package com.ufrpe.feelingsbox.redesocial.dominio;

import com.ufrpe.feelingsbox.infra.FormataData;
import java.util.ArrayList;

public class Post {
    private String texto;
    private ArrayList<Tag> listaTags = new ArrayList<Tag>();
    private long idUsuario;
    private long id;
    private String dataHora;

    public Post(){
        dataHora = FormataData.formatarDataHora();
    }

    public String getDataHora(){
        return dataHora;
    }

    public ArrayList<Tag> getListaTags(){
        return this.listaTags;
    }

    public void addTag(Tag novaTag){
        listaTags.add(novaTag);
    }

    public void removerTag(Tag tag){
        listaTags.remove(tag);
    }

    public String getTexto(){

        return this.texto;
    }

    public void setTexto(String novoTexto){
        this.texto = novoTexto;
    }

    public long getidUsuario(){
        return this.idUsuario;
    }

    public void setIdUsuario(long novoId){
        this.idUsuario = novoId;
    }

    public long getId(){
        return this.id;
    }

    public void setId(long novoId){
        this.id = novoId;
    }

}