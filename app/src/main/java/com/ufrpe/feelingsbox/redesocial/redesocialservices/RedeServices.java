package com.ufrpe.feelingsbox.redesocial.redesocialservices;

import android.content.Context;

import com.ufrpe.feelingsbox.infra.Sessao;
import com.ufrpe.feelingsbox.redesocial.dominio.Post;
import com.ufrpe.feelingsbox.redesocial.persistencia.PostDAO;
import com.ufrpe.feelingsbox.usuario.dominio.Pessoa;
import com.ufrpe.feelingsbox.usuario.dominio.Usuario;
import com.ufrpe.feelingsbox.usuario.persistencia.PessoaDAO;
import com.ufrpe.feelingsbox.usuario.persistencia.UsuarioDAO;

import java.util.ArrayList;


public class RedeServices {
    private Sessao sessao = Sessao.getInstancia();
    private Post post;
    private PostDAO postDAO;
    private Context context;
    private PessoaDAO pessoaDAO;
    private UsuarioDAO usuarioDAO;

    public RedeServices(Context context){
        this.context = context;
    }

    public void salvarPost(String texto){
        postDAO = new PostDAO(context);
        post = new Post();
        post.setTexto(texto);
        post.setDataHora();
        post.setIdUsuario(sessao.getUsuarioLogado().getId());
        long idPost = postDAO.inserirPost(post);
        post.setId(idPost);
    }

    public ArrayList<Post> exibirPosts(){
        return postDAO.getPostsByOrderId();
    }


}
