package com.ufrpe.feelingsbox.infra.adapter.post;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.ufrpe.feelingsbox.R;
import com.ufrpe.feelingsbox.redesocial.dominio.Post;
import com.ufrpe.feelingsbox.redesocial.redesocialservices.RedeServices;
import com.ufrpe.feelingsbox.usuario.usuarioservices.UsuarioService;

import java.util.List;

/**
 * Created by Faig-PC on 31/07/2017.
 */

public class PostRecyclerAdapter extends RecyclerView.Adapter<PostRecyclerAdapter.MyViewHolder> {
    private List<Post> mList;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewOnClickListenerhack mRecyclerViewOnClickListenerhack;
    private UsuarioService usuarioService;

    //Construtor
    public PostRecyclerAdapter(Context context, List<Post> lista) {
        mList = lista;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //Cria os Itens da Lista (até alguns a mais do que a tela comporta)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_post_cardview, parent, false);
        MyViewHolder mvh = new MyViewHolder(view);

        return mvh;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setRecyclerViewOnClickListenerhack(RecyclerViewOnClickListenerhack r){
        mRecyclerViewOnClickListenerhack = r;
    }

    public void addListItem(Post post, int position){
        mList.add(post);
        notifyItemInserted(position);
    }

    public void removeListItem(int position){
        mList.remove(position);
        notifyItemRemoved(position);
    }

    //Autaliza os itens da lista (Os que não estão visíveis)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //holder.ivUser.setImageResource( mList.get(position).getFoto() );
        usuarioService = new UsuarioService(mLayoutInflater.getContext());
        long idUsuario = mList.get(position).getIdUsuario();
        String nickUsuario = usuarioService.buscarNick(idUsuario) ;
        holder.txtDonoPost.setText(nickUsuario);
        holder.txtPostagem.setText(mList.get(position).getTexto());

        //Animação
        try {
            YoYo.with(Techniques.ZoomIn)
                    .duration(1000)
                    .repeat(0)
                    .playOn(holder.itemView);
        }catch (Exception e){

        }

    }

    //ViewHolder personalizada
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView ivUser;
        public TextView txtDonoPost, txtPostagem;


        public MyViewHolder(View itemView) {
            super(itemView);

            ivUser      = (ImageView) itemView.findViewById(R.id.ivUser);
            txtDonoPost = (TextView) itemView.findViewById(R.id.txtDonoPost);
            txtPostagem = (TextView) itemView.findViewById(R.id.txtPostagem);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mRecyclerViewOnClickListenerhack != null){
                mRecyclerViewOnClickListenerhack.onClickListener(v, getPosition());
            }
        }
    }
}
