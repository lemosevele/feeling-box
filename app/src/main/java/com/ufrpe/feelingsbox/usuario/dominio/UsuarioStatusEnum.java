package com.ufrpe.feelingsbox.usuario.dominio;


public enum UsuarioStatusEnum {
    ATIVO("Ativo"), INATIVO("Inativo"), BANIDO("Banido");

    private String valor;

    UsuarioStatusEnum(String valor){ this.valor = valor; }

    public String getValor() { return valor; }
}