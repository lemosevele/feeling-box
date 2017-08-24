package com.ufrpe.feelingsbox.infra.adapter.post;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ufrpe.feelingsbox.R;
import com.ufrpe.feelingsbox.infra.Animacao;
import com.ufrpe.feelingsbox.infra.FormataData;
import com.ufrpe.feelingsbox.redesocial.dominio.Post;
import com.ufrpe.feelingsbox.redesocial.dominio.Sessao;
import com.ufrpe.feelingsbox.redesocial.redesocialservices.RedeServices;
import com.ufrpe.feelingsbox.usuario.usuarioservices.UsuarioService;

import java.util.List;

public class PostRecyclerAdapter extends RecyclerView.Adapter<PostRecyclerAdapter.MyViewHolder> {
    private Sessao sessao = Sessao.getInstancia();
    private RedeServices redeServices;
    private UsuarioService usuarioService;
    private List<Post> mList;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewOnClickListenerhack mRecyclerViewOnClickListenerhack;

    //Construtor
    public PostRecyclerAdapter(Context context, List<Post> lista) {
        mList = lista;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        redeServices = new RedeServices(mLayoutInflater.getContext());
        usuarioService = new UsuarioService(mLayoutInflater.getContext());
    }

    //Cria os Itens da Lista (até alguns a mais do que a tela comporta)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_post_cardview, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
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
        long idUsuario = mList.get(position).getIdUsuario();
        long idPost = mList.get(position).getId();
        long qtnComentario = redeServices.qtdComentariosPost(idPost);
        String nickUsuario = usuarioService.buscarNick(idUsuario) ;
        String data = FormataData.tempoParaMostrarEmPost(mList.get(position).getDataHora());
        holder.numComentario.setText(qtnComentario < 2 ? qtnComentario + " comentário" : qtnComentario + " comentários");
        holder.txtDonoPost.setText(nickUsuario);
        holder.txtPostagem.setText(mList.get(position).getTexto());
        holder.txtData.setText(data);

        if(redeServices.verificacaoSeguidor(sessao.getUsuarioLogado().getId(), idUsuario)){
            holder.txtDonoPost.setTextColor(mLayoutInflater.getContext().getResources()
                                            .getColor(R.color.colorUserFontFavorite));
        } else {
            holder.txtDonoPost.setTextColor(mLayoutInflater.getContext().getResources()
                                            .getColor(R.color.colorUserFont));
        }
        Animacao.animacaoZoomIn(holder.itemView);
    }

    //ViewHolder personalizada
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView ivUser;
        public TextView txtDonoPost, txtPostagem, txtData, numComentario;
        public Button btnComentar;


        public MyViewHolder(final View itemView) {
            super(itemView);

            ivUser      = (ImageView) itemView.findViewById(R.id.ivUser);
            txtDonoPost = (TextView) itemView.findViewById(R.id.txtDonoPost);
            txtPostagem = (TextView) itemView.findViewById(R.id.txtPostagem);
            txtData     = (TextView) itemView.findViewById(R.id.txtData);
            numComentario = (TextView) itemView.findViewById(R.id.numComentario);
            btnComentar = (Button) itemView.findViewById(R.id.btnComentar);

            ivUser.setOnClickListener(this);
            btnComentar.setOnClickListener(this);
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