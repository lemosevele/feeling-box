package com.ufrpe.feelingsbox.infra;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Classe responsável por gerar uma máscara para os @see {@link EditText}.
 */

public abstract class Mask {
    public static String unmask(String s) {
        return s.replaceAll("[.]", "").replaceAll("[-]", "")
                .replaceAll("[/]", "").replaceAll("[(]", "")
                .replaceAll("[)]", "");
    }

    /**
     * Método estático que será vinculado ao @see {@link EditText} para a formatação do texto de
     * acordo com o padrão passado.
     * @param mask - String com o padrão que a máscara utilizará.
     * @param ediTxt - @see {@link EditText} que receberá a formatação da máscara.
     * @return - Retorna o texto formatado.
     */

    public static TextWatcher insert(final String mask, final EditText ediTxt) {
        return new TextWatcher() {
            boolean isUpdating;
            String old = "";
            public void onTextChanged(CharSequence s, int start, int before,int count) {
                String str = Mask.unmask(s.toString());
                String mascara = "";
                if (isUpdating) {
                    old = str;
                    isUpdating = false;
                    return;
                }
                int i = 0;
                for (char m : mask.toCharArray()) {
                    if (m != '#' && str.length() > old.length()) {
                        mascara += m;
                        continue;
                    }
                    try {
                        mascara += str.charAt(i);
                    } catch (Exception e) {
                        break;
                    }
                    i++;
                }
                isUpdating = true;
                ediTxt.setText(mascara);
                ediTxt.setSelection(mascara.length());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        };
    }
}