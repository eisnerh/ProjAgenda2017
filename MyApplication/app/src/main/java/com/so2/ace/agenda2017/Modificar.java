package com.so2.ace.agenda2017;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
// Hecho por Eisner López Acevedo
public class Modificar extends AppCompatActivity {
    MyDBHandler dbHandler;
    EditText nombre_input;
    EditText apellido_input;
    EditText edad_input;
    EditText telefono_input;
    EditText email_input;
    EditText sangre_input;
    int idglobal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Aqui se hace el retrieve de la base de datos tomando un valor que viene en el intent anterior

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar);
        nombre_input = (EditText) findViewById(R.id.nombre_input);
        apellido_input = (EditText) findViewById(R.id.apellido_input);
        edad_input = (EditText) findViewById(R.id.edad_input);
        telefono_input = (EditText) findViewById(R.id.telefono_input);
        email_input = (EditText) findViewById(R.id.email_input);
        sangre_input = (EditText) findViewById(R.id.sangre_input);
        dbHandler = new MyDBHandler(this, null, null, 1);
        Personas persona = new Personas();
        Intent i = getIntent(); // gets the previously created intent
        String stringid = i.getStringExtra("id_persona");
        int id = Integer.parseInt(stringid);
        Cursor c = dbHandler.personabyid(id);

        //Vuelve a rellenar los inputs con los valores del cursor
        nombre_input.setText(c.getString(c.getColumnIndexOrThrow("nombre")));
        apellido_input.setText(c.getString(c.getColumnIndexOrThrow("apellido")));
        edad_input.setText(c.getString(c.getColumnIndexOrThrow("edad")));
        telefono_input.setText(c.getString(c.getColumnIndexOrThrow("telefono")));
        email_input.setText(c.getString(c.getColumnIndexOrThrow("email")));
        sangre_input.setText(c.getString(c.getColumnIndexOrThrow("tiposangre")));
        idglobal = c.getInt(c.getColumnIndexOrThrow("_id"));

    }

    public void modificar_clicked(View view) {

        Personas persona = new Personas(nombre_input.getText().toString(), apellido_input.getText().toString(), Integer.parseInt(edad_input.getText().toString()), telefono_input.getText().toString(), email_input.getText().toString(), sangre_input.getText().toString());
        persona.set_id(idglobal);
        dbHandler.updatepersona(persona);
        confirmacion();
        limpiarcampos();
        finish(); //Termina la actividad y vuelve al menu principal

    }

    public void confirmacion() {

        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
        dlgAlert.setMessage("Se ha modificado exitosamente!");
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

    //Limpia los valores entrados para efectos de estetica
    public void limpiarcampos() {

        nombre_input.setText("");
        apellido_input.setText("");
        edad_input.setText("");
        telefono_input.setText("");
        email_input.setText("");
        sangre_input.setText("");

    }
}