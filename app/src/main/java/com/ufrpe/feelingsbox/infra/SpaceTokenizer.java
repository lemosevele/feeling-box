package com.ufrpe.feelingsbox.infra;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.widget.MultiAutoCompleteTextView;

/**
 * Classe responsável pela interação do @see {@link MultiAutoCompleteTextView} com lista de sugestões.
 */

public class SpaceTokenizer implements MultiAutoCompleteTextView.Tokenizer {

    /**
     * @see {@Link android.widget.MultiAutoCompleteTextView.Tokenizer}.
     * @param text - Texto.
     * @param cursor - Posição do Cursor.
     * @return - Retorna o índice de inicio do Token.
     */

    public int findTokenStart(CharSequence text, int cursor) {
        int i = cursor;

        while (i > 0 && text.charAt(i - 1) != ' ') {
            i--;
        }
        while (i < cursor && text.charAt(i) == ' ') {
            i++;
        }

        return i;
    }

    /**
     * @see {@Link android.widget.MultiAutoCompleteTextView.Tokenizer}.
     * @param text - Texto.
     * @param cursor - Posição do Cursor.
     * @return - Retorna o índice de fim do Token.
     */

    public int findTokenEnd(CharSequence text, int cursor) {
        int i = cursor;
        int len = text.length();

        while (i < len) {
            if (text.charAt(i) == ' ') {
                return i;
            } else {
                i++;
            }
        }

        return len;
    }

    /**
     * @see {@Link android.widget.MultiAutoCompleteTextView.Tokenizer}.
     * @param text - Texto.
     * @return - Retorna texto modificado.
     */

    public CharSequence terminateToken(CharSequence text) {
        int i = text.length();

        while (i > 0 && text.charAt(i - 1) == ' ') {
            i--;
        }

        if (i > 0 && text.charAt(i - 1) == ' ') {
            return text;
        } else {
            if (text instanceof Spanned) {
                SpannableString sp = new SpannableString(text + " ");
                TextUtils.copySpansFrom((Spanned) text, 0, text.length(),
                        Object.class, sp, 0);
                return sp;
            } else {
                return text + " ";
            }
        }
    }
}