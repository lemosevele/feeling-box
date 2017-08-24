package com.ufrpe.feelingsbox.redesocial.dominio;

public enum PostVisibilidadeEnum {
    PUBLICO("Publico"), ANONIMO("Anonimo");

    private String valor;

    PostVisibilidadeEnum(String valor){ this.valor = valor; }

    public String getValor() { return valor; }
}
