package com.ufrpe.feelingsbox.usuario.dominio;

/**
 * Created by Faig-PC on 23/07/2017.
 */

public enum SexoEnum {
    FEMININO('F'), MASCULINO('M'), OUTRO('O');

    private char valor;

    SexoEnum(char valor){
        this.valor = valor;
    }
    public char getValor(){
        return this.valor;
    }
    public String capitalize(){
        return this.toString().substring(0, 1) + this.toString().substring(1).toLowerCase();
    }
    public static String[] SexoEnumLista(){
        SexoEnum[] listaSexo = SexoEnum.values();
        String[] lista = new String[listaSexo.length];

        for (int i = 0; i < listaSexo.length; i++) {
            lista[i] = listaSexo[i].capitalize();
        }
        return lista;
    }
}