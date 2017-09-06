package com.ufrpe.feelingsbox.infra.adapter.usuario;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ufrpe.feelingsbox.R;
import com.ufrpe.feelingsbox.infra.GuiUtil;
import com.ufrpe.feelingsbox.infra.adapter.post.PostRecyclerAdapter;
import com.ufrpe.feelingsbox.infra.adapter.post.RecyclerViewOnClickListenerhack;
import com.ufrpe.feelingsbox.redesocial.dominio.BundleEnum;
import com.ufrpe.feelingsbox.redesocial.dominio.Sessao;
import com.ufrpe.feelingsbox.redesocial.gui.ActHome;
import com.ufrpe.feelingsbox.redesocial.gui.ActPerfilPost;
import com.ufrpe.feelingsbox.redesocial.redesocialservices.RedeServices;
import com.ufrpe.feelingsbox.usuario.dominio.Usuario;

import java.util.ArrayList;
import java.util.List;

import static com.ufrpe.feelingsbox.redesocial.dominio.BundleEnum.SEGUIDORES;
import static com.ufrpe.feelingsbox.redesocial.dominio.BundleEnum.SEGUIDOS;

/**
 * Classe que exibirá @see {@link android.support.v7.widget.CardView} com os dados do @see {@link Usuario}
 * em um @see {@link UserRecyclerAdapter}.
 */

public class UserFragment extends Fragment implements RecyclerViewOnClickListenerhack {
    private RecyclerView mRecyclerView;
    private List<Usuario> mList = new ArrayList<>();
    private RedeServices redeServices;
    private Sessao sessao = Sessao.getInstancia();
    private UserRecyclerAdapter adapter;
    private Usuario usuarioDonoTela;
    private BundleEnum modo;

    //Setando o RecyclerView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
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
        usuarioDonoTela = sessao.getUltimoUsuario();
        modo = sessao.getUltimoModo();

        if(modo == SEGUIDOS && usuarioDonoTela != null) {
            mList = redeServices.listarSeguidos(usuarioDonoTela.getId());
        } else if (modo == SEGUIDORES && usuarioDonoTela != null){
            mList = redeServices.listarSeguidores(usuarioDonoTela.getId());
        } else {
            String msgErro = "usuarioDonoTela = " + usuarioDonoTela + " modo = " + modo;
            Log.d("onCreateView-UserFragme", msgErro);
            GuiUtil.myToast(getActivity(), msgErro);
            Intent intent = new Intent(getActivity(), ActHome.class);
            startActivity(intent);
            getActivity().finish();
        }

        adapter = new UserRecyclerAdapter(getActivity(), mList);
        adapter.setRecyclerViewOnClickListenerhack(this);
        mRecyclerView.setAdapter(adapter);

        return view;
    }

    /**
     * Método que executa ação ao clicar em um elemento da @see {@link android.support.v7.widget.RecyclerView.ViewHolder}
     * @param view
     * @param position
     */

    @Override
    public void onClickListener(View view, int position) {
        Intent intent;
        Usuario usuario = mList.get(position);
        switch (view.getId()){
            case R.id.ivUser:
                intent = new Intent(view.getContext(), ActPerfilPost.class);
                sessao.addUsuario(usuario);
                startActivity(intent);
                getActivity().finish();
                break;
            case R.id.btnFollow:
                redeServices.seguirUser(sessao.getUsuarioLogado().getId(), usuario.getId());
                adapter.atualizarSeguir(position);
                break;
            case R.id.btnUnfollow:
                redeServices.deletarUser(sessao.getUsuarioLogado().getId(), usuario.getId());
                if(sessao.getUsuarioLogado().getId() == usuarioDonoTela.getId() && modo == SEGUIDOS) {
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
}
