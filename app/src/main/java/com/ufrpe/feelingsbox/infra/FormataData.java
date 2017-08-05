package com.ufrpe.feelingsbox.infra;


import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class FormataData {
    private static final String DATA_POST_BANCO  = "yyyyMMddHHmmss";
    private static final String DATA_POST_GUI    = "dd/MM/yyyy HH:mm:ss";
    private static final String DATA_COMUM_GUI   = "dd/MM/yyyy";
    private static final String DATA_COMUM_BANCO = "yyyyMMdd";

    private static final int SEGUNDO = 1000;
    private static final int MINUTO = 60000;
    private static final int HORA = 3600000;
    private static final int DIA = 86400000;
    private static final int SEMANA = 604800000;
    private static final long MES = 2592000000l;
    private static final long ANO = 31536000000l;


    //Recebe data no formato 01/10/2000 -> 20001001
    public static String americano(String data){
        StringBuilder novaData = new StringBuilder();
        novaData.append(data.substring(6));
        novaData.append(data.substring(3, 5));
        novaData.append(data.substring(0, 2));

        return novaData.toString();
    }

    public static String formatarDataHora(){
        SimpleDateFormat formatoDataHora = new SimpleDateFormat(DATA_POST_GUI);
        formatoDataHora.setTimeZone(TimeZone.getTimeZone("GMT-03:00"));
        Date dataFormatada = new Date();
        return formatoDataHora.format(dataFormatada);
    }

    //Recebe uma string no formato que esta no banco e Retorna no formato para exibicao.
    public static String formatarDataHoraPostDataBaseParaExibicao (String stringData){
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(DATA_POST_BANCO);
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(DATA_POST_GUI);
        simpleDateFormat1.setLenient(false);

        try{
            Date date = simpleDateFormat1.parse(stringData);
            return simpleDateFormat2.format(date);
        }catch (Exception e){
            return e.getMessage();
        }

    }

    //Retorna uma string data atual no formato para guardar no banco
    public static String formatarDataHoraAtualParaPostDataBase(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATA_POST_BANCO);
        return simpleDateFormat.format(date);
    }

    public static boolean dataExiste(String data){
        SimpleDateFormat dataFormatada = new SimpleDateFormat (DATA_COMUM_GUI);
        dataFormatada.setLenient (false);

        //Testa no formato dd/MM/yyyy
        try {
            dataFormatada.parse(data);
            return true;
        } catch (Exception e) {
        }

        dataFormatada = new SimpleDateFormat (DATA_COMUM_BANCO);
        dataFormatada.setLenient (false);

        //Testa no formato yyyyMMdd
        try {
            dataFormatada.parse(data);
            return true;
        } catch (Exception e) {
        }

        return false;

    }

    public static boolean dataMenorOuIgualQueAtual(String data){
        SimpleDateFormat dataFormatada = new SimpleDateFormat (DATA_COMUM_GUI);
        dataFormatada.setLenient (false);
        //Testa no formato dd/MM/yyyy
        try {
            Date dataAtual = new Date();
            Date dataCliente = dataFormatada.parse(data);

            if(dataAtual.compareTo(dataCliente) >= 0){
                return true;
            }
        } catch (Exception e) {
        }

        dataFormatada = new SimpleDateFormat (DATA_COMUM_BANCO);
        dataFormatada.setLenient (false);
        //Testa no formato yyyyMMdd
        try {
            Date dataAtual = new Date();
            Date dataCliente = dataFormatada.parse(data);

            if(dataAtual.compareTo(dataCliente) >= 0){
                return true;
            }
        } catch (Exception e) {
        }

        return false;
    }
    public static String tempoParaMostrarEmPost(String data){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat (DATA_POST_BANCO);
        Date datainicio = new Date();
        Date datafim = new Date();
        try {
            datainicio = simpleDateFormat.parse(data);
        } catch (ParseException e) {
            Log.d("tempoParaMostrarEmPost", e.getMessage());
        }

        long diferenca = datafim.getTime() - datainicio.getTime();
        String tempo;

        if(diferenca < MINUTO){
            tempo = Long.toString(diferenca/ SEGUNDO);
            tempo = "Há " + tempo + (tempo.equals("1") ? " segundo":" segundos");
        } else if(diferenca < HORA){
            tempo = Long.toString(diferenca/ MINUTO);
            tempo = "Há " + tempo + (tempo.equals("1") ? " minuto":" minutos");
        } else if(diferenca < DIA){
            tempo = Long.toString(diferenca/ HORA);
            tempo = "Há " + tempo + (tempo.equals("1") ? " hora":" horas");
        } else if(diferenca < SEMANA){
            tempo = Long.toString(diferenca/ DIA);
            tempo = "Há " + tempo + (tempo.equals("1") ? " dia":" dias");
        } else if(diferenca < MES){
            tempo = Long.toString(diferenca/ SEMANA);
            tempo = "Há " + tempo + (tempo.equals("1") ? " semana":" semanas");
        } else if(diferenca < ANO){
            tempo = Long.toString(diferenca/ MES);
            tempo = "Há " + tempo + (tempo.equals("1") ? " mês":" meses");
        } else {
            simpleDateFormat = new SimpleDateFormat(DATA_POST_GUI);
            tempo = "em " + simpleDateFormat.format(datainicio);

        }
        return tempo;
    }
}