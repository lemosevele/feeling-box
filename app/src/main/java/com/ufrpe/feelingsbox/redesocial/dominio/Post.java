package com.ufrpe.feelingsbox.redesocial.dominio;

import android.os.Parcel;
import android.os.Parcelable;

import com.ufrpe.feelingsbox.infra.FormataData;
import java.util.ArrayList;
import java.util.Objects;

public class Post implements Parcelable{
    private String texto;
    private ArrayList<String> listaTags = new ArrayList<String>();
    private long idUsuario;
    private long id;
    private String dataHora;
    private ArrayList<Comentario> listaComentarios = new ArrayList<Comentario>();

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public ArrayList<String> getListaTags() {
        return listaTags;
    }

    public void setListaTags(ArrayList<String> listaTags) {
        this.listaTags = listaTags;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public void setDataHora() {
        dataHora = FormataData.formatarDataHoraAtualParaPostDataBase();
    }

    public ArrayList<Comentario> getListaComentarios() {
        return listaComentarios;
    }

    public Post() {}

    public Post (Parcel parcel){
        setTexto(parcel.readString());
        setIdUsuario(parcel.readLong());
        setId(parcel.readLong());
        setDataHora(parcel.readString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(texto);
        dest.writeStringList(listaTags);
        dest.writeLong(idUsuario);
        dest.writeLong(id);
        dest.writeString(dataHora);
    }

    public static final Parcelable.Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Post post = (Post) o;

        return getId() == post.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id);
    }
}