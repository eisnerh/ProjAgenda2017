package com.so2.ace.agenda2017;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //botón Agregar para llamar al activity activity_agregar
    public void btnAgregar(View view){
        Intent agregar = new Intent(this, Agregar.class);
        startActivity(agregar);
    }
}
