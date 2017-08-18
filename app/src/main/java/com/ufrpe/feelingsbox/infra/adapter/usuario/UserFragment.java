package com.ufrpe.feelingsbox.infra.adapter.usuario;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.ufrpe.feelingsbox.R;
import com.ufrpe.feelingsbox.infra.adapter.post.PostRecyclerAdapter;
import com.ufrpe.feelingsbox.infra.adapter.post.RecyclerViewOnClickListenerhack;
import com.ufrpe.feelingsbox.redesocial.dominio.Sessao;
import com.ufrpe.feelingsbox.redesocial.gui.ActPerfilPost;
import com.ufrpe.feelingsbox.redesocial.redesocialservices.RedeServices;
import com.ufrpe.feelingsbox.usuario.dominio.Usuario;

import java.util.ArrayList;
import java.util.List;

import static com.ufrpe.feelingsbox.redesocial.dominio.BundleEnum.ID_USUARIO;
import static com.ufrpe.feelingsbox.redesocial.dominio.BundleEnum.MODO;
import static com.ufrpe.feelingsbox.redesocial.dominio.BundleEnum.SEGUIDOS;

public class UserFragment extends Fragment implements RecyclerViewOnClickListenerhack {
    private RecyclerView mRecyclerView;
    private List<Usuario> mList;
    private RedeServices redeServices;
    private String modo = "";
    private Bundle bundle;
    private Sessao sessao = Sessao.getInstancia();
    private UserRecyclerAdapter adapter;

    //Setando o RecyclerView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            /*@Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager mLinearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                PostRecyclerAdapter adapter = (PostRecyclerAdapter) mRecyclerView.getAdapter();

                if(mList.size() == mLinearLayoutManager.findLastCompletelyVisibleItemPosition() + 1){
                    List<Post> listaAux = redeServices.exibirPosts();

                    for (int i = 0; i < listaAux.size(); i++){
                        adapter.addListItem(listaAux.get(i), mList.size() );

                    }

                }
            }*/
        });


        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        redeServices = new RedeServices(getActivity());
        bundle = this.getArguments();
        Long idUser = null;

        if(bundle != null){
            modo = bundle.getString(MODO.getValor());
            idUser = bundle.getLong(ID_USUARIO.getValor());
        }

        if(modo.equals(SEGUIDOS.getValor()) && idUser != null) {
            mList = redeServices.listarSeguidos(idUser);
        } else if (idUser != null){
            mList = redeServices.listarSeguidores(idUser);
        } else {
            mList = new ArrayList<>();
        }

        adapter = new UserRecyclerAdapter(getActivity(), mList);
        adapter.setRecyclerViewOnClickListenerhack(this);
        mRecyclerView.setAdapter(adapter);

        return view;
    }

    //Click Normal
    @Override
    public void onClickListener(View view, int position) {
        Intent intent;
        Usuario usuario = mList.get(position);
        switch (view.getId()){
            case R.id.ivUser:
                intent = new Intent(view.getContext(), ActPerfilPost.class);
                intent.putExtra(ID_USUARIO.getValor(), mList.get(position).getId());
                intent.putExtra(MODO.getValor(), bundle.getString(MODO.getValor()));
                startActivity(intent);
                getActivity().finish();
                break;
            case R.id.btnFollow:
                redeServices.seguirUser(sessao.getUsuarioLogado().getId(), usuario.getId());
                adapter.atualizarSeguir(position);
                break;
            case R.id.btnUnfollow:
                redeServices.deletarUser(sessao.getUsuarioLogado().getId(), usuario.getId());
                if(sessao.getUsuarioLogado().getId() == bundle.getLong(ID_USUARIO.getValor())) {
                    adapter.removeListItem(position);
                } else {
                    adapter.atualizarSeguir(position);
                }
                break;
            case -1:
                break;
        }
    }
    //Click longo
    @Override
    public void onLongPressClickListener(View view, int position) {
        PostRecyclerAdapter adapter = (PostRecyclerAdapter) mRecyclerView.getAdapter();
        adapter.removeListItem(position);
    }

    //Passa o objeto pressionado atraves da posição
    private static class RecyclerViewTouchListener implements RecyclerView.OnItemTouchListener{
        private Context mContext;
        private GestureDetector mGestureDetector;
        private RecyclerViewOnClickListenerhack mRecyclerViewOnClickListenerhack;

        //Construtor
        public RecyclerViewTouchListener(Context mContext, final RecyclerView recyclerView, final RecyclerViewOnClickListenerhack mRecyclerViewOnClickListenerhack) {
            this.mContext = mContext;
            this.mRecyclerViewOnClickListenerhack = mRecyclerViewOnClickListenerhack;

                    mGestureDetector = new GestureDetector(mContext, new GestureDetector.SimpleOnGestureListener(){
                @Override
                public void onLongPress(MotionEvent e) {
                    super.onLongPress(e);

                    View cv = recyclerView.findChildViewUnder(e.getX(), e.getY());

                    if(cv != null && mRecyclerViewOnClickListenerhack != null){
                        mRecyclerViewOnClickListenerhack.onLongPressClickListener(cv, recyclerView.getChildPosition(cv));
                    }
                }

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    View cv = recyclerView.findChildViewUnder(e.getX(), e.getY());

                    if(cv != null && mRecyclerViewOnClickListenerhack != null){
                        mRecyclerViewOnClickListenerhack.onClickListener(cv, recyclerView.getChildPosition(cv));
                    }

                    return true;
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            mGestureDetector.onTouchEvent(e);
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}
