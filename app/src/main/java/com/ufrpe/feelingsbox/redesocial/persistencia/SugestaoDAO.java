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
    private PostDAO postDAO;

    public SugestaoDAO(Context context){
        dbHelper = new DataBase(context);
        postDAO = new PostDAO(context);
    }

    public List<Post> getPostsMaisComentadosHoje(){
        feelingsDb = dbHelper.getReadableDatabase();
        ArrayList<Post> listaPostsFiltrados = new ArrayList<>();
        String horaHoje = FormataData.formataDataHoraHoje();
        /*
        String query = "SELECT " + DataBase.COMENTARIO_POST_ID + ", " + DataBase.POST_DATAHORA +
                ", COUNT("+DataBase.COMENTARIO_POST_ID +") AS qtd" +
                " FROM " + DataBase.TABELA_COMENTARIO + " GROUP BY " +DataBase.COMENTARIO_POST_ID + " ORDER BY qtd DESC";*/

        String query = "SELECT " + DataBase.COMENTARIO_POST_ID + "AS postIdC, " + DataBase.POST_DATAHORA +
                ", COUNT("+DataBase.COMENTARIO_POST_ID +") AS qtd" +
                " FROM " + DataBase.TABELA_COMENTARIO + " INNER JOIN " + DataBase.TABELA_POST +
                "ON postIdC = " + DataBase.ID + " WHERE " + DataBase.POST_DATAHORA + " >= " + horaHoje +
                " GROUP BY " +DataBase.COMENTARIO_POST_ID + " ORDER BY qtd DESC";

        Cursor cursor = feelingsDb.rawQuery(query, null);

        while (cursor.moveToNext()){
            String colunaPostId = DataBase.COMENTARIO_POST_ID;
            int indexColunaPostId = cursor.getColumnIndex(colunaPostId);
            long idPost = cursor.getInt(indexColunaPostId);
            Post post = postDAO.getPostId(idPost);
            listaPostsFiltrados.add(post);
        }

        feelingsDb.close();
        return listaPostsFiltrados;
    }
}
