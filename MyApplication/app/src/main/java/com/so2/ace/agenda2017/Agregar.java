package com.so2.ace.agenda2017;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Agregar extends AppCompatActivity {

    MyDBHandler dbHandler;
    EditText nombre_input;
    EditText apellido_input;
    EditText edad_input;
    EditText telefono_input;
    EditText email_input;
    EditText alias_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);

        nombre_input = (EditText) findViewById(R.id.nombre_input);
        apellido_input = (EditText) findViewById(R.id.apellido_input);
        edad_input = (EditText) findViewById(R.id.edad_input);
        telefono_input = (EditText) findViewById(R.id.telefono_input);
        email_input = (EditText) findViewById(R.id.email_input);
        alias_input = (EditText) findViewById(R.id.alias_txt);
        dbHandler = new MyDBHandler(this, null, null, 1);
    }

    //Añade una Persona a la Base de Datos

    public void agregar_clicked(View view) {

        Personas persona = new Personas(nombre_input.getText().toString(),
                apellido_input.getText().toString(),
                Integer.parseInt(edad_input.getText().toString()),
                telefono_input.getText().toString(),
                email_input.getText().toString(),
                alias_input.getText().toString());
        dbHandler.addPersona(persona);
        confirmacion();
        limpiarcampos();
    }


    //Limpia los valores entrados para efectos de estetica
    public void limpiarcampos() {

        nombre_input.setText("");
        apellido_input.setText("");
        edad_input.setText("");
        telefono_input.setText("");
        email_input.setText("");
        alias_input.setText("");

    }

    public void confirmacion() {

        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
        dlgAlert.setMessage("Se ha agregado exitosamente!");
        dlgAlert.setTitle("Agregar Persona");
        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //dismiss the dialog
                    }
                });
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }

}
