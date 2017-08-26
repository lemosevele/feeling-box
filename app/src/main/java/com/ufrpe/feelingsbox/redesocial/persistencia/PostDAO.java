package com.ufrpe.feelingsbox.redesocial.persistencia;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import com.ufrpe.feelingsbox.infra.DataBase;
import com.ufrpe.feelingsbox.redesocial.dominio.Post;


public class PostDAO {
    private DataBase dbHelper;
    private SQLiteDatabase feelingsDb;
    private final int ID_INDEX = 0;
    private final int USER_ID_INDEX = 1;
    private final int TEXTO_INDEX = 2;
    private final int DATAHORA_INDEX = 3;

    public PostDAO(Context context){
        dbHelper = new DataBase(context);
    }

    // Cria objeto post passando o cursor, retorna objeto post criado

    public Post criarPost(Cursor cursor){

        String colunaId = DataBase.ID;
        int indexColunaId= cursor.getColumnIndex(colunaId);
        long id = cursor.getInt(indexColunaId);

        String colunaUserId = DataBase.POST_USER_ID;
        int indexColunaUserId = cursor.getColumnIndex(colunaUserId);
        long idUsuario = cursor.getInt(indexColunaUserId);

        String colunaTexto = DataBase.POST_TEXTO;
        int indexColunaTexto = cursor.getColumnIndex(colunaTexto);
        String texto = cursor.getString(indexColunaTexto);

        String colunaDataHora = DataBase.POST_DATAHORA;
        int indexColunaDataHora = cursor.getColumnIndex(colunaDataHora);
        String datahora = cursor.getString(indexColunaDataHora);

        Post post = new Post();
        post.setId(id);
        post.setTexto(texto);
        post.setIdUsuario(idUsuario);
        post.setDataHora(datahora);

        return post;
    }

    public Post criarPostInnerJoin(Cursor cursor){

        long id = cursor.getInt(ID_INDEX);

        long idUsuario = cursor.getInt(USER_ID_INDEX);

        String texto = cursor.getString(TEXTO_INDEX);

        String datahora = cursor.getString(DATAHORA_INDEX);

        Post post = new Post();
        post.setId(id);
        post.setTexto(texto);
        post.setIdUsuario(idUsuario);
        post.setDataHora(datahora);

        return post;
    }

    //Insere objeto Post na TABELA_POST, retorna id do post

    public long inserirPost(Post post){
        feelingsDb = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        String colunaTexto =  DataBase.POST_TEXTO;
        String texto = post.getTexto();
        values.put(colunaTexto, texto);

        String colunaIdUsuario = DataBase.POST_USER_ID;
        long idUser = post.getIdUsuario();
        values.put(colunaIdUsuario, idUser);

        String colunaDataHora = DataBase.POST_DATAHORA;
        String dataHora = post.getDataHora();
        values.put(colunaDataHora,dataHora);

        String colunaVisibilidade = DataBase.POST_VISIVEL;
        String visibilidade = "publico";
        values.put(colunaVisibilidade,visibilidade);

        String colunaStatus = DataBase.POST_STATUS;
        values.put(colunaStatus,"visivel"); //Inicialmente o post não está excluído, está visível

        String tabela = DataBase.TABELA_POST;

        long id = feelingsDb.insert(tabela, null, values);

        feelingsDb.close();

        return id;
    }

    //Retorna lista de posts, ordenados pelo id

    public List<Post> getPostsByOrderId (){
        feelingsDb = dbHelper.getReadableDatabase();
        List<Post> listaPosts = new ArrayList<>();

        String query = "SELECT * FROM " + DataBase.TABELA_POST +
                " ORDER BY " + DataBase.POST_DATAHORA + " DESC";

        Cursor cursor = feelingsDb.rawQuery(query, null);

        while (cursor.moveToNext()){
            Post post = criarPost(cursor);
            listaPosts.add(post);
        }
        feelingsDb.close();
        return listaPosts;
    }

    // Passa o id do post e retorna o post

    public Post getPostId(long id){
        feelingsDb = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM " + DataBase.TABELA_POST +
                " WHERE " + DataBase.ID + " LIKE ?";

        String idString = Long.toString(id);
        String[] argumentos = {idString};

        Cursor cursor = feelingsDb.rawQuery(query, argumentos);

        Post post = null;

        if(cursor.moveToNext()){
            post = criarPost(cursor);
        }

        cursor.close();
        feelingsDb.close();
        return post;
    }

    public ArrayList<Long> getListaPostId() {
        feelingsDb = dbHelper.getReadableDatabase();
        ArrayList<Long> listaIdPost = new ArrayList<>();

        String query = "SELECT " + DataBase.ID + " FROM " + DataBase.TABELA_POST;

        Cursor cursor = feelingsDb.rawQuery(query, null);

        int colunaIndexId = cursor.getColumnIndex(DataBase.ID);

        while (cursor.moveToNext()) {
            listaIdPost.add((long) cursor.getInt(colunaIndexId));
        }


        cursor.close();
        feelingsDb.close();
        return listaIdPost;
    }

    public List<Post> getPostByUser(long id){
        feelingsDb = dbHelper.getReadableDatabase();
        ArrayList<Post> postUser = new ArrayList<>();

        String query = "SELECT * FROM " + DataBase.TABELA_POST +
                " WHERE " + DataBase.POST_USER_ID + " LIKE ?" +
                " ORDER BY " + DataBase.POST_DATAHORA + " DESC";

        String idString = Long.toString(id);
        String[] argumentos = {idString};

        Cursor cursor = feelingsDb.rawQuery(query, argumentos);

        while (cursor.moveToNext()){
            Post post = criarPost(cursor);
            postUser.add(post);
        }
        feelingsDb.close();
        return postUser;
    }

    // Passa o id do Usuário e retorna os posts de quem ele segue
    public List<Post> getPostFavoritos(long id){
        feelingsDb = dbHelper.getReadableDatabase();
        ArrayList<Post> postsFavoritos = new ArrayList<>();
        String idString = Long.toString(id);

        String query = "SELECT P." + DataBase.ID + ", P." + DataBase.POST_USER_ID + ", P." + DataBase.POST_TEXTO + ", P." +
                DataBase.POST_DATAHORA + ", P." + DataBase.POST_VISIVEL + ", P." + DataBase.POST_STATUS +
                " FROM " + DataBase.TABELA_POST + " AS P INNER JOIN (SELECT " + DataBase.SEGUIDOR_ID +
                ", " + DataBase.SEGUIDO_ID + " FROM " + DataBase.TABELA_REL_SEGUIDORES +
                " WHERE " + DataBase.SEGUIDOR_ID + " = " + idString +") AS S ON P." + DataBase.POST_USER_ID  +
                " = S." + DataBase.SEGUIDO_ID + " ORDER BY P." + DataBase.POST_DATAHORA + " DESC";

        Cursor cursor = feelingsDb.rawQuery(query, null);
        while (cursor.moveToNext()){
            Post post = criarPostInnerJoin(cursor);
            postsFavoritos.add(post);
        }
        feelingsDb.close();
        return postsFavoritos;

    }
}