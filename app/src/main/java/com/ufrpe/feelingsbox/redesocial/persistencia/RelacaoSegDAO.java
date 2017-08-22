package com.ufrpe.feelingsbox.redesocial.persistencia;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.ufrpe.feelingsbox.infra.DataBase;
import com.ufrpe.feelingsbox.usuario.dominio.Usuario;
import com.ufrpe.feelingsbox.usuario.persistencia.UsuarioDAO;

import java.util.ArrayList;


public class RelacaoSegDAO {
    private DataBase dbHelper;
    private SQLiteDatabase feelingsDb;
    private UsuarioDAO usuarioDAO;

    public RelacaoSegDAO(Context context) {
        this.dbHelper = new DataBase(context);
        this.usuarioDAO = new UsuarioDAO(context);
    }

    public ArrayList<Usuario> getSeguidoresUser(long id) {
        feelingsDb = dbHelper.getReadableDatabase();
        ArrayList<Usuario> listaSeguidores = new ArrayList<>();

        String query = "SELECT * FROM " + DataBase.TABELA_REL_SEGUIDORES +
                " WHERE " + DataBase.SEGUIDO_ID + " LIKE ?" +
                " ORDER BY " + DataBase.ID + " DESC";

        String idString = Long.toString(id);
        String[] argumentos = {idString};

        Cursor cursor = feelingsDb.rawQuery(query, argumentos);

        Usuario usuario;

        while (cursor.moveToNext()) {
            String colunaId = DataBase.SEGUIDOR_ID;
            int indexColunaId = cursor.getColumnIndex(colunaId);
            long idSeguidor = cursor.getInt(indexColunaId);
            usuario = usuarioDAO.getUsuarioId(idSeguidor);
            listaSeguidores.add(usuario);
        }
        cursor.close();
        feelingsDb.close();
        return listaSeguidores;
    }

    public long getQtdSeguidoresUser(long id) {
        feelingsDb = dbHelper.getReadableDatabase();
        String idString = Long.toString(id);
        String[] argumentos = {idString};

        Long resultado = DatabaseUtils.queryNumEntries(feelingsDb, DataBase.TABELA_REL_SEGUIDORES,
                DataBase.SEGUIDO_ID + "= ?", argumentos);
        feelingsDb.close();
        return resultado;
    }

    public long getQtdSeguidosUser(long id) {
        feelingsDb = dbHelper.getReadableDatabase();
        String idString = Long.toString(id);
        String[] argumentos = {idString};

        Long resultado = DatabaseUtils.queryNumEntries(feelingsDb, DataBase.TABELA_REL_SEGUIDORES,
                DataBase.SEGUIDOR_ID + "= ?", argumentos);
        feelingsDb.close();
        return resultado;
    }

    public ArrayList<Usuario> getSeguidosUser(long id) {
        feelingsDb = dbHelper.getReadableDatabase();
        ArrayList<Usuario> listaSeguidos = new ArrayList<>();

        String query = "SELECT * FROM " + DataBase.TABELA_REL_SEGUIDORES +
                " WHERE " + DataBase.SEGUIDOR_ID + " LIKE ?" +
                " ORDER BY " + DataBase.ID + " DESC";

        String idString = Long.toString(id);
        String[] argumentos = {idString};

        Cursor cursor = feelingsDb.rawQuery(query, argumentos);

        Usuario usuario;

        while (cursor.moveToNext()) {
            String colunaId = DataBase.SEGUIDO_ID;
            int indexColunaId = cursor.getColumnIndex(colunaId);
            long idSeguidor = cursor.getInt(indexColunaId);
            usuario = usuarioDAO.getUsuarioId(idSeguidor);
            listaSeguidos.add(usuario);
        }
        cursor.close();
        feelingsDb.close();
        return listaSeguidos;
    }

    public long inserirRelSeguidores(long idSeguidor, long idSeguido){
        feelingsDb = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        String colunaSeguidor =  DataBase.SEGUIDOR_ID;
        values.put(colunaSeguidor, idSeguidor);

        String colunaSeguido = DataBase.SEGUIDO_ID;
        values.put(colunaSeguido, idSeguido);

        String tabela = DataBase.TABELA_REL_SEGUIDORES;
        long id = feelingsDb.insert(tabela, null, values);

        feelingsDb.close();

        return id;
    }

    public void deletarRelSeguidores(long idSeguidor, long idSeguido){
        feelingsDb = dbHelper.getWritableDatabase();

        String idSeguidorStr = Long.toString(idSeguidor);
        String idSeguidoStr = Long.toString(idSeguido);

        feelingsDb.delete(DataBase.TABELA_REL_SEGUIDORES, DataBase.SEGUIDOR_ID + "=? AND " +
                DataBase.SEGUIDO_ID + "=?", new String[] {idSeguidorStr,idSeguidoStr});
    }



    public boolean verificaSeguidor (long idSeguidor, long idSeguido){
        feelingsDb = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM " + DataBase.TABELA_REL_SEGUIDORES +
                " WHERE " + DataBase.SEGUIDOR_ID + " LIKE ?" +
                " AND " + DataBase.SEGUIDO_ID  + " LIKE ?";

        String idString = Long.toString(idSeguidor);
        String idString1 = Long.toString(idSeguido);

        String[] argumentos = {idString, idString1};

        Cursor cursor = feelingsDb.rawQuery(query, argumentos);

        return (cursor.moveToNext());

    }
}
