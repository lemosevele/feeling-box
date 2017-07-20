package com.ufrpe.feelingsbox.persistencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FeelingsBoxDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "feelingsbox.db";

    public static final String TABLE_USER = "usuario";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_SENHA = "senha";
    public static final String COLUMN_NICK = "nick";

    public static final String TABLE_PESSOA = "pessoa";
    public static final String COLUMN_PESSOA_ID = "pessoa_id";
    public static final String COLUMN_NOME = "nome";
    public static final String COLUMN_SEXO = "sexo";
    public static final String COLUMN_DATANASC = "data_nasc";

    public FeelingsBoxDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("CREATE TABLE" + TABLE_USER + " (" +
                                                COLUMN_ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                COLUMN_EMAIL + "TEXT NOT NULL, " +
                                                COLUMN_SENHA + "TEXT NOT NULL, " +
                                                COLUMN_NICK + "TEXT NOT NULL);");

        sqLiteDatabase.execSQL("CREATE TABLE" + TABLE_PESSOA + "(" +
                                                COLUMN_PESSOA_ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                COLUMN_NOME + "TEXT NOT NULL, " +
                                                COLUMN_DATANASC + "TEXT NOT NULL, " +
                                                COLUMN_SEXO + "TEXT NOT NULL);");

    }

    public void onUpgrade(SQLiteDatabase feelingsboxdb, int oldVersion, int newVersion){
        String query = "DROP TABLE IF EXISTS " + TABLE_USER;
        feelingsboxdb.execSQL(query);
        this.onCreate(feelingsboxdb);
    }

    public static String getColumnEmail(){
        return COLUMN_EMAIL;
    }

    public static String getColumnNick(){
        return COLUMN_NICK;
    }

    public static String getColumnNome(){
        return COLUMN_NOME;
    }

    public static String getColumnSenha(){
        return COLUMN_SENHA;
    }
}
