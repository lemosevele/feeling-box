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

    //TABELA POST
    public static final String TABELA_POST = "post";
    public static final String POST_USER_ID = "usuario_id";
    public static final String POST_TEXTO = "texto";
    public static final String POST_DATAHORA = "datahora";
    public static final String POST_STATUS = "status"; //Post excluido ou não
    public static final String POST_VISIVEL = "visibilidade"; //Anônimo ou não

    //TABELA TAG
    public static final String TABELA_TAG = "tag";
    public static final String TAG_TEXTO = "texto";

    //TABELA DE RELACIONAMENTO ENTRE TAG E POST
    public static final String TABELA_REL_TAG_POST = "relacionamento";
    public static final String REL_ID_TAG = "tag_id";
    public static final String REL_ID_POST = "post_id";

    //TABELA DE SESSAO DO USUARIO
    // A Pessoa que estiver nessa tabela está logada, quando deslogar tirar da tabela
    // Essa tabela só tem o ID da pessoa logada
    public static final String TABELA_SESSAO = "sessao";
    public static final String ID_PESSOA = "pessoa_id";

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

        db.execSQL("CREATE TABLE " + TABELA_POST + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                POST_TEXTO + " TEXT NOT NULL, " +
                POST_DATAHORA + " TEXT NOT NULL, " +
                POST_VISIVEL + " TEXT NOT NULL, " +
                POST_STATUS + " TEXT NOT NULL, " +
                POST_USER_ID + " INTEGER);");

        db.execSQL("CREATE TABLE " + TABELA_TAG + " (" +
                TAG_TEXTO + " TEXT NOT NULL, " +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT);");

        db.execSQL("CREATE TABLE " + TABELA_REL_TAG_POST + " (" +
                REL_ID_TAG + " INTEGER, " +
                REL_ID_POST + " INTEGER);");

        db.execSQL("CREATE TABLE " + TABELA_SESSAO+ " (" +
                ID_PESSOA + " INTEGER);");
    }

    //Atualização da tabela
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        String query = "DROP TABLE IF EXISTS " + TABELA_USUARIO;
        db.execSQL(query);
        this.onCreate(db);
    }
}
