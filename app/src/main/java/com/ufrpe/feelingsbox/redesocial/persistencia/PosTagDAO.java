package com.ufrpe.feelingsbox.redesocial.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ufrpe.feelingsbox.infra.DataBase;
import com.ufrpe.feelingsbox.redesocial.dominio.Post;

import java.util.ArrayList;

/**
 * Classe de persitência da relação entre tag e Post
 * @see PostDAO
 * @see Post
 */

public class PosTagDAO {
    private DataBase dbHelper;
    private SQLiteDatabase feelingsDb;
    private PostDAO postDAO;

    /**
     * Constructor
     * @param context
     */

    public PosTagDAO(Context context){
        this.postDAO = new PostDAO(context);
        this.dbHelper = new DataBase(context);
    }

    /**
     * Método que pesquisa todos Post que possuem uma determinada tag
     * @param tag Recebe a tag a ser pesquisada
     * @return Retorna um Array de Post que possuem a tag
     */

    public ArrayList<Post> getPostByTag(String tag) {
        feelingsDb = dbHelper.getReadableDatabase();
        ArrayList<Post> listaPost = new ArrayList<>();

        String query = "SELECT * FROM " + DataBase.TABELA_REL_TAG_POST +
            " WHERE " + DataBase.REL_TEXTO_TAG + " LIKE ?" +
                " ORDER BY " + DataBase.ID + " DESC";

        String[] argumentos = {tag};

        Cursor cursor = feelingsDb.rawQuery(query, argumentos);

        while (cursor.moveToNext()){
            String colunaTagPostId = DataBase.REL_ID_POST;
            int indexColunaTagPostId = cursor.getColumnIndex(colunaTagPostId);
            long idPost = cursor.getInt(indexColunaTagPostId);
            listaPost.add(postDAO.getPostId(idPost));
        }

        cursor.close();
        feelingsDb.close();

        return listaPost;
    }

    /**
     * Método que insere no banco de dados um relacionamento entre o Post e a tag na TABELA_REL_POS_TAG
     * @param tag Recebe Tag que está no Post feito pelo Usuario
     * @param idPost Recebe id do Post a ser inserido
     * @return Retorna o id que foi inserido na tabela de relacionamento
     */

    public long inserirTagIdPost(String tag, long idPost){
        feelingsDb = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        String colunaTag =  DataBase.REL_TEXTO_TAG;
        values.put(colunaTag, tag);

        String colunaIdPost = DataBase.REL_ID_POST;
        values.put(colunaIdPost, idPost);

        String tabela = DataBase.TABELA_REL_TAG_POST;
        long id = feelingsDb.insert(tabela, null, values);

        feelingsDb.close();

        return id;
    }

    /**
     * Método que utiliza banco para verificar se uma tag está relacionada ao Post (verifica se a tag existe no Post)
     * @param idPost Recebe id do Post a ser pesquisado
     * @param tag Recebe tag a ser pesquisada
     * @return Retorna verdadeiro se a tag está relacionada ao Post, se não, retorna falso
     */

    public boolean getRelacaoTagPost(long idPost, String tag){
        feelingsDb = dbHelper.getWritableDatabase();

        String query = "SELECT * FROM " + DataBase.TABELA_REL_TAG_POST +
                " WHERE " + DataBase.REL_ID_POST + " LIKE ? AND " +
                DataBase.REL_TEXTO_TAG + " LIKE ?";

        String idString = Long.toString(idPost);
        String[] argumentos = {idString, tag};

        Cursor cursor = feelingsDb.rawQuery(query, argumentos);

        return cursor.moveToNext();
    }
}