package com.ufrpe.feelingsbox.persistencia.usuariodao;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ufrpe.feelingsbox.dominio.usuario.Usuario;
import com.ufrpe.feelingsbox.persistencia.FeelingsBoxDbHelper;


public class UsuarioDAO {

    private FeelingsBoxDbHelper dbHelper;
    private SQLiteDatabase feelingsDb;

    public UsuarioDAO(Context context) {

        dbHelper = new FeelingsBoxDbHelper(context);
    }

    public Usuario getUsuario(String email, String senha) {
        feelingsDb = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM " + FeelingsBoxDbHelper.TABELA_USUARIO +
                " WHERE " + FeelingsBoxDbHelper.USUARIO_EMAIL + " LIKE ? AND " +
                FeelingsBoxDbHelper.USUARIO_SENHA + " LIKE ?";

        String[] argumentos = {email, senha};

        Cursor cursor = feelingsDb.rawQuery(query, argumentos);

        Usuario usuario = null;

        if (cursor.moveToNext()) {
            usuario = criarUsuario(cursor);
        }
        cursor.close();
        feelingsDb.close();

        return usuario;
    }

    public Usuario getUsuario(String nick) {
        feelingsDb = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM " + FeelingsBoxDbHelper.TABELA_USUARIO +
                " WHERE " + FeelingsBoxDbHelper.USUARIO_NICK + " LIKE ?";

        String[] argumentos = {nick};

        Cursor cursor = feelingsDb.rawQuery(query, argumentos);

        Usuario usuario = null;

        if (cursor.moveToNext()) {

            usuario = criarUsuario(cursor);
        }

        cursor.close();
        feelingsDb.close();

        return usuario;
    }


    public Usuario getUsuario(int id){
        feelingsDb = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM " + FeelingsBoxDbHelper.TABELA_USUARIO +
                " WHERE " + FeelingsBoxDbHelper.ID + " LIKE ?";

        String idString = Long.toString(id);
        String[] argumentos = {idString};

        Cursor cursor = feelingsDb.rawQuery(query, argumentos);

        Usuario usuario = null;

        if(cursor.moveToNext()){

            usuario = criarUsuario(cursor);
        }

        cursor.close();
        feelingsDb.close();

        return usuario;
    }

    public Usuario getUsuarioEmail(String email) {
        feelingsDb = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM " + FeelingsBoxDbHelper.TABELA_USUARIO +
                " WHERE " + FeelingsBoxDbHelper.USUARIO_EMAIL + " LIKE ?";

        String[] argumentos = {email};

        Cursor cursor = feelingsDb.rawQuery(query, argumentos);

        Usuario usuario = null;

        if (cursor.moveToNext()) {

            usuario = criarUsuario(cursor);
        }

        cursor.close();
        feelingsDb.close();

        return usuario;
    }

    public long inserirUsuario(Usuario usuario){
        feelingsDb = dbHelper.getReadableDatabase();

        ContentValues values = new ContentValues();

        String colunaEmail = FeelingsBoxDbHelper.USUARIO_EMAIL;
        String email = usuario.getEmail();
        values.put(colunaEmail, email);

        String colunaSenha = FeelingsBoxDbHelper.USUARIO_SENHA;
        String senha = usuario.getSenha();
        values.put(colunaSenha, senha);

        String colunaNick = FeelingsBoxDbHelper.USUARIO_NICK;
        String nick = usuario.getNick();
        values.put(colunaNick, nick);

        String tabela = FeelingsBoxDbHelper.TABELA_USUARIO;
        long id = feelingsDb.insert(tabela, null, values);

        feelingsDb.close();
        return id;
    }

    // CURSOR PERCORRE AS COLUNAS DA TABELA E CRIA UM OBJETO PESSOA

    public Usuario criarUsuario(Cursor cursor){

        String colunaId = FeelingsBoxDbHelper.ID;
        int indexColunaId= cursor.getColumnIndex(colunaId);
        long id = cursor.getInt(indexColunaId);

        String colunaEmail = FeelingsBoxDbHelper.USUARIO_EMAIL;
        int indexColunaEmail = cursor.getColumnIndex(colunaEmail);
        String email = cursor.getString(indexColunaEmail);

        String colunaSenha = FeelingsBoxDbHelper.USUARIO_SENHA;
        int indexColunaSenha = cursor.getColumnIndex(colunaSenha);
        String senha = cursor.getString(indexColunaSenha);

        String colunaNick = FeelingsBoxDbHelper.USUARIO_NICK;
        int indexColunaNick = cursor.getColumnIndex(colunaNick);
        String nick = cursor.getString(indexColunaNick);

        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setEmail(email);
        usuario.setSenha(senha);
        usuario.setNick(nick);

        return usuario;
    }

}