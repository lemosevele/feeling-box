package com.ufrpe.feelingsbox.redesocial.persistencia;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ufrpe.feelingsbox.redesocial.dominio.Tag;
import com.ufrpe.feelingsbox.infra.DataBase;

public class TagDAO {
    private DataBase dbHelper;
    private SQLiteDatabase feelingsDb;

    public TagDAO(Context context){

        dbHelper = new DataBase(context);
    }

    public long inserirTag(Tag tag){
        feelingsDb = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        String colunaTexto =  DataBase.TAG_TEXTO;
        String texto = tag.getTexto();
        values.put(colunaTexto, texto);

        String tabela = DataBase.TABELA_TAG;
        long id = feelingsDb.insert(tabela, null, values);

        feelingsDb.close();

        return id;
    }

    public Tag criarTag(Cursor cursor){
        String colunaId = DataBase.ID;
        int indexColunaId= cursor.getColumnIndex(colunaId);
        long id = cursor.getInt(indexColunaId);

        String colunaTexto = DataBase.TAG_TEXTO;
        int indexColunaTexto = cursor.getColumnIndex(colunaTexto);
        String texto = cursor.getString(indexColunaTexto);

        Tag tag = new Tag(texto);
        tag.setId(id);

        return tag;
    }

    public Tag getTagTexto(String texto) { //Retorna Tag se existe, se nao, retorna null
        feelingsDb = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM " + DataBase.TABELA_TAG +
                " WHERE " + DataBase.TAG_TEXTO + " LIKE ?";

        String[] argumentos = {texto};

        Cursor cursor = feelingsDb.rawQuery(query, argumentos);

         Tag tag = null;

        if (cursor.moveToNext()) {

            tag = criarTag(cursor);
        }

        cursor.close();
        feelingsDb.close();

        return tag;
    }

}
