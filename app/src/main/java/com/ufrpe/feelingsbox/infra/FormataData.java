package com.ufrpe.feelingsbox.infra;

public class FormataData {

    //Recebe data no formato 01/10/2000 -> 20001001
    public static String americano(String data){
        StringBuilder novaData = new StringBuilder();
        novaData.append(data.substring(6));
        novaData.append(data.substring(3, 5));
        novaData.append(data.substring(0, 2));

        return novaData.toString();
    }

}
