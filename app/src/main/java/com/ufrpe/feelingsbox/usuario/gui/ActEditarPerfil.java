package com.ufrpe.feelingsbox.usuario.gui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.ufrpe.feelingsbox.R;
import com.ufrpe.feelingsbox.infra.GuiUtil;
import com.ufrpe.feelingsbox.infra.Mask;

import static com.ufrpe.feelingsbox.usuario.dominio.SexoEnum.SexoEnumLista;

public class ActEditarPerfil extends AppCompatActivity {
    private EditText edtNomePerfil, edtNascPerfil;
    private Spinner spnSexoPerfil;

    //Lista para por no Spinner
    private String[] listaSexo = SexoEnumLista();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_editar_perfil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        edtNomePerfil = (EditText) findViewById(R.id.edtNomePerfil);
        edtNascPerfil = (EditText) findViewById(R.id.edtNascPerfil);
        edtNascPerfil.addTextChangedListener(Mask.insert("##/##/####", edtNascPerfil));

        //ArrayAdapter é usado preparar a lista da por no Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listaSexo);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Encontrando o Spinner e colocando a lista adaptada
        spnSexoPerfil = (Spinner)findViewById(R.id.spnSexoPerfil);
        spnSexoPerfil.setAdapter(adapter);
        //Setando o valor inicial do Spinner
        spnSexoPerfil.setSelection(adapter.getPosition("Masculino"));

        //Metodo para quando um elemento do Spinner é selecionado()
        spnSexoPerfil.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_act_editar_perfil, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_salvar:
                GuiUtil.myToast(this, "Botão salvar foi precionado");
                break;
            case R.id.action_cancelar:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
