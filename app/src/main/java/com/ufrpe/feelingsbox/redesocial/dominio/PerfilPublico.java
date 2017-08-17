package com.ufrpe.feelingsbox.redesocial.dominio;

import com.ufrpe.feelingsbox.usuario.dominio.Usuario;

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

    public void setSeguidores(ArrayList<Usuario> seguidores) {
        if(this.seguidores == null){
            this.seguidores = seguidores;
        }
    }

    public void addSeguidor(Usuario usuario) {
        this.seguidores.add(usuario);
    }

    public void deleteSeguidor(Usuario seguidor){
        this.seguidores.remove(seguidor);
    }

    public ArrayList<Usuario> getSeguidos() {
        return seguidos;
    }

    public void setSeguidos(ArrayList<Usuario> seguidos) {
        if(this.seguidos == null){
            this.seguidos = seguidos;
        }
    }

    public void addSeguido(Usuario usuario) {
        this.seguidos.add(usuario);
    }

    public void deleteSeguido(Usuario seguido){
        this.seguidos.remove(seguido);
    }
}
