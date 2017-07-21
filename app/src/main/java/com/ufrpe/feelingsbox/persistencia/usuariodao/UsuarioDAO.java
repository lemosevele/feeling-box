package com.ufrpe.feelingsbox.persistencia.usuariodao;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ufrpe.feelingsbox.dominio.usuario.Usuario;
import com.ufrpe.feelingsbox.persistencia.FeelingsBoxDbHelper;


public class UsuarioDAO {

    private FeelingsBoxDbHelper dbHelper;


    public UsuarioDAO(Context context) {

        dbHelper = new FeelingsBoxDbHelper(context);
    }


    public Usuario getUsuario(String email, String senha) {
        SQLiteDatabase feelingsDb = dbHelper.getReadableDatabase();

        String comando = "SELECT * FROM " + FeelingsBoxDbHelper.TABELA_USUARIO +
                " WHERE " + FeelingsBoxDbHelper.USUARIO_EMAIL + " LIKE ? AND " +
                FeelingsBoxDbHelper.USUARIO_SENHA + " LIKE ?";

        String[] argumentos = {email, senha};

        Cursor cursor = feelingsDb.rawQuery(comando, argumentos);

        Usuario usuario = null;

        if (cursor.moveToNext()) {
            usuario = criarUsuario(cursor);
        }
        cursor.close();
        feelingsDb.close();

        return usuario;
    }

    public Usuario getUsuario(String nick) {
        SQLiteDatabase feelingsDb = dbHelper.getReadableDatabase();

        String comando = "SELECT * FROM " + FeelingsBoxDbHelper.TABELA_USUARIO +
                " WHERE " + FeelingsBoxDbHelper.USUARIO_NICK + " LIKE ?";

        String[] argumentos = {nick};

        Cursor cursor = feelingsDb.rawQuery(comando, argumentos);

        Usuario usuario = null;

        if (cursor.moveToNext()) {

            usuario = criarUsuario(cursor);
        }

        cursor.close();
        feelingsDb.close();

        return usuario;
    }



    public Usuario getUsuario(int id){
        SQLiteDatabase feelingsDb = dbHelper.getReadableDatabase();

        String comando = "SELECT * FROM " + FeelingsBoxDbHelper.TABELA_USUARIO +
                " WHERE " + FeelingsBoxDbHelper.ID + " LIKE ?";

        String idString = Long.toString(id);
        String[] argumentos = {idString};

        Cursor cursor = feelingsDb.rawQuery(comando, argumentos);

        Usuario usuario = null;

        if(cursor.moveToNext()){

            usuario = criarUsuario(cursor);
        }

        cursor.close();
        feelingsDb.close();

        return usuario;
    }

    public long inserir(Usuario usuario){
        SQLiteDatabase feelingsDb = dbHelper.getReadableDatabase();

        ContentValues values = new ContentValues();

        String emailColumn = FeelingsBoxDbHelper.USUARIO_EMAIL;
        String email = usuario.getEmail();
        values.put(emailColumn, email);

        String senhaColumn = FeelingsBoxDbHelper.USUARIO_SENHA;
        String senha = usuario.getSenha();
        values.put(senhaColumn, senha);

        String nickColumn = FeelingsBoxDbHelper.USUARIO_NICK;
        String nick = usuario.getNick();
        values.put(nickColumn, nick);

        String tabela = FeelingsBoxDbHelper.TABELA_USUARIO;
        long id = feelingsDb.insert(tabela, null, values);

        feelingsDb.close();
        return id;
    }

    public Usuario criarUsuario(Cursor cursor){

        String idColumn = FeelingsBoxDbHelper.ID;
        int indexColumnID = cursor.getColumnIndex(idColumn);
        int id = cursor.getInt(indexColumnID);

        String emailColumn = FeelingsBoxDbHelper.USUARIO_EMAIL;
        int indexColumnEmail = cursor.getColumnIndex(emailColumn);
        String email = cursor.getString(indexColumnEmail);

        String senhaColumn = FeelingsBoxDbHelper.USUARIO_SENHA;
        int indexColumnSenha = cursor.getColumnIndex(senhaColumn);
        String senha = cursor.getString(indexColumnSenha);

        String nickColumn = FeelingsBoxDbHelper.USUARIO_NICK;
        int indexColumnNick = cursor.getColumnIndex(nickColumn);
        String nick = cursor.getString(indexColumnNick);

        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setEmail(email);
        usuario.setSenha(senha);
        usuario.setNick(nick);

        return usuario;
    }

}