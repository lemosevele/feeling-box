package com.ufrpe.feelingsbox.redesocial.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ufrpe.feelingsbox.infra.DataBase;
import com.ufrpe.feelingsbox.infra.provider.SearchableProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe de persistência das tags inseridas pesquisadas pelo usuario
 */

public class TagDAO {
    private DataBase dbHelper;
    private SQLiteDatabase feelingsDb;
    private Context context;

    /**
     * Constructor
     * @param context
     */

    public TagDAO(Context context){
        dbHelper = new DataBase(context);
        this.context = context;
    }

    /**
     * Método que insere uma tag na TABELA_TAG no banco de dados
     * @param tag Recebe tag a ser inserida
     * @return Retorna id da tag inserida no banco
     */

    public long inserirTag(String tag){
        feelingsDb = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        String colunaTexto =  DataBase.TAG_TEXTO;
        values.put(colunaTexto, tag);

        String tabela = DataBase.TABELA_TAG;
        long id = feelingsDb.insert(tabela, null, values);

        SearchableProvider.salvarSugestao(context, tag);
        feelingsDb.close();

        return id;
    }

    /**
     * Método que busca uma tag no banco de dados através de um cursor
     * @param cursor Recebe Cursor que irá percorrer as colunas da tabela
     * @return Retorna tag pesquisada
     */

    public String criarTag(Cursor cursor){
        String colunaTexto = DataBase.TAG_TEXTO;
        int indexColunaTexto = cursor.getColumnIndex(colunaTexto);
        return cursor.getString(indexColunaTexto);
    }

    /**
     * Método que recebe uma tag e verifica se a tag existe no banco
     * @param texto
     * @return Retorna a tag se for encontrada, se não, retorna null
     */

    public String getTagTexto(String texto) {
        feelingsDb = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM " + DataBase.TABELA_TAG +
                " WHERE " + DataBase.TAG_TEXTO + " LIKE ?";

        String[] argumentos = {texto};

        Cursor cursor = feelingsDb.rawQuery(query, argumentos);

        String tag = null;

        if (cursor.moveToNext()) {

            tag = criarTag(cursor);
        }
        cursor.close();
        feelingsDb.close();
        return tag;
    }

    /**
     * Método que pesquisa tags na TABELA_TAGS no banco de dados
     * @return Retorna um Array com todas as tags encontradas
     */

    public List<String> getTagsByOrder(){
        feelingsDb = dbHelper.getReadableDatabase();
        List<String> listaTags = new ArrayList<>();

        String query = "SELECT * FROM " + DataBase.TABELA_TAG +
                " ORDER BY " + DataBase.TAG_TEXTO + " DESC";

        Cursor cursor = feelingsDb.rawQuery(query, null);

        while (cursor.moveToNext()){
            String tag = criarTag(cursor);
            listaTags.add(tag);
        }
        cursor.close();
        feelingsDb.close();
        return listaTags;
    }

    /**
     * Método que pesquisa tags na TABELA_TAG no banco de dados
     * @param texto Recebe uma tag
     * @return Retorna Array com todas as tags diferentes da tag recebida
     */

    public ArrayList<String> getListaTags(String texto){
        feelingsDb = dbHelper.getReadableDatabase();
        ArrayList<String> listaTags = new ArrayList<>();

        String query = "SELECT * FROM " + DataBase.TABELA_TAG +
                " WHERE " + DataBase.TAG_TEXTO + "  NOT LIKE ?";

        String[] argumentos = {texto};

        Cursor cursor = feelingsDb.rawQuery(query,argumentos);

        while (cursor.moveToNext()){
            String tag = criarTag(cursor);
            listaTags.add(tag);
        }
        cursor.close();
        feelingsDb.close();
        return listaTags;
    }
}