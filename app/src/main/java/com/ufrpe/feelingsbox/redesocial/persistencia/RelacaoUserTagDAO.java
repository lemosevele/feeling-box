package com.ufrpe.feelingsbox.redesocial.persistencia;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ufrpe.feelingsbox.infra.DataBase;

import java.util.ArrayList;

/**
 * Classe de persistência para o relacionamento em tag e Usuario
 */

public class RelacaoUserTagDAO {
    private DataBase dbHelper;
    private SQLiteDatabase feelingsDb;


    /**
     * Constructor
     * @param context
     */

    public RelacaoUserTagDAO(Context context) {
        this.dbHelper = new DataBase(context);
    }

    /**
     * Método que insere relacionamento entre tag e Usuario no banco de dados na TABELA_REL_USER_TAG
     * que recebe uma tag que foi pesquisada ou usada por um Usuario
     * @param tag Recebe tag pesquisada ou usada por um Usuario
     * @param idUser Recebe id do Usuario
     * @return Rertorna id que foi inserido na tabela de relacionamento
     */

    public long inserirRelUserTag(String tag, long idUser) {
        feelingsDb = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        String colunaTag = DataBase.REL_TEXTO_TAG;
        values.put(colunaTag, tag);

        String colunaUser = DataBase.REL_USER_ID;
        values.put(colunaUser, idUser);

        String tabela = DataBase.TABELA_REL_USER_TAG;
        long id = feelingsDb.insert(tabela, null, values);

        feelingsDb.close();
        return id;
    }

    /**
     * Método utilizado para retornar uma Tag no banco de dados
     * @param cursor Cursor que irá percorrer as colunas da tabela
     * @return Retorna tag encontrada na linha da tabela
     */

    public String pesquisarTag(Cursor cursor) {
        String colunaTexto = DataBase.REL_TEXTO_TAG;
        int indexColunaTexto = cursor.getColumnIndex(colunaTexto);
        return cursor.getString(indexColunaTexto);
    }

    /**
     * Método pesquisa as tags utilizadas pelo Usuario
     * @param idUser recebe id do usuario a ter tags utilizadas por ele pesquisadas
     * @return Retorna Array com tags pesquisadas pelo Usuario
     */

    public ArrayList<String> getTagsByUser(long idUser) {
        feelingsDb = dbHelper.getReadableDatabase();
        ArrayList<String> listaTags = new ArrayList<>();

        String query = "SELECT * FROM " + DataBase.TABELA_REL_USER_TAG +
                " WHERE " + DataBase.REL_USER_ID + " LIKE ?" +
                " ORDER BY " + DataBase.ID + " DESC";

        String idString = Long.toString(idUser);
        String[] argumentos = {idString};

        Cursor cursor = feelingsDb.rawQuery(query, argumentos);

        while (cursor.moveToNext()) {
            String tag = pesquisarTag(cursor);
            listaTags.add(tag);
        }
        cursor.close();
        feelingsDb.close();
        return listaTags;
    }

    /**
     * Método que pesquisa tag mais pesquisada pelo Usuario
     * @param idUser Recebe id do Usuario
     * @return Retorna tag mais utilizada pelo Usuario
     */

    public String getTagsMaisPesquisadas(long idUser) {
        feelingsDb = dbHelper.getReadableDatabase();

        String query = "SELECT COUNT (" + DataBase.REL_TEXTO_TAG + ") AS qtd, " + DataBase.REL_TEXTO_TAG + " FROM " +
                DataBase.TABELA_REL_USER_TAG + " WHERE " + DataBase.REL_USER_ID + " LIKE ?" +
                " ORDER BY qtd DESC";

        String idString = Long.toString(idUser);
        String[] argumentos = {idString};

        Cursor cursor = feelingsDb.rawQuery(query, argumentos);

        String tag = null;
        if (cursor.moveToNext()) {
            tag = pesquisarTag(cursor);
        }

        cursor.close();
        feelingsDb.close();
        return tag;
    }
}