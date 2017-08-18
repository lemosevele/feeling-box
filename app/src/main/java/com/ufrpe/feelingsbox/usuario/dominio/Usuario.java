package com.ufrpe.feelingsbox.usuario.dominio;

public class Usuario {
    private String email;
    private String senha;
    private String nick;
    private long id;
    private byte[] foto;

    public String getEmail(){

        return this.email;
    }

    public void setEmail(String novoEmail){

        this.email = novoEmail;
    }

    public String getSenha(){
        return this.senha;
    }

    public void setSenha(String novaSenha){

        this.senha = novaSenha;
    }

    public String getNick(){

        return this.nick;
    }

    public void setNick(String novoNick){
        this.nick = novoNick;
    }

    public long getId(){

        return this.id;
    }

    public void setId(long novoId){

        this.id = novoId;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
}