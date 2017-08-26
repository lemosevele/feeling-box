package com.ufrpe.feelingsbox.redesocial.persistencia;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ufrpe.feelingsbox.infra.DataBase;
import com.ufrpe.feelingsbox.infra.FormataData;
import com.ufrpe.feelingsbox.redesocial.dominio.Post;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe de persistência dos Post sugeridos para o Usuario
 */

public class SugestaoDAO {
    private DataBase dbHelper;
    private SQLiteDatabase feelingsDb;
    private PostDAO postDAO;

    private static final int CONTADOR_INICIO = 0;
    private static final int CONTADOR_FIM = 4;

    /**
     * Construct
     * @param context
     */

    public SugestaoDAO(Context context){
        dbHelper = new DataBase(context);
        postDAO = new PostDAO(context);
    }

    /**
     * Método que pesquisa os Post mais comentados do dia
     * @return Retorna Array com Post mais comentados do dia
     */

    public List<Post> getPostsMaisComentadosHoje(){
        feelingsDb = dbHelper.getReadableDatabase();
        ArrayList<Post> listaPostsFiltrados = new ArrayList<>();
        String horaHoje = FormataData.formataDataHoraHoje();

        String query = "SELECT P." + DataBase.ID + ", P." + DataBase.POST_TEXTO + ", P." + DataBase.POST_DATAHORA +
                ", P." + DataBase.POST_VISIVEL + ", P." + DataBase.POST_STATUS + ", P." + DataBase.POST_USER_ID +
                ", qtde FROM " + DataBase.TABELA_POST + " AS P INNER JOIN (SELECT " + DataBase.COMENTARIO_POST_ID + ", "
                + "COUNT(" + DataBase.COMENTARIO_POST_ID + ") AS qtde FROM " + DataBase.TABELA_COMENTARIO + " GROUP BY " +
                DataBase.COMENTARIO_POST_ID +") AS C ON P." + DataBase.ID +" = C." + DataBase.COMENTARIO_POST_ID + " WHERE P." + DataBase.POST_DATAHORA +
                " = " + horaHoje + " OR " + DataBase.POST_DATAHORA + " > " + horaHoje + " ORDER BY qtde DESC";

        Cursor cursor = feelingsDb.rawQuery(query, null);
        int contador = CONTADOR_INICIO;
        while (cursor.moveToNext() && contador <= CONTADOR_FIM){
            Post post = postDAO.criarPost(cursor);
            listaPostsFiltrados.add(post);
            contador ++;
        }
        cursor.close();
        feelingsDb.close();
        return listaPostsFiltrados;
    }


}