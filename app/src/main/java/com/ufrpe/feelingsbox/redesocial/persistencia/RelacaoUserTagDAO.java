package com.ufrpe.feelingsbox.redesocial.persistencia;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ufrpe.feelingsbox.infra.DataBase;

import java.util.ArrayList;


public class RelacaoUserTagDAO {
    private DataBase dbHelper;
    private SQLiteDatabase feelingsDb;

    public RelacaoUserTagDAO(Context context) {
        this.dbHelper = new DataBase(context);
    }

    public long inserirRelUserTag(String tag, long idUser){
        feelingsDb = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        String colunaTag =  DataBase.REL_TEXTO_TAG;
        values.put(colunaTag, tag);

        String colunaUser = DataBase.REL_USER_ID;
        values.put(colunaUser, idUser);

        String tabela = DataBase.TABELA_REL_USER_TAG;
        long id = feelingsDb.insert(tabela, null, values);

        feelingsDb.close();
        return id;
    }

    public String criarTag(Cursor cursor) {
        String colunaTexto = DataBase.REL_TEXTO_TAG;
        int indexColunaTexto = cursor.getColumnIndex(colunaTexto);
        return cursor.getString(indexColunaTexto);
    }

    public ArrayList<String> getTagsByUser(long idUser) {
        feelingsDb = dbHelper.getReadableDatabase();
        ArrayList<String> listaSeguidores = new ArrayList<>();

        String query = "SELECT * FROM " + DataBase.TABELA_REL_USER_TAG +
                " WHERE " + DataBase.REL_USER_ID + " LIKE ?" +
                " ORDER BY " + DataBase.ID + " DESC";

        String idString = Long.toString(idUser);
        String[] argumentos = {idString};

        Cursor cursor = feelingsDb.rawQuery(query, argumentos);

        while (cursor.moveToNext()) {
            String tag = criarTag(cursor);
            listaSeguidores.add(tag);
        }
        cursor.close();
        feelingsDb.close();
        return listaSeguidores;
    }
}