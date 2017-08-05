package com.ufrpe.feelingsbox.infra;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class FormataData {
    private static final String DATA_POST_BANCO = "yyyyMMddHHmmss";
    private static final String DATA_POST_GUI = "dd/MM/yyyy HH:mm:ss";
    private static final String DATA_COMUM_GUI = "dd/MM/yyyy";
    private static final String DATA_COMUM_BANCO = "yyyyMMdd";

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
}