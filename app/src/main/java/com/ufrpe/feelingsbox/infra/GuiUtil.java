package com.ufrpe.feelingsbox.infra;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

import com.ufrpe.feelingsbox.R;

//Classe retorna elementos de texto na Tela
public class GuiUtil {

    public static void myToast(Context context, String text, int time){
        Toast.makeText(context, text, time).show();
    }
    public static void myToast(Context context, Exception e, int time){
        GuiUtil.myToast(context, e.getMessage(), time);
    }
    public static void myToast(Context context, String text){
        GuiUtil.myToast(context, text, Toast.LENGTH_LONG);
    }
    public static void myToast(Context context, Exception e){
        GuiUtil.myToast(context, e.getMessage(), Toast.LENGTH_LONG);
    }
    public static void myToastShort(Context context, String text){
        GuiUtil.myToast(context, text, Toast.LENGTH_SHORT);
    }
    public static void myToastShort(Context context, Exception e){
        GuiUtil.myToast(context, e.getMessage(), Toast.LENGTH_SHORT);
    }

    public static void myAlertDialog(Context context, String text, String titulo){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(context);
        dialogo.setTitle(titulo);
        dialogo.setMessage(text);
        dialogo.setNeutralButton(R.string.dialogo_botao_ok, null);
        dialogo.show();

    }
    public static void myAlertDialog(Context context, Exception e, String titulo){
        GuiUtil.myAlertDialog(context, e.getMessage(), titulo);

    }
    public static void myAlertDialog(Context context, Exception e){
        GuiUtil.myAlertDialog(context, e.getMessage(), context.getString(R.string.dialogo_titulo_erro));

    }
    public static void myAlertDialog(Context context, String text){
        GuiUtil.myAlertDialog(context, text, context.getString(R.string.dialogo_titulo_erro));

    }
}