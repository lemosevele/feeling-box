package com.ufrpe.feelingsbox.persistencia.usuariodao;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ufrpe.feelingsbox.dominio.usuario.Usuario;
import com.ufrpe.feelingsbox.persistencia.FeelingsBoxDbHelper;

/**
 * Classe de persistencia para Usuário
 *
 */

public class UsuarioDAO {

    private FeelingsBoxDbHelper dbHelper;

    /**
     * Construtor
     *
     * @param context Contexto do Usuário
     */

    public UsuarioDAO(Context context) {
        dbHelper = new FeelingsBoxDbHelper(context);
    }

    /**
     * Busca um Usuario na tabela TABLE_USER do banco de dados
     *
     * @param email Email do Usuário a ser buscado no Banco de dados.
     * @param senha Senha do Usuário a ser buscada no Banco de dados.
     * @return Retorna um objeto da classe Usuário caso exista um Usuario cadastrado ou Null caso não exista.
     */

    public Usuario getUsuario(String email, String senha) {
        SQLiteDatabase feelingsDb = dbHelper.getReadableDatabase();

        String comando = "SELECT * FROM " + FeelingsBoxDbHelper.TABLE_USER +
                " WHERE " + FeelingsBoxDbHelper.COLUMN_EMAIL + " LIKE ? AND " +
                FeelingsBoxDbHelper.COLUMN_SENHA + " LIKE ?";

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

    /**
     * Busca um Usuario na tabela TABLE_USER do banco de dados
     *
     * @param nick Nick do Usuario a ser buscado no Banco de dados.
     * @return Retorna um objeto da Classe Usuario caso exista um Usuario cadastrado ou Null caso não exista.
     */

    public Usuario getUsuario(String nick) {
        SQLiteDatabase feelingsDb = dbHelper.getReadableDatabase();

        String comando = "SELECT * FROM " + FeelingsBoxDbHelper.TABLE_USER +
                " WHERE " + FeelingsBoxDbHelper.COLUMN_NICK + " LIKE ?";

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

    /**
     *
     * Busca o Usuario na tabela TABLE_USER no Banco de dados atravez de seu ID
     *
     * @param id Id do Usuario a ser buscado
     * @return Retorna um objeto da Classe Usuario caso exista um Usuario cadastrado ou Null caso não exista.
     */

    public Usuario getUsuario(long id){
        SQLiteDatabase feelingsDb = dbHelper.getReadableDatabase();

        String comando = "SELECT * FROM " + FeelingsBoxDbHelper.TABLE_USER +
                " WHERE " + FeelingsBoxDbHelper.COLUMN_ID + " LIKE ?";

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

    /**
     *
     *Insere um novo Usuario na tabela TABLE_USER do Banco de dados caso não exista um Usuario com e-mail identico cadastrado.
     *
     * @param usuario Passa uma objeto do tipo Usuario.
     *
     */

    public long inserir(Usuario usuario){
        SQLiteDatabase feelingsDb = dbHelper.getReadableDatabase();

        ContentValues values = new ContentValues();

        String emailColumn = FeelingsBoxDbHelper.COLUMN_EMAIL;
        String email = usuario.getEmail();

        String senhaColumn = FeelingsBoxDbHelper.COLUMN_SENHA;
        String senha = usuario.getSenha();

        String nickColumn = FeelingsBoxDbHelper.COLUMN_NICK;
        String nick = usuario.getNick();

        values.put(emailColumn, email);
        values.put(senhaColumn, senha);
        values.put(nickColumn, nick);

        String tabela = FeelingsBoxDbHelper.TABLE_USER;

        long id = feelingsDb.insert(tabela, null, values);

        feelingsDb.close();
        return id;
    }
    /**
     *
     * Cria um objeto Usuario a partir de um cursor
     *
     * @param cursor Cursor que vai percorrer as colunas da tabela
     * @return Retorna um Usuario
     */

    public Usuario criarUsuario(Cursor cursor){

        String idColumn = FeelingsBoxDbHelper.COLUMN_ID;
        int indexColumnID = cursor.getColumnIndex(idColumn);
        long id = cursor.getLong(indexColumnID);

        String emailColumn = FeelingsBoxDbHelper.COLUMN_EMAIL;
        int indexColumnEmail = cursor.getColumnIndex(emailColumn);
        String email = cursor.getString(indexColumnEmail);

        String senhaColumn = FeelingsBoxDbHelper.COLUMN_SENHA;
        int indexColumnSenha = cursor.getColumnIndex(senhaColumn);
        String senha = cursor.getString(indexColumnSenha);

        String nickColumn = FeelingsBoxDbHelper.COLUMN_NICK;
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