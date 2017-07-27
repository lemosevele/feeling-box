package com.ufrpe.feelingsbox.usuario.gui;

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
import android.widget.TextView;

import com.ufrpe.feelingsbox.R;
import com.ufrpe.feelingsbox.infra.GuiUtil;

import static com.ufrpe.feelingsbox.R.id.fab;

public class ActPerfil extends AppCompatActivity {
    private TextView txtNome, txtNicK, txtNasc, txtSexo, txtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_perfil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtNome = (TextView) findViewById(R.id.txtNome);
        txtNicK = (TextView) findViewById(R.id.txtNick);
        txtNasc = (TextView) findViewById(R.id.txtNasc);
        txtSexo = (TextView) findViewById(R.id.txtSexo);
        txtEmail = (TextView) findViewById(R.id.txtEmail);

        //TODO Setar o texto com os dados do Usuário das variáveis TextView acima.


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_act_perfil, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_editar:
                GuiUtil.myToast(this, "Botão editar está funcionando");
                Intent it = new Intent(this, ActEditarPerfil.class);
                startActivity(it);

                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
