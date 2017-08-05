package com.ufrpe.feelingsbox.redesocial.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ufrpe.feelingsbox.infra.DataBase;
import com.ufrpe.feelingsbox.redesocial.dominio.Post;

import java.util.ArrayList;

public class PosTagDAO {
    private DataBase dbHelper;
    private SQLiteDatabase feelingsDb;
    private TagDAO tagDAO;
    private PostDAO postDAO;

    public PosTagDAO(Context context){
        this.tagDAO = new TagDAO(context);
        this.postDAO = new PostDAO(context);
        this.dbHelper = new DataBase(context);
    }

    public ArrayList<String> getTagsPost(long idPost) {
        feelingsDb = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM " + DataBase.TABELA_REL_TAG_POST +
                " WHERE " + DataBase.REL_ID_POST + " LIKE ?";

        String idString = Long.toString(idPost);
        String[] argumentos = {idString};

        Cursor cursor = feelingsDb.rawQuery(query, argumentos);

        String tag;
        ArrayList<String> listaTags = new ArrayList<String>();

        while (cursor.moveToNext()){
            tag = tagDAO.criarTag(cursor);
            listaTags.add(tag);
        }

        cursor.close();
        feelingsDb.close();

        return listaTags;
    }

    public ArrayList<Post> getPostTag(String texto) {
        feelingsDb = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM " + DataBase.TABELA_REL_TAG_POST +
                " WHERE " + DataBase.REL_TEXTO_TAG + " LIKE ?";

        String[] argumentos = {texto};

        Cursor cursor = feelingsDb.rawQuery(query, argumentos);

        Post post;
        ArrayList<Post> listaPost = new ArrayList<>();

        while (cursor.moveToNext()){
            post = postDAO.criarPost(cursor);
            listaPost.add(post);
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

}