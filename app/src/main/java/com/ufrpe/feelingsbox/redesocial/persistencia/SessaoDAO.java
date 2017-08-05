package com.ufrpe.feelingsbox.redesocial.persistencia;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.content.Context;

import com.ufrpe.feelingsbox.infra.DataBase;
import com.ufrpe.feelingsbox.redesocial.dominio.Sessao;

public class SessaoDAO {
    private DataBase dbHelper;
    private SQLiteDatabase feelingsDb;

    public SessaoDAO (Context context){
        dbHelper = new DataBase(context);
    }

    // Recebe a Sessao da pessoa a logar e insere na tabela
    public void inserirIdPessoa(Sessao sessao){
        feelingsDb = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        String colunaIdPessoa = DataBase.ID_PESSOA;
        values.put(colunaIdPessoa, sessao.getPessoaLogada().getId());

        feelingsDb.insert(DataBase.TABELA_SESSAO, null, values);
    }


    // Pesquisa idPessoa, se pessoa estiver logada retorna True, se não, retorna False
    public boolean getIdPessoaLogada(long id){
        feelingsDb = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM " + DataBase.TABELA_SESSAO +
                " WHERE " + DataBase.ID_PESSOA + " LIKE ?";

        String idString = Long.toString(id);
        String[] argumentos = {idString};

        Cursor cursor = feelingsDb.rawQuery(query, argumentos);

        boolean idPessoa = false;

        if(cursor.moveToNext()){

            idPessoa = true;
        }

        cursor.close();
        feelingsDb.close();

        return idPessoa;
    }

    //Encerra a sessao
    public void removerPessoa() {
        feelingsDb = dbHelper.getWritableDatabase();
        feelingsDb.delete(DataBase.TABELA_SESSAO, null, null);
        feelingsDb.close();
    }
}
