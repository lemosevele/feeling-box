package com.ufrpe.feelingsbox.redesocial.dominio;

/**
 * Created by Faig-PC on 17/08/2017.
 */

public enum BundleEnum {
    RETORNO("retorno"), MODO("modo"), ID_USUARIO("idUsuario"), ID_POST("idPost"),
    SEGUIDOS("Seguidos"), SEGUIDORES("Seguidores"), MAIN_FRAG("mainFrag");

    private String valor;

    BundleEnum(String valor) { this.valor = valor; }

    public String getValor() { return valor; }
}
