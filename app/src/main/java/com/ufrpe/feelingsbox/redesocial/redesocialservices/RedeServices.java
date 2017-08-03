package com.ufrpe.feelingsbox.redesocial.redesocialservices;

import android.content.Context;

import com.ufrpe.feelingsbox.infra.Sessao;
import com.ufrpe.feelingsbox.redesocial.dominio.Post;
import com.ufrpe.feelingsbox.redesocial.dominio.Tag;
import com.ufrpe.feelingsbox.redesocial.persistencia.PostDAO;
import com.ufrpe.feelingsbox.redesocial.persistencia.TagDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RedeServices {
    private Sessao sessao = Sessao.getInstancia();
    private Post post;
    private PostDAO postDAO;
    private Context context;

    public RedeServices(Context context) {
        this.context = context;
    }

    public void salvarPost(String texto) {
        postDAO = new PostDAO(context);
        post = new Post();
        post.setTexto(texto);
        post.setDataHora();
        post.setIdUsuario(sessao.getUsuarioLogado().getId());
        long idPost = postDAO.inserirPost(post);
        post.setId(idPost);

        TagDAO tagDAO = new TagDAO(context);
        ArrayList<Tag> listaTags = new ArrayList<Tag>();
        String regexHashTag = "(#\\w+)";

        Pattern pattern = Pattern.compile(regexHashTag);
        Matcher matcher = pattern.matcher(texto);
        while (matcher.find()) {
            String hashTagStr = matcher.group(1);
            Tag tag = new Tag(hashTagStr);
            listaTags.add(tag);
            if (tagDAO.getTagTexto(texto) == null) {
                tagDAO.inserirTag(tag);
            }
            //ToDo chamar a função de setar a relação post-tag que ainda n existe

        }

    }

    public List<Post> exibirPosts() {
        postDAO = new PostDAO(context);
        return postDAO.getPostsByOrderId();
    }
}