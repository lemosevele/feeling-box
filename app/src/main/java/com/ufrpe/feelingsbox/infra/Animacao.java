package com.ufrpe.feelingsbox.infra;


import android.util.Log;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class Animacao {
    private final static int DURACAO = 1000;
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
