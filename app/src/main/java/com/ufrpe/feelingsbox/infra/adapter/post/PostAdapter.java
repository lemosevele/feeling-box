package com.ufrpe.feelingsbox.infra.adapter.post;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ufrpe.feelingsbox.R;
import com.ufrpe.feelingsbox.redesocial.dominio.Post;
import com.ufrpe.feelingsbox.usuario.persistencia.UsuarioDAO;

import java.util.ArrayList;

/**
 * Created by Faig-PC on 27/07/2017.
 */

public class PostAdapter extends ArrayAdapter<Post>{

    private final Context context;
    private final ArrayList<Post> listaPost;

    public PostAdapter(Context context, ArrayList<Post> listaPost){
        super(context, R.layout.post_layout_listview, listaPost);

        this.context = context;
        this.listaPost = listaPost;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.post_layout_listview, parent, false);

        TextView txtDonoPost = (TextView)rowView.findViewById(R.id.txtDonoPost);
        TextView txtPostagem = (TextView)rowView.findViewById(R.id.txtPostagem);

        long idUsuário = listaPost.get(position).getIdUsuario();
        UsuarioDAO usuarioDAO = new UsuarioDAO(context);

       // Usuario usuario = usuarioDAO.getUsuarioId(idUsuário);

        //txtDonoPost.setText(usuario.getNick());
        txtPostagem.setText(listaPost.get(position).getTexto());

        return rowView;

    }

}
