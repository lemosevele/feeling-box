package com.ufrpe.feelingsbox.infra;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

//Classe retorna elementos de texto na Tela
public class GuiUtil {
    public static void erroToast(Context context, Exception e){
        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
    }

    public static void erroToast(Context context, String text){
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    public static void erroAlertDialog(Context context, Exception e, String titulo){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(context);
        dialogo.setTitle(titulo);
        dialogo.setMessage(e.getMessage());
        dialogo.setNeutralButton("Ok", null);
        dialogo.show();

    }
    public static void erroAlertDialog(Context context, String text, String titulo){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(context);
        dialogo.setTitle(titulo);
        dialogo.setMessage(text);
        dialogo.setNeutralButton("Ok", null);
        dialogo.show();

    }
    public static void erroAlertDialog(Context context, Exception e){
        GuiUtil.erroAlertDialog(context, e, "Erro");

    }
    public static void erroAlertDialog(Context context, String text){
        GuiUtil.erroAlertDialog(context, text, "Erro");

    }
}