package com.ufrpe.feelingsbox.redesocial.dominio;

import com.ufrpe.feelingsbox.infra.FormataData;
import java.util.ArrayList;

public class Post {
    private String texto;
    private ArrayList<Tag> listaTags = new ArrayList<Tag>();
    private long idUsuario;
    private long id;
    private String dataHora;
    private ArrayList<Comentario> listaComentarios = new ArrayList<Comentario>();

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public ArrayList<Tag> getListaTags() {
        return listaTags;
    }

    public void setListaTags(ArrayList<Tag> listaTags) {
        this.listaTags = listaTags;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public void setDataHora(){
        dataHora = FormataData.formatarDataHoraAtualParaPostDataBase();
    }

    public ArrayList<Comentario> getListaComentarios() {
        return listaComentarios;
    }

    public void setListaComentarios(ArrayList<Comentario> listaComentarios) {
        this.listaComentarios = listaComentarios;
    }
}