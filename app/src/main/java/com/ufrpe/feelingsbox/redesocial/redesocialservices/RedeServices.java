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
        post = new Post();
        post.setTexto(texto);
        post.setDataHora();
        post.setIdUsuario(sessao.getUsuarioLogado().getId());
        long idPost = postDAO.inserirPost(post);
        post.setId(idPost);

    }
}
