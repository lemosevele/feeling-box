package com.ufrpe.feelingsbox.redesocial.dominio;

public class Tag {
    private String texto;
    private long id;

    public Tag(String texto){
        this.texto = texto;
    }

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
}