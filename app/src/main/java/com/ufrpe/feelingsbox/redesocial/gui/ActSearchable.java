package com.ufrpe.feelingsbox.redesocial.gui;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ufrpe.feelingsbox.R;
import com.ufrpe.feelingsbox.infra.GuiUtil;
import com.ufrpe.feelingsbox.infra.adapter.post.PostRecyclerAdapter;
import com.ufrpe.feelingsbox.infra.adapter.post.RecyclerViewOnClickListenerhack;
import com.ufrpe.feelingsbox.infra.provider.SearchableProvider;
import com.ufrpe.feelingsbox.redesocial.dominio.Post;
import com.ufrpe.feelingsbox.redesocial.dominio.Sessao;
import com.ufrpe.feelingsbox.redesocial.redesocialservices.RedeServices;
import com.ufrpe.feelingsbox.usuario.dominio.Usuario;
import com.ufrpe.feelingsbox.usuario.usuarioservices.UsuarioService;

import java.util.ArrayList;

import static com.ufrpe.feelingsbox.redesocial.dominio.BundleEnum.ID_POST;

public class ActSearchable extends AppCompatActivity implements RecyclerViewOnClickListenerhack{
    private ArrayList<Post> mListAux;
    private RecyclerView mRecyclerView;
    private PostRecyclerAdapter adapter;
    private TextView txtMsgNaoEncontrado;
    private Sessao sessao = Sessao.getInstancia();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            super.onCreate(savedInstanceState);
            setContentView(R.layout.act_searchable);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            txtMsgNaoEncontrado = (TextView) findViewById(R.id.txtMsg);

            if(savedInstanceState != null){
                mListAux = savedInstanceState.getParcelableArrayList("mListAux");
            } else {
                mListAux = new ArrayList<>();
            }

            mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
            mRecyclerView.setHasFixedSize(true);

            LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
            mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(mLinearLayoutManager);

            adapter = new PostRecyclerAdapter(this, mListAux);
            adapter.setRecyclerViewOnClickListenerhack(this);
            mRecyclerView.setAdapter(adapter);

            handleSearch(getIntent());
        } catch (Exception e){
            GuiUtil.myToast(this, e);
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent); //Evita problemas ao rotacionar a tela.
        handleSearch(intent);
    }

    public void handleSearch(Intent intent){
        if(Intent.ACTION_SEARCH.equalsIgnoreCase(intent.getAction())){
            String stringBusca = intent.getStringExtra(SearchManager.QUERY);

            buscarPosts(stringBusca);
        }
    }

    public void buscarPosts(String stringBusca){
        mListAux.clear();
        RedeServices redeServices = new RedeServices(this);

        ArrayList<Post> mList = (ArrayList<Post>) redeServices.buscarPosts(stringBusca);

        for(int i = 0; i < mList.size(); i++){
            mListAux.add(mList.get(i));
        }

        mRecyclerView.setVisibility(mListAux.isEmpty() ? View.GONE : View.VISIBLE);
        if(mListAux.isEmpty()){
            txtMsgNaoEncontrado.setVisibility(View.VISIBLE);
        } else if(!mListAux.isEmpty()){
            txtMsgNaoEncontrado.setVisibility(View.INVISIBLE);
            SearchableProvider.salvarSugestao(getApplicationContext(), stringBusca);
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        outState.putParcelableArrayList("mListAux", mListAux);

        super.onSaveInstanceState(outState);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_act_searchable, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView;
        MenuItem item = menu.findItem(R.id.action_pesquisar);

        searchView = (SearchView) item.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    //Click Normal
    @Override
    public void onClickListener(View view, int position) {
        Intent intent;
        UsuarioService usuarioService = new UsuarioService(view.getContext());
        Usuario usuarioSelecionado;
        switch (view.getId()){
            case R.id.ivUser:
                usuarioSelecionado = usuarioService.buscarUsuario(mListAux.get(position).getIdUsuario());
                sessao.addUsuario(usuarioSelecionado);
                intent = new Intent(view.getContext(), ActPerfilPost.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btnComentar:
                intent = new Intent(view.getContext(), ActCriarComentario.class);
                intent.putExtra(ID_POST.getValor(), mListAux.get(position).getId());
                startActivity(intent);
                finish();
                break;
            case -1:
                usuarioSelecionado = usuarioService.buscarUsuario(mListAux.get(position).getIdUsuario());
                sessao.addPost(mListAux.get(position));
                sessao.addUsuario(usuarioSelecionado);
                intent = new Intent(view.getContext(), ActPost.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public void onLongPressClickListener(View view, int position) {

    }
}
