package com.ufrpe.feelingsbox.usuario.persistencia;

import com.ufrpe.feelingsbox.infra.DataBase;
import com.ufrpe.feelingsbox.usuario.dominio.Pessoa;
import com.ufrpe.feelingsbox.usuario.dominio.Usuario;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Classe de persistência da classe Pessoa
 * @see Pessoa
 */

public class PessoaDAO {

    private DataBase dbHelper;
    private SQLiteDatabase feelingsDb;

    /**
     * Constructor
     * @param context
     */

    public PessoaDAO(Context context){

        dbHelper = new DataBase(context);
    }

    /**
     * Cria um objeto Pessoa através de um Cursor
     * @param cursor Recebe cursor que irá percorrer as colunas da tabela
     * @return Retorna objeto Pessoa criado
     */

    public Pessoa criarPessoa(Cursor cursor){

        String colunaNome = DataBase.PESSOA_NOME;
        int indexColunaNome = cursor.getColumnIndex(colunaNome);
        String nome = cursor.getString(indexColunaNome);

        String colunaSexo = DataBase.PESSOA_SEXO;
        int indexColunaSexo = cursor.getColumnIndex(colunaSexo);
        String sexo = cursor.getString(indexColunaSexo);

        String colunaId = DataBase.ID;
        int indexColunaId = cursor.getColumnIndex(colunaId);
        long id  = cursor.getInt(indexColunaId);

        String colunaDataNasc = DataBase.PESSOA_DATANASC;
        int indexColunaDataNasc = cursor.getColumnIndex(colunaDataNasc);
        String data = cursor.getString(indexColunaDataNasc);

        String colunaUsuarioId =  DataBase.PESSOA_USER_ID;
        int indexColunaUsuarioId = cursor.getColumnIndex(colunaUsuarioId);
        long idUsuario = cursor.getInt(indexColunaUsuarioId);

        Pessoa pessoa = new Pessoa();

        pessoa.setId(id);
        pessoa.setSexo(sexo);
        pessoa.setNome(nome);
        pessoa.setDataNasc(data);
        pessoa.setIdUsuario(idUsuario);

        return pessoa;
    }

    /**
     * Insere objeto Pessoa na TABELA_PESSOA do banco de dados
     * @param pessoa Recebe Pessoa a ser inserida no banco de dados
     * @return Retorna id do objeto Pessoa inserido na tabela
     */

    public long inserirPessoa(Pessoa pessoa){
        feelingsDb = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        String colunaNome =  DataBase.PESSOA_NOME;
        String nome = pessoa.getNome();
        values.put(colunaNome, nome);

        String colunaSexo = DataBase.PESSOA_SEXO;
        String sexo = pessoa.getSexo();
        values.put(colunaSexo, sexo);

        String colunaUserId = DataBase.PESSOA_USER_ID;
        long idUsuario = pessoa.getIdUsuario();
        values.put(colunaUserId, idUsuario);

        String colunaDataNasc = DataBase.PESSOA_DATANASC;
        String dataNasc = pessoa.getDataNasc();
        values.put(colunaDataNasc, dataNasc);

        String tabela = DataBase.TABELA_PESSOA;

        long id = feelingsDb.insert(tabela, null, values);

        feelingsDb.close();

        return id;
    }

    /**
     * Busca um objeto Pessoa na TABELA_PESSOA no banco de dados
     * @param usuario Recebe objeto Usuario
     * @return Rertorna objeto Pessoa, se for encontrado, se não, retorna null
     */

    public Pessoa getPessoa(Usuario usuario){
        feelingsDb = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM " + DataBase.TABELA_PESSOA +
                " WHERE " + DataBase.PESSOA_USER_ID + " LIKE ?";

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

    /**
     * Atualiza os dados do objeto Pessoa na TABELA_PESSOA
     * @param pessoa Recebe objeto Pessoa com seus dados atualizados
     * @return Retorna o id do objeto pessoa que foi atualizado
     */

    public long atualizarPessoa(Pessoa pessoa){
        feelingsDb = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        String colunaNome =  DataBase.PESSOA_NOME;
        String nome = pessoa.getNome();
        values.put(colunaNome, nome);

        String colunaSexo = DataBase.PESSOA_SEXO;
        String sexo = pessoa.getSexo();
        values.put(colunaSexo, sexo);

        String colunaDataNasc = DataBase.PESSOA_DATANASC;
        String dataNasc = pessoa.getDataNasc();
        values.put(colunaDataNasc, dataNasc);

        String tabela = DataBase.TABELA_PESSOA;

        String whereClause = DataBase.ID + " = ?";
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(pessoa.getId());

        long id = feelingsDb.update(tabela, values, whereClause , parametros);

        feelingsDb.close();

        return id;
    }

    /**
     * Busca Pessoa na TABELA_PESSOA do banco de dados
     * @param id Recebe id do objeto Pessoa a ser buscado
     * @return Se encontrado, retorna objeto Pessoa, se não, retorna null
     */

    public Pessoa getPessoa(long id){
        feelingsDb = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM " + DataBase.TABELA_PESSOA +
                " WHERE " + DataBase.ID + " LIKE ?";

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