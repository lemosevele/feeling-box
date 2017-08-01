package com.ufrpe.feelingsbox.redesocial.redesocialservices;

import android.content.Context;

import com.ufrpe.feelingsbox.infra.Sessao;
import com.ufrpe.feelingsbox.redesocial.dominio.Post;
import com.ufrpe.feelingsbox.redesocial.persistencia.PostDAO;


public class RedeServices {
    private Sessao sessao = Sessao.getInstancia();
    private Post post;
    private PostDAO postDAO;

    public RedeServices(Context context){
        postDAO = new PostDAO(context);
    }

    public void salvarPost(String texto){
        //Falta continuar
    }
}
