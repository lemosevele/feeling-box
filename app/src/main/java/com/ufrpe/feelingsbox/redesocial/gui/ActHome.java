package com.ufrpe.feelingsbox.redesocial.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ufrpe.feelingsbox.R;
import com.ufrpe.feelingsbox.infra.GuiUtil;
import com.ufrpe.feelingsbox.infra.PostAdapter;
import com.ufrpe.feelingsbox.redesocial.dominio.Post;

import java.util.ArrayList;

public class ActHome extends AppCompatActivity {
    private ListView lvPost;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Encontrando Elemento da Tela(activity)
        lvPost = (ListView) findViewById(R.id.lvPost);

        ArrayList<Post> listaPost = gerarPosts();
        ArrayAdapter<Post> adapter = new PostAdapter(this, listaPost);
        lvPost.setAdapter(adapter);

        //Botão Flutuante
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(ActHome.this, ActCriarPost.class);
                startActivity(intent);
            }
        });
    }

    private ArrayList<Post> gerarPosts(){
        ArrayList<Post> listaPost = new ArrayList<Post>();

        Post post = new Post();
        post.setTexto("As melhores coisas da vida são gratuitas: abraços, sorrisos, amigos, beijos, família, dormir, amor, risos e boas lembranças.");
        listaPost.add(post);

        post = new Post();
        post.setTexto("É pra frente que se anda, é pra cima que se olha e é lutando que se conquista.");
        listaPost.add(post);

        post = new Post();
        post.setTexto("Treine sua mente para ver o lado bom de qualquer situação.");
        listaPost.add(post);

        post = new Post();
        post.setTexto("Creio de coração que tudo nesta vida se renova. Tudo recomeça, tudo renasce, tudo avança. Creio no bem e na força maior que nos move. Creio em dias de paz e que a felicidade acontece quando nos colocamos a favor de todo o bem, em tudo e para todos.");
        listaPost.add(post);

        post = new Post();
        post.setTexto("Um dia você ainda vai olhar para trás e ver que os problemas eram, na verdade, os degraus que te levaram à vitória.");
        listaPost.add(post);

        post = new Post();
        post.setTexto("Cuidemos do nosso coração porque é de lá que sai o que é bom e ruim, o que constrói e destrói.");
        listaPost.add(post);

        return listaPost;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_act_home, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_perfil:
                Intent it = new Intent(ActHome.this, ActPerfil.class);
                startActivity(it);

                break;
            case R.id.action_sair:
                //Definir a ação do botão
                break;
            
        }
        return super.onOptionsItemSelected(item);
    }
}
