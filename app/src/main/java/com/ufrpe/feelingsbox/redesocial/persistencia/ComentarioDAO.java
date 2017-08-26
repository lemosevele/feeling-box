package com.ufrpe.feelingsbox.redesocial.persistencia;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.ufrpe.feelingsbox.infra.DataBase;
import com.ufrpe.feelingsbox.redesocial.dominio.Comentario;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe de persistência da classe Comentario
 */

public class ComentarioDAO {
    private DataBase dbHelper;
    private SQLiteDatabase feelingsDb;

    /**
     * Constructor
     * @param context
     * @see Comentario
     */

    public ComentarioDAO (Context context){
        dbHelper = new DataBase(context);
    }

    /**
     * Método que cria um objeto Comentario através de um Cursor
     * @param cursor Recebe Cursor que percorre as colunas da tabela
     * @return Retorna objeto Comentario criado
     */

    public Comentario criarComentario(Cursor cursor){
        String colunaId = DataBase.ID;
        int indexColunaId= cursor.getColumnIndex(colunaId);
        long id = cursor.getInt(indexColunaId);

        String colunaUserId = DataBase.COMENTARIO_USER_ID;
        int indexColunaUserId = cursor.getColumnIndex(colunaUserId);
        long idUsuario = cursor.getInt(indexColunaUserId);

        String colunaPostId = DataBase.COMENTARIO_POST_ID;
        int indexColunaPostId = cursor.getColumnIndex(colunaPostId);
        long idPost = cursor.getInt(indexColunaPostId);

        String colunaTexto = DataBase.COMENTARIO_TEXTO;
        int indexColunaTexto = cursor.getColumnIndex(colunaTexto);
        String texto = cursor.getString(indexColunaTexto);

        String colunaDataHora = DataBase.COMENTARIO_DATAHORA;
        int indexColunaDataHora = cursor.getColumnIndex(colunaDataHora);
        String datahora = cursor.getString(indexColunaDataHora);

        Comentario comentario = new Comentario();
        comentario.setId(id);
        comentario.setIdUsuario(idUsuario);
        comentario.setIdPost(idPost);
        comentario.setTexto(texto);
        comentario.setDataHora(datahora);

        return comentario;
    }

    /**
     * Insere Comentario na TABELA_COMENTARIO no banco de dados
     * @param comentario Recebe objeto Comentario a ser inserido
     * @return Retorna id do Comentario inserido
     */

    public long inserirComentario(Comentario comentario){
        feelingsDb = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        String colunaTexto =  DataBase.COMENTARIO_TEXTO;
        String texto = comentario.getTexto();
        values.put(colunaTexto, texto);

        String colunaIdUsuario = DataBase.COMENTARIO_USER_ID;
        long idUser = comentario.getIdUsuario();
        values.put(colunaIdUsuario, idUser);

        String colunaPostId = DataBase.COMENTARIO_POST_ID;
        long idPost = comentario.getIdPost();
        values.put(colunaPostId,idPost);

        String colunaStatus = DataBase.COMENTARIO_STATUS;
        values.put(colunaStatus,"visivel");

        String colunaDataHora = DataBase.POST_DATAHORA;
        String dataHora = comentario.getDataHora();
        values.put(colunaDataHora,dataHora);

        String tabela = DataBase.TABELA_COMENTARIO;

        long id = feelingsDb.insert(tabela, null, values);

        feelingsDb.close();
        return id;
    }

    /**
     * Método que retorna todos os comentários de um Post
     * @param idPost Recebe o id do Post a ter todos seus comentários pesquisados
     * @return Rertorna um Array  com comentários do Post
     */

    public List<Comentario> getComentariorioByPost(long idPost){
        feelingsDb = dbHelper.getReadableDatabase();
        ArrayList<Comentario> comentarioUser = new ArrayList<>();

        String query = "SELECT * FROM " + DataBase.TABELA_COMENTARIO +
                " WHERE " + DataBase.COMENTARIO_POST_ID + " LIKE ?";

        String idString = Long.toString(idPost);
        String[] argumentos = {idString};

        Cursor cursor = feelingsDb.rawQuery(query, argumentos);

        while (cursor.moveToNext()){
            Comentario comentario = criarComentario(cursor);
            comentarioUser.add(comentario);
        }
        cursor.close();
        feelingsDb.close();
        return comentarioUser;
    }

    /**
     * Método utilizado para contar a quantidade de Comentario de um Post
     * @param id Recebe id do Post a ter seus comentários contados
     * @return Retorna a quantidade de comentários do Post
     */

    public long  qtdQuantidadeComentario(long id) {
        feelingsDb = dbHelper.getReadableDatabase();
        String idString = Long.toString(id);
        String[] argumentos = {idString};

        Long resultado = DatabaseUtils.queryNumEntries(feelingsDb, DataBase.TABELA_COMENTARIO,
                DataBase.COMENTARIO_POST_ID+ "= ?", argumentos);
        feelingsDb.close();
        return resultado;
    }
}