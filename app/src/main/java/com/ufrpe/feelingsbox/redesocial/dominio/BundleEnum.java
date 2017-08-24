package com.ufrpe.feelingsbox.redesocial.dominio;

public enum BundleEnum {
    RETORNO("retorno"), MODO("modo"), ID_USUARIO("idUsuario"), ID_POST("idPost"),
    SEGUIDOS("Seguindo"), SEGUIDORES("Seguidores"), MAIN_FRAG("mainFrag"),
    POST("Post"), COMENTARIO("Comentário"), TAB("tab");

    private String valor;

    BundleEnum(String valor) { this.valor = valor; }

    public String getValor() { return valor; }
}
