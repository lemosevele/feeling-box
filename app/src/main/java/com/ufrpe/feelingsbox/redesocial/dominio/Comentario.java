package com.ufrpe.feelingsbox.redesocial.dominio;


import com.ufrpe.feelingsbox.infra.FormataData;

public class Comentario {
    private String texto;
    private long id;
    private long idUsuario;
    private long idPost;
    private String dataHora;


    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public long getIdPost() {
        return idPost;
    }

    public void setIdPost(long idPost) {
        this.idPost = idPost;
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
}
