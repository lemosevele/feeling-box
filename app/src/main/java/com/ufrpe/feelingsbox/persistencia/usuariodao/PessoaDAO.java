package com.ufrpe.feelingsbox.persistencia.usuariodao;

import com.ufrpe.feelingsbox.dominio.usuario.Pessoa;
import com.ufrpe.feelingsbox.dominio.usuario.Usuario;

import com.ufrpe.feelingsbox.persistencia.FeelingsBoxDbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class PessoaDAO {

    private FeelingsBoxDbHelper dbHelper; //OBJ DA CLASSE FeelingsSBoxDbHelper
    private UsuarioDAO usuarioDAO;
    private SQLiteDatabase feelingsDb;


    public PessoaDAO(Context context){

        dbHelper = new FeelingsBoxDbHelper(context);
        usuarioDAO = new UsuarioDAO(context);
    }

    // CURSOR PERCORRE AS COLUNAS DA TABELA E CRIA UM OBJETO PESSOA

    public Pessoa criarPessoa(Cursor cursor){

        String colunaNome = FeelingsBoxDbHelper.PESSOA_NOME;
        int indexColunaNome = cursor.getColumnIndex(colunaNome);
        String nome = cursor.getString(indexColunaNome);

        String colunaSexo = FeelingsBoxDbHelper.PESSOA_SEXO;
        int indexColunaSexo = cursor.getColumnIndex(colunaSexo);
        String sexo = cursor.getString(indexColunaSexo);

        String colunaId = FeelingsBoxDbHelper.ID;
        int indexColunaId = cursor.getColumnIndex(colunaId);
        long id  = cursor.getInt(indexColunaId);

        String colunaDataNasc = FeelingsBoxDbHelper.PESSOA_DATANASC;
        int indexColunaDataNasc = cursor.getColumnIndex(colunaDataNasc);
        String data = cursor.getString(indexColunaDataNasc);

        String colunaUsuarioId =  FeelingsBoxDbHelper.PESSOA_USER_ID;
        int indexColunaUsuarioId = cursor.getColumnIndex(colunaUsuarioId);
        int idUsuario = cursor.getInt(indexColunaUsuarioId);

        Usuario usuario = usuarioDAO.getUsuario(idUsuario);
        Pessoa pessoa = new Pessoa();

        pessoa.setId(id);
        pessoa.setSexo(sexo);
        pessoa.setNome(nome);
        pessoa.setDataNasc(data);
        pessoa.setIdUsuario(idUsuario);
        pessoa.setUsuario(usuario);

        return pessoa;
    }

    // INSERE OBJ PESSOA NA TABELA PESSOA, PEGA OS ATRIBUTOS DO OBJ E VAI INSERINDO

    public long inserirPessoa(Pessoa pessoa){
        feelingsDb = dbHelper.getReadableDatabase();
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

    public Pessoa getPessoa(Usuario usuario){
        feelingsDb = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM " + FeelingsBoxDbHelper.TABELA_PESSOA +
                " WHERE " + FeelingsBoxDbHelper.PESSOA_USER_ID + " LIKE ?";

        long idUsuario = usuario.getId();
        String idUsuarioString = Long.toString(idUsuario);
        String[] argumentos = {idUsuarioString};

        Cursor cursor = feelingsDb.rawQuery(query, argumentos);

        Pessoa pessoa = null;

        if (cursor.moveToNext()) {

            pessoa = criarPessoa(cursor);
        }
        cursor.close();
        feelingsDb.close();

        return pessoa;
    }

    public Pessoa getPessoa(long id){
        feelingsDb = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM " + FeelingsBoxDbHelper.TABELA_PESSOA +
                " WHERE " + FeelingsBoxDbHelper.ID + " LIKE ?";

        String idString = Long.toString(id);
        String[] argumentos = {idString};

        Cursor cursor = feelingsDb.rawQuery(query, argumentos);

        Pessoa pessoa = null;

        if (cursor.moveToNext()) {

            pessoa = criarPessoa(cursor);

        }
        cursor.close();
        feelingsDb.close();

        return pessoa;

    }
}
