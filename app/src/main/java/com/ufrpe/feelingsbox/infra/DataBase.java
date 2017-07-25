package com.ufrpe.feelingsbox.infra;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper; // Cria banco de dados
//Classe responsável por criar o banco de dados

public class DataBase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "dbfeelingsbox";

    // TABELA USUÁRIO
    public static final String TABELA_USUARIO = "usuario";
    public static final String ID = "_id";
    public static final String USUARIO_EMAIL = "email";
    public static final String USUARIO_SENHA = "senha";
    public static final String USUARIO_NICK = "nick";


    //TABELA PESSOA
    public static final String TABELA_PESSOA = "pessoa";
    public static final String PESSOA_USER_ID = "usuario_id";
    public static final String PESSOA_NOME = "nome";
    public static final String PESSOA_SEXO = "sexo";
    public static final String PESSOA_DATANASC = "data_nasc";


    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Criação da tabela
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABELA_USUARIO + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                USUARIO_EMAIL + " TEXT NOT NULL, " +
                USUARIO_SENHA + " TEXT NOT NULL, " +
                USUARIO_NICK + " TEXT NOT NULL);");

        db.execSQL("CREATE TABLE " + TABELA_PESSOA + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PESSOA_NOME + " TEXT NOT NULL, " +
                PESSOA_DATANASC + " TEXT NOT NULL, " +
                PESSOA_SEXO + " TEXT NOT NULL, " +
                PESSOA_USER_ID + " INTEGER);");
    }

    //Atualização da tabela
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        String query = "DROP TABLE IF EXISTS " + TABELA_USUARIO;
        db.execSQL(query);
        this.onCreate(db);
    }

    public static String getColumnEmail(){
        return USUARIO_EMAIL;
    }

    public static String getColumnNick(){
        return USUARIO_NICK;
    }

    public static String getColumnNome(){
        return PESSOA_NOME;
    }

    public static String getColumnSenha(){
        return USUARIO_SENHA;
    }
}
