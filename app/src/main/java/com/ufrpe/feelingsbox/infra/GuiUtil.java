package com.ufrpe.feelingsbox.infra;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

import com.ufrpe.feelingsbox.R;

/**
 * Classe responsável pela exibição das mensagens mostradas pela aplicação.
 */

public class GuiUtil {

    /**
     * Método exibe uma mensagem em formato de @see {@link Toast}.
     * @param context - Contexto da aplicação.
     * @param text - Texto a ser exibido.
     * @param time - Duração da exibição.
     */

    public static void myToast(Context context, String text, int time){
        Toast.makeText(context, text, time).show();
    }

    /**
     * Método exibe uma mensagem em formato de @see {@link Toast}.
     * @param context - Contexto da aplicação.
     * @param e - Exceção com o texto a ser exibido.
     * @param time - Duração da exibição.
     */

    public static void myToast(Context context, Exception e, int time){
        GuiUtil.myToast(context, e.getMessage(), time);
    }

    /**
     * Método exibe uma mensagem em formato de @see {@link Toast}.
     * @param context - Contexto da aplicação.
     * @param text - Texto a ser exibido.
     */

    public static void myToast(Context context, String text){
        GuiUtil.myToast(context, text, Toast.LENGTH_LONG);
    }

    /**
     * Método exibe uma mensagem em formato de @see {@link Toast}.
     * @param context - Contexto da aplicação.
     * @param e - Exceção com o texto a ser exibido.
     */

    public static void myToast(Context context, Exception e){
        GuiUtil.myToast(context, e.getMessage(), Toast.LENGTH_LONG);
    }

    /**
     * Método exibe uma mensagem em formato de @see {@link Toast} com o tempo menor que o do @see myToast.
     * @param context - Contexto da aplicação.
     * @param text - Texto a ser exibido.
     */

    public static void myToastShort(Context context, String text){
        GuiUtil.myToast(context, text, Toast.LENGTH_SHORT);
    }

    /**
     * Método exibe uma mensagem em formato de @see {@link Toast} com o tempo menor que o do @see myToast.
     * @param context - Contexto da aplicação.
     * @param e - Exceção com o texto a ser exibido.
     */

    public static void myToastShort(Context context, Exception e){
        GuiUtil.myToast(context, e.getMessage(), Toast.LENGTH_SHORT);
    }

    /**
     * Método exibe uma mensagem em formato de @see {@link AlertDialog}.
     * @param context - Contexto da aplicação.
     * @param text - Texto a ser exibido.
     * @param titulo - Título a ser exibido.
     */

    public static void myAlertDialog(Context context, String text, String titulo){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(context);
        dialogo.setTitle(titulo);
        dialogo.setMessage(text);
        dialogo.setNeutralButton(R.string.dialogo_botao_ok, null);
        dialogo.show();
    }

    /**
     * Método exibe uma mensagem em formato de @see {@link AlertDialog}.
     * @param context - Contexto da aplicação.
     * @param e - Exceção com o texto a ser exibido.
     * @param titulo - Título a ser exibido.
     */

    public static void myAlertDialog(Context context, Exception e, String titulo){
        GuiUtil.myAlertDialog(context, e.getMessage(), titulo);
    }

    /**
     * Método exibe uma mensagem em formato de @see {@link AlertDialog} com título de Erro como padrão.
     * @param context - Contexto da aplicação.
     * @param e - Exceção com o texto a ser exibido.
     */

    public static void myAlertDialog(Context context, Exception e){
        GuiUtil.myAlertDialog(context, e.getMessage(), context.getString(R.string.dialogo_titulo_erro));
    }

    /**
     * Método exibe uma mensagem em formato de @see {@link AlertDialog} com título de Erro como padrão.
     * @param context - Contexto da aplicação.
     * @param text - Texto a ser exibido.
     */

    public static void myAlertDialog(Context context, String text){
        GuiUtil.myAlertDialog(context, text, context.getString(R.string.dialogo_titulo_erro));
    }
}