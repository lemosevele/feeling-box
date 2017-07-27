package com.ufrpe.feelingsbox.infra;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class FormataData {

    //Recebe data no formato 01/10/2000 -> 20001001
    public static String americano(String data){
        StringBuilder novaData = new StringBuilder();
        novaData.append(data.substring(6));
        novaData.append(data.substring(3, 5));
        novaData.append(data.substring(0, 2));

        return novaData.toString();
    }

    public static String formatarDataHora(){
        SimpleDateFormat formatoDataHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        formatoDataHora.setTimeZone(TimeZone.getTimeZone("GMT-03:00"));
        Date dataFormatada = new Date();
        return formatoDataHora.format(dataFormatada);
    }

}
