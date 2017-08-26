package com.ufrpe.feelingsbox.usuario.persistencia;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ufrpe.feelingsbox.infra.DataBase;
import com.ufrpe.feelingsbox.usuario.dominio.Usuario;

/**
 * Classe de persistência da classe Usuario
 * @see Usuario
 */

public class UsuarioDAO {

    private DataBase dbHelper;
    private SQLiteDatabase feelingsDb;

    /**
     *  Constructor
     * @param context
     */

    public UsuarioDAO(Context context) {

        dbHelper = new DataBase(context);
    }

    /**
     * Busca Usuario na TABELA_USUARIO no banco de dados
     * @param email Email do Usuario a ser buscado
     * @param senha Senha do Usuario a ser buscado
     * @return Se objeto existir retorna o objeto da Classe Usuario, se não, retorna null
     */

    public Usuario getUsuarioEmailSenha(String email, String senha) {
        feelingsDb = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM " + DataBase.TABELA_USUARIO +
                " WHERE " + DataBase.USUARIO_EMAIL + " LIKE ? AND " +
                DataBase.USUARIO_SENHA + " LIKE ?";

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

    /**
     * Busca usuário na TABELA_USUARIO através do email e senha
     * @param nick Nick do Usuario a ser buscado
     * @param senha Senha do Usuario a ser buscado
     * @return Se objeto existir, retorna o objeto Usuario, se não, retorna null
     */

    public Usuario getUsuarioNickSenha(String nick, String senha) {
        feelingsDb = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM " + DataBase.TABELA_USUARIO +
                " WHERE " + DataBase.USUARIO_NICK+ " LIKE ? AND " +
                DataBase.USUARIO_SENHA + " LIKE ?";

        String[] argumentos = {nick, senha};

        Cursor cursor = feelingsDb.rawQuery(query, argumentos);

        Usuario usuario = null;

        if (cursor.moveToNext()) {
            usuario = criarUsuario(cursor);
        }
        cursor.close();
        feelingsDb.close();

        return usuario;
    }

    /**
     * Busca Usuario na TABELA_USUARIO no banco de dados
     * @param nick Nick do Usuario a ser buscado
     * @return Rertorna objeto Usuario se for encontrado, se não, retorna null
     */
    public Usuario getUsuarioNick(String nick) {

        feelingsDb = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM " + DataBase.TABELA_USUARIO +
                " WHERE " + DataBase.USUARIO_NICK + " LIKE ?";

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

    /**
     * Busca Usuario na TABELA_USUARIO no banco de dados
     * @param id Id do Usuario a ser pesquisado
     * @return Retorna objeto Usuario caso seja encontrado, se não, retorna null
     */

    public Usuario getUsuarioId(long id){
        feelingsDb = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM " + DataBase.TABELA_USUARIO +
                " WHERE " + DataBase.ID + " LIKE ?";

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

    /**
     * Busca Usuario na TABELA_USUARIO do banco de dados
     * @param email Email do Usuario a ser buscado
     * @return Se encontrado, retorna objeto Usuario pesquisado, se não, retorna null
     */

    public Usuario getUsuarioEmail(String email) {
        feelingsDb = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM " + DataBase.TABELA_USUARIO +
                " WHERE " + DataBase.USUARIO_EMAIL + " LIKE ?";

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


    /**
     * Insere Usuario na TABELA_USUARIO do banco de dados
     * @param usuario Recebe Usuario a ser inserido no banco de dados
     * @return Retorna id do Usuario inserido
     */

    public long inserirUsuario(Usuario usuario){
        feelingsDb = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        String colunaEmail = DataBase.USUARIO_EMAIL;
        String email = usuario.getEmail();
        values.put(colunaEmail, email);

        String colunaSenha = DataBase.USUARIO_SENHA;
        String senha = usuario.getSenha();
        values.put(colunaSenha, senha);

        String colunaNick = DataBase.USUARIO_NICK;
        String nick = usuario.getNick();
        values.put(colunaNick, nick);

        String tabela = DataBase.TABELA_USUARIO;
        long id = feelingsDb.insert(tabela, null, values);

        feelingsDb.close();
        return id;
    }

    /**
     * Cria objeto Usuario através de um Cursor
     * @param cursor Recebe Cursor que percorre as colunas da TABELA_USUARIO
     * @return Rertorna objeto Usuario criado
     */

    public Usuario criarUsuario(Cursor cursor){

        String colunaId = DataBase.ID;
        int indexColunaId= cursor.getColumnIndex(colunaId);
        long id = cursor.getInt(indexColunaId);

        String colunaEmail = DataBase.USUARIO_EMAIL;
        int indexColunaEmail = cursor.getColumnIndex(colunaEmail);
        String email = cursor.getString(indexColunaEmail);

        String colunaSenha = DataBase.USUARIO_SENHA;
        int indexColunaSenha = cursor.getColumnIndex(colunaSenha);
        String senha = cursor.getString(indexColunaSenha);

        String colunaNick = DataBase.USUARIO_NICK;
        int indexColunaNick = cursor.getColumnIndex(colunaNick);
        String nick = cursor.getString(indexColunaNick);

        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setEmail(email);
        usuario.setSenha(senha);
        usuario.setNick(nick);

        return usuario;
    }

    /**
     * Atualiza dados do Usuario na TABELA_USUARIO no banco de dados
     * @param usuario Recebe Usuario a ser atualizado na tabela
     * @return Retorna o id do Usuario que teve seus dados atualizados
     */

    public long atualizarUsuario(Usuario usuario){
        feelingsDb = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        String colunaEmail = DataBase.USUARIO_EMAIL;
        String email = usuario.getEmail();
        values.put(colunaEmail, email);

        String colunaSenha = DataBase.USUARIO_SENHA;
        String senha = usuario.getSenha();
        values.put(colunaSenha, senha);

        String colunaNick = DataBase.USUARIO_NICK;
        String nick = usuario.getNick();
        values.put(colunaNick, nick);

        String tabela = DataBase.TABELA_USUARIO;

        String whereClause = DataBase.ID + " = ?";
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(usuario.getId());

        long id = feelingsDb.update(tabela, values, whereClause, parametros);

        feelingsDb.close();
        return id;
    }

}