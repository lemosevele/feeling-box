package com.ufrpe.feelingsbox.usuario.persistencia;

import com.ufrpe.feelingsbox.infra.DataBase;
import com.ufrpe.feelingsbox.usuario.dominio.Pessoa;
import com.ufrpe.feelingsbox.usuario.dominio.Usuario;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


public class PessoaDAO {

    private DataBase dbHelper;
    private UsuarioDAO usuarioDAO;
    private SQLiteDatabase feelingsDb;


    public PessoaDAO(Context context){

        dbHelper = new DataBase(context);
        usuarioDAO = new UsuarioDAO(context);
    }

    // Cursor percorre as colunas da TABELA_PESSOA e cria Objeto Pessoa

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

    // Insere o Obj pessoa na TABELA_PESSOA, pegando os atributos e inserindo

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

    // Busca um objeto Pessoa na TABELA_PESSOA passando um objeto Usuario

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

    // Busca obj Pessoa na TABELA_PESSOA passando o Id

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
    //Atualiza o Pessoa na Tabela Pessoa
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
    //Deleta o Pessoa na Tabela Pessoa
    public long deletarPessoa(Pessoa pessoa){
        String tabela = DataBase.TABELA_PESSOA;
        String whereClause = DataBase.ID + " = ?";
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(pessoa.getId());

        long id = feelingsDb.delete(tabela, whereClause , parametros);

        feelingsDb.close();

        return id;
    }
    public List<Pessoa> buscarTodosPessoa(){
        feelingsDb = dbHelper.getReadableDatabase();

        List<Pessoa>  listaPessoas = new ArrayList<Pessoa>();

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(DataBase.ID + ", ");
        sql.append(DataBase.PESSOA_NOME + ", ");
        sql.append(DataBase.PESSOA_DATANASC + ", ");
        sql.append(DataBase.PESSOA_SEXO + ", ");
        sql.append(DataBase.PESSOA_USER_ID + " ");
        sql.append("  FROM ");
        sql.append(DataBase.TABELA_PESSOA + " ");

        Cursor resultado = feelingsDb.rawQuery(sql.toString(), null);

        if(resultado.getCount() > 0){
            resultado.moveToFirst();

            do{
                Pessoa pessoa = new Pessoa();

                pessoa = criarPessoa(resultado);

                listaPessoas.add(pessoa);

            }while(resultado.moveToNext());
        }

        return listaPessoas;
    }
}