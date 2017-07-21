package com.ufrpe.feelingsbox.gui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.ufrpe.feelingsbox.R;

public class ActHome extends AppCompatActivity {
    private TextView lblHomeGreetings;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Encontrando Elemento da Tela(activity)
        lblHomeGreetings = (TextView)findViewById(R.id.lblHomeGreetings);

        // Set o Text no elemento TextView (Label)
        lblHomeGreetings.setText("Olá, Isso é um teste.");

        //Botão Flutuante
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
