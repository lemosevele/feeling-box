package com.ufrpe.feelingsbox.usuario.dominio;

import java.util.ArrayList;

public class PerfilPublico {
    private long idUsuario;
    private ArrayList<Usuario> seguidores = new ArrayList<>();
    private ArrayList<Usuario> seguidos = new ArrayList<>();

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public ArrayList<Usuario> getSeguidores() {
        return seguidores;
    }

    public void addSeguidor(Usuario usuario) {
        this.seguidores.add(usuario);
    }

    public ArrayList<Usuario> getSeguidos() {
        return seguidos;
    }

    public void addSeguido(Usuario usuario) {
        this.seguidos.add(usuario);
    }

    public void deleteSeguido(Usuario usuario){
        this.seguidos.remove(usuario);
    }
}
