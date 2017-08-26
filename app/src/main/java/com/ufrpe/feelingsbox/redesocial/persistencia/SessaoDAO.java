package com.ufrpe.feelingsbox.redesocial.persistencia;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.content.Context;

import com.ufrpe.feelingsbox.infra.DataBase;
import com.ufrpe.feelingsbox.redesocial.dominio.Sessao;
import com.ufrpe.feelingsbox.usuario.dominio.Pessoa;
import com.ufrpe.feelingsbox.usuario.persistencia.PessoaDAO;

/**
 * Método de persistência da classe Sessao
 * @see SessaoDAO
 * @see PessoaDAO
 * @see Pessoa
 */

public class SessaoDAO {
    private DataBase dbHelper;
    private SQLiteDatabase feelingsDb;
    private PessoaDAO pessoaDAO;

    /**
     * Constructor
     * @param context
     */

    public SessaoDAO (Context context){
        dbHelper = new DataBase(context);
        pessoaDAO = new PessoaDAO(context);
    }

    /**
     * Método que recebe Sessao da Pessoa a logar e insere na TABELA_SESSAO do banco de dados
     * @param sessao
     */

    public void inserirIdPessoa(Sessao sessao){
        feelingsDb = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        String colunaIdPessoa = DataBase.ID_PESSOA;
        values.put(colunaIdPessoa, sessao.getPessoaLogada().getId());

        feelingsDb.insert(DataBase.TABELA_SESSAO, null, values);
    }

    /**
     * Método que na TABELA_SESSAO busca qual pessoa está logada
     * @return Retorna objeto Pessoa que está logada no sistema, se não, retorna null
     */

    public Pessoa getPessoaLogada(){
        feelingsDb = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM " + DataBase.TABELA_SESSAO;

        Cursor cursor = feelingsDb.rawQuery(query, null);

        Pessoa pessoa = null;

        if(cursor.moveToNext()){
            String colunaPessoaId = DataBase.ID_PESSOA;
            int indexColunaPessoatId = cursor.getColumnIndex(colunaPessoaId);
            long idPessoa = cursor.getInt(indexColunaPessoatId);
            pessoa = pessoaDAO.getPessoa(idPessoa);
        }
        cursor.close();
        feelingsDb.close();
        return pessoa;
    }

    /**
     * Encerra a Sessao da Pessoa no sistema
     */

    public void removerPessoa() {
        feelingsDb = dbHelper.getWritableDatabase();
        feelingsDb.delete(DataBase.TABELA_SESSAO, null, null);
        feelingsDb.close();
    }
}