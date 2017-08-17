package com.ufrpe.feelingsbox.redesocial.persistencia;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ufrpe.feelingsbox.infra.DataBase;
import com.ufrpe.feelingsbox.infra.FormataData;
import com.ufrpe.feelingsbox.redesocial.dominio.Post;

import java.util.ArrayList;
import java.util.List;



public class SugestaoDAO {
    private DataBase dbHelper;
    private SQLiteDatabase feelingsDb;
    private ComentarioDAO comentarioDAO;

    public SugestaoDAO(Context context){
        dbHelper = new DataBase(context);
        comentarioDAO = new ComentarioDAO(context);
    }

    public List<Post> getPostsMaisComentadosHoje(){
        feelingsDb = dbHelper.getReadableDatabase();
        ArrayList<Post> listaPostsFiltrados = new ArrayList<>();
        String horaHoje = FormataData.formatarDataHoraAtualParaPostDataBase();

        feelingsDb.close();
        return listaPostsFiltrados;
    }
}
