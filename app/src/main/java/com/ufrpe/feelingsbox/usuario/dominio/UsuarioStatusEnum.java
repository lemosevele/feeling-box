package com.ufrpe.feelingsbox.usuario.dominio;

/**
 * Created by Faig-PC on 26/07/2017.
 */

public enum UsuarioStatusEnum {
    ATIVO("Ativo"), INATIVO("Inativo"), BANIDO("Banido");

    private String valor;

    UsuarioStatusEnum(String valor){ this.valor = valor; }

}
