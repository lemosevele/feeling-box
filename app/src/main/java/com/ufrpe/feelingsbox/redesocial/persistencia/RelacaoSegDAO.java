package com.ufrpe.feelingsbox.redesocial.persistencia;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ufrpe.feelingsbox.infra.DataBase;
import com.ufrpe.feelingsbox.usuario.dominio.PerfilPublico;
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

    public PerfilPublico criarPerfil(long id){
        PerfilPublico perfilPublico = new PerfilPublico();
        perfilPublico.setSeguidores(getSeguidoresUser(id));
        perfilPublico.setSeguidos(getSeguidosUser(id));
        return perfilPublico;
    }
}
