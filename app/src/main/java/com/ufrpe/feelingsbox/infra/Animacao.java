package com.ufrpe.feelingsbox.infra;


import android.util.Log;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

/**
 * Classe responsável por concentrar os padrões de animação usadas na aplicação.
 */

public class Animacao {
    private final static int DURACAO = 1000;

    /**
     * Método anima o elemento passado como paramentro, animação Zoom In.
     * @param view - @see {@link View} que será animada.
     */

    public static void animacaoZoomIn(View view){
        //Animação
        try {
            YoYo.with(Techniques.ZoomIn)
                    .duration(DURACAO)
                    .repeat(0)
                    .playOn(view);
        }catch (Exception e){
            Log.d("animacaoCard();", "Erro ao animar o User Card");
        }
    }
}
