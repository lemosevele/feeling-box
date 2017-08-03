package com.ufrpe.feelingsbox.redesocial.persistencia;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ufrpe.feelingsbox.infra.DataBase;

public class TagDAO {
    private DataBase dbHelper;
    private SQLiteDatabase feelingsDb;

    public TagDAO(Context context){

        dbHelper = new DataBase(context);
    }

    public long inserirTag(String tag){
        feelingsDb = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        String colunaTexto =  DataBase.TAG_TEXTO;
        values.put(colunaTexto, tag);

        String tabela = DataBase.TABELA_TAG;
        long id = feelingsDb.insert(tabela, null, values);

        feelingsDb.close();

        return id;
    }

    public String criarTag(Cursor cursor){
        String colunaTexto = DataBase.TAG_TEXTO;
        int indexColunaTexto = cursor.getColumnIndex(colunaTexto);
        return cursor.getString(indexColunaTexto);
    }

    public String getTagTexto(String texto) {        //Retorna Tag se existe, se nao, retorna null
        feelingsDb = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM " + DataBase.TABELA_TAG +
                " WHERE " + DataBase.TAG_TEXTO + " LIKE ?";

        String[] argumentos = {texto};

        Cursor cursor = feelingsDb.rawQuery(query, argumentos);

        String tag = null;

        if (cursor.moveToNext()) {

            tag = criarTag(cursor);
        }

        cursor.close();
        feelingsDb.close();

        return tag;
    }

}
