package com.ufrpe.feelingsbox.redesocial.dominio;

public enum PostStatusEnum {
    ATIVO("Ativo"), EXClUIDO("Excluido");

    private String valor;

    PostStatusEnum(String valor){ this.valor = valor; }

    public String getValor() { return valor; }
}
