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

        String query = "SELECT P." + DataBase.ID + ", P." + DataBase.POST_TEXTO + ", P." + DataBase.POST_DATAHORA +
                ", P." + DataBase.POST_VISIVEL + ", P." + DataBase.POST_STATUS + ", P." + DataBase.POST_USER_ID +
                ", qtde FROM " + DataBase.TABELA_POST + " AS P INNER JOIN (SELECT " + DataBase.COMENTARIO_POST_ID + ", "
                + "COUNT(" + DataBase.COMENTARIO_POST_ID + ") AS qtde FROM " + DataBase.TABELA_COMENTARIO + "GROUP BY " + DataBase.ID +") AS " +
                "C ON P." + DataBase.ID +" = C." + DataBase.ID + " WHERE P." + DataBase.POST_DATAHORA + "=" + horaHoje + " OR " +
                DataBase.POST_DATAHORA + "=" + horaHoje + " ORDER BY " + DataBase.ID + " DESC";

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
