package com.ufrpe.feelingsbox.redesocial.dominio;

public enum ActEnum {
    ACT_HOME("ActHome"), ACT_PERFIL("ActPerfil"), ACT_PERFIL_POST("ActPerfilPost"),
    ACT_SEGUIDOS_SEGUIDORES("ActSeguidosSeguidores");

    private String valor;

    ActEnum(String valor) { this.valor = valor; }

    public String getValor() { return valor; }
}
