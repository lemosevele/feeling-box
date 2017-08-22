package com.ufrpe.feelingsbox.redesocial.dominio;

import com.ufrpe.feelingsbox.redesocial.gui.ActHome;
import com.ufrpe.feelingsbox.redesocial.gui.ActPerfil;
import com.ufrpe.feelingsbox.redesocial.gui.ActPerfilPost;
import com.ufrpe.feelingsbox.redesocial.gui.ActPost;
import com.ufrpe.feelingsbox.redesocial.gui.ActSeguidosSeguidores;

public enum ActEnum {
    ACT_HOME(ActHome.class), ACT_PERFIL(ActPerfil.class), ACT_PERFIL_POST(ActPerfilPost.class),
    ACT_SEGUIDOS_SEGUIDORES(ActSeguidosSeguidores.class), ACT_POST(ActPost.class);

    private Class valor;

    ActEnum(Class valor) { this.valor = valor; }

    public Class getValor() { return valor; }
}
