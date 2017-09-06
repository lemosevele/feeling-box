package com.ufrpe.feelingsbox.infra.adapter.comentario;

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
import com.ufrpe.feelingsbox.infra.adapter.post.RecyclerViewOnClickListenerhack;
import com.ufrpe.feelingsbox.redesocial.dominio.Comentario;
import com.ufrpe.feelingsbox.redesocial.dominio.Sessao;
import com.ufrpe.feelingsbox.redesocial.gui.ActCriarPostComentario;
import com.ufrpe.feelingsbox.redesocial.gui.ActHome;
import com.ufrpe.feelingsbox.redesocial.gui.ActPerfilPost;
import com.ufrpe.feelingsbox.redesocial.redesocialservices.RedeServices;
import com.ufrpe.feelingsbox.usuario.dominio.Usuario;
import com.ufrpe.feelingsbox.usuario.usuarioservices.UsuarioService;

import java.util.List;

import static com.ufrpe.feelingsbox.redesocial.dominio.BundleEnum.COMENTARIO;
import static com.ufrpe.feelingsbox.redesocial.dominio.BundleEnum.ID_POST;

/**
 * Classe que exibirá @see {@link android.support.v7.widget.CardView} com os dados do @see {@link Comentario}
 * em um @see {@link ComentarioRecyclerAdapter}.
 */

public class ComentarioFragment extends Fragment implements RecyclerViewOnClickListenerhack {
    private RecyclerView mRecyclerView;
    private List<Comentario> mList;
    private Sessao sessao = Sessao.getInstancia();

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
                ComentarioRecyclerAdapter adapter = (ComentarioRecyclerAdapter) mRecyclerView.getAdapter();

                if(mList.size() == mLinearLayoutManager.findLastCompletelyVisibleItemPosition() + 1){
                    List<Comentario> listaAux = redeServices.exibirComentarios();

                    for (int i = 0; i < listaAux.size(); i++){
                        adapter.addListItem(listaAux.get(i), mList.size() );

                    }

                }
            }*/
        });

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        RedeServices redeServices = new RedeServices(getActivity());

        if(sessao.getUltimoPost() != null){
            mList = redeServices.exibirComentarios(sessao.getUltimoPost().getId());
        } else {
            String msgErro = "Ultimo comentário = " + sessao.getUltimoPost();
            Log.d("onCreateView-ComentFrag", msgErro);
            GuiUtil.myToast(getActivity(), msgErro);
            Intent intent = new Intent(getActivity(), ActHome.class);
            startActivity(intent);
            getActivity().finish();
        }

        ComentarioRecyclerAdapter adapter = new ComentarioRecyclerAdapter(getActivity(), mList);
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
        UsuarioService usuarioService = new UsuarioService(view.getContext());
        switch (view.getId()){
            case R.id.ivUser:
                Usuario usuarioSelecionado = usuarioService.buscarUsuario(mList.get(position).getIdUsuario());
                sessao.addUsuario(usuarioSelecionado);
                intent = new Intent(view.getContext(), ActPerfilPost.class);
                startActivity(intent);
                getActivity().finish();
                break;
            case R.id.btnComentar:
                sessao.addModo(COMENTARIO);
                intent = new Intent(view.getContext(), ActCriarPostComentario.class);
                intent.putExtra(ID_POST.getValor(), mList.get(position).getId());
                startActivity(intent);
                getActivity().finish();
                break;
            case -1:
                break;
        }

    }

    //Click longo
    @Override
    public void onLongPressClickListener(View view, int position) {
        ComentarioRecyclerAdapter adapter = (ComentarioRecyclerAdapter) mRecyclerView.getAdapter();
        adapter.removeListItem(position);
    }
}
