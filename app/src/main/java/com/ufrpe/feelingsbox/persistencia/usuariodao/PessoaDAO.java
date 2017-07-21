package com.ufrpe.feelingsbox.persistencia.usuariodao;

import com.ufrpe.feelingsbox.dominio.usuario.Pessoa;
import com.ufrpe.feelingsbox.persistencia.FeelingsBoxDbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class PessoaDAO {

    private FeelingsBoxDbHelper dbHelper;
    private UsuarioDAO usuarioDAO;

    public PessoaDAO(Context context){
        dbHelper = new FeelingsBoxDbHelper(context);
    }

    public Pessoa criarPessoa(Cursor cursor){

        String nomeColumn = FeelingsBoxDbHelper.PESSOA_NOME;
        int indexColumnNome = cursor.getColumnIndex(nomeColumn);
        String nome = cursor.getString(indexColumnNome);

        String sexoColumn = FeelingsBoxDbHelper.PESSOA_SEXO;
        int indexColumnSexo = cursor.getColumnIndex(sexoColumn);
        String sexo = cursor.getString(indexColumnSexo);

        String idColumn = FeelingsBoxDbHelper.ID;
        int indexColumnId = cursor.getColumnIndex(idColumn);
        int id  = cursor.getInt(indexColumnId);

        String dataNascColmun = FeelingsBoxDbHelper.PESSOA_DATANASC;
        int indexColumnDataNasc = cursor.getColumnIndex(dataNascColmun);
        String data = cursor.getString(indexColumnDataNasc);

        String idUsuarioColumn =  FeelingsBoxDbHelper.PESSOA_USER_ID;
        int indexColumnUserId = cursor.getColumnIndex(idUsuarioColumn);
        int idUsuario = cursor.getInt(indexColumnUserId);

        Pessoa pessoa = new Pessoa();
        pessoa.setId(id);
        pessoa.setSexo(sexo);
        pessoa.setNome(nome);
        pessoa.setDataNasc(data);
        pessoa.setIdUsuario(idUsuario);

        return pessoa;
    }

    public long inserir(Pessoa pessoa){
        SQLiteDatabase feelingsDb = dbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();

        String colunaNome =  FeelingsBoxDbHelper.PESSOA_NOME;
        String nome = pessoa.getNome();
        values.put(colunaNome, nome);

        String colunaSexo = FeelingsBoxDbHelper.PESSOA_SEXO;
        String sexo = pessoa.getSexo();
        values.put(colunaSexo, sexo);

        String colunaId = FeelingsBoxDbHelper.ID;
        long idPessoa = pessoa.getId();
        values.put(colunaId, idPessoa);

        String colunaUserId = FeelingsBoxDbHelper.PESSOA_USER_ID;
        long idUsuario = pessoa.getIdUsuario();
        values.put(colunaUserId, idUsuario);

        String colunaDataNasc = FeelingsBoxDbHelper.PESSOA_DATANASC;
        String dataNasc = pessoa.getDataNasc();
        values.put(colunaDataNasc, dataNasc);

        String tabela = FeelingsBoxDbHelper.TABELA_PESSOA;
        long id = feelingsDb.insert(tabela, null, values);

        feelingsDb.close();

        return id;
    }




}
