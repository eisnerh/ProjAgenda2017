package com.so2.ace.agenda2017;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class pre_Modificar extends AppCompatActivity {

    EditText modificar_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre__modificar);
        modificar_input = (EditText) findViewById(R.id.modificar_input);
    }


    public void modificar_clicked(View view){

        Intent i = new Intent(this, Modificar.class);
        modificar_input = (EditText) findViewById(R.id.modificar_input);
        i.putExtra("id_persona" , modificar_input.getText().toString());
        startActivity(i);
    }


}
