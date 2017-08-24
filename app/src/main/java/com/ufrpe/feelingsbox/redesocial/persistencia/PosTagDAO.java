package com.ufrpe.feelingsbox.redesocial.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ufrpe.feelingsbox.infra.DataBase;
import com.ufrpe.feelingsbox.redesocial.dominio.Post;

import java.util.ArrayList;
import java.util.List;

public class PosTagDAO {
    private DataBase dbHelper;
    private SQLiteDatabase feelingsDb;
    private PostDAO postDAO;

    public PosTagDAO(Context context){
        this.postDAO = new PostDAO(context);
        this.dbHelper = new DataBase(context);
    }

    public ArrayList<Post> getPostByTag(String tag) {
        feelingsDb = dbHelper.getReadableDatabase();
        ArrayList<Post> listaPost = new ArrayList<>();

        String query = "SELECT * FROM " + DataBase.TABELA_REL_TAG_POST +
            " WHERE " + DataBase.REL_TEXTO_TAG + " LIKE ?" +
                " ORDER BY " + DataBase.ID + " DESC";

        String[] argumentos = {tag};

        Cursor cursor = feelingsDb.rawQuery(query, argumentos);

        while (cursor.moveToNext()){
            String colunaTagPostId = DataBase.REL_ID_POST;
            int indexColunaTagPostId = cursor.getColumnIndex(colunaTagPostId);
            long idPost = cursor.getInt(indexColunaTagPostId);
            listaPost.add(postDAO.getPostId(idPost));
        }

        cursor.close();
        feelingsDb.close();

        return listaPost;
    }

    public long inserirTagIdPost(String tag, long idPost){
        feelingsDb = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        String colunaTag =  DataBase.REL_TEXTO_TAG;
        values.put(colunaTag, tag);

        String colunaIdPost = DataBase.REL_ID_POST;
        values.put(colunaIdPost, idPost);

        String tabela = DataBase.TABELA_REL_TAG_POST;
        long id = feelingsDb.insert(tabela, null, values);

        feelingsDb.close();

        return id;
    }

    public boolean getRelacaoTagPost(long idPost, String tag){
        feelingsDb = dbHelper.getWritableDatabase();

        String query = "SELECT * FROM " + DataBase.TABELA_REL_TAG_POST +
                " WHERE " + DataBase.REL_ID_POST + " LIKE ? AND " +
                DataBase.REL_TEXTO_TAG + " LIKE ?";

        String idString = Long.toString(idPost);
        String[] argumentos = {idString, tag};

        Cursor cursor = feelingsDb.rawQuery(query, argumentos);

        return cursor.moveToNext();
    }
}