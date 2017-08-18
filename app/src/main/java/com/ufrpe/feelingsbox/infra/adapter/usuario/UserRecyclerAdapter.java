package com.ufrpe.feelingsbox.infra.adapter.usuario;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.ufrpe.feelingsbox.R;
import com.ufrpe.feelingsbox.infra.adapter.post.RecyclerViewOnClickListenerhack;
import com.ufrpe.feelingsbox.usuario.dominio.Usuario;
import com.ufrpe.feelingsbox.usuario.usuarioservices.UsuarioService;

import java.util.List;

public class UserRecyclerAdapter extends RecyclerView.Adapter<UserRecyclerAdapter.MyViewHolder> {
    private List<Usuario> mList;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewOnClickListenerhack mRecyclerViewOnClickListenerhack;
    private UsuarioService usuarioService;

    //Construtor
    public UserRecyclerAdapter(Context context, List<Usuario> lista) {
        mList = lista;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //Cria os Itens da Lista (até alguns a mais do que a tela comporta)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_user_cardview, parent, false);
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

    public void addListItem(Usuario usuario, int position){
        mList.add(usuario);
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
        holder.txtUser.setText(mList.get(position).getNick());
        //holder.numSeguidos.setText();
        //holder.numSeguidos.setText();

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
        public TextView txtUser, numSeguidos, numSeguidores;
        public ImageButton btnFollow, btnUnfollow;


        public MyViewHolder(final View itemView) {
            super(itemView);

            ivUser          = (ImageView) itemView.findViewById(R.id.ivUser);
            txtUser         = (TextView) itemView.findViewById(R.id.txtUser);
            numSeguidos     = (TextView) itemView.findViewById(R.id.numSeguidos);
            numSeguidores   = (TextView) itemView.findViewById(R.id.numSeguidores);
            btnFollow       = (ImageButton) itemView.findViewById(R.id.btnFollow);
            btnUnfollow     = (ImageButton) itemView.findViewById(R.id.btnUnfollow);

            ivUser.setOnClickListener(this);
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
