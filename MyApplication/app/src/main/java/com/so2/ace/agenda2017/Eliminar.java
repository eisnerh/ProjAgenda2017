package com.so2.ace.agenda2017;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
// Hecho por Eisner López Acevedo
public class Eliminar extends AppCompatActivity {

    MyDBHandler dbHandler;
    EditText elimina_input;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar);
        elimina_input = (EditText) findViewById(R.id.modificar_input);
        dbHandler = new MyDBHandler(this, null, null, 1);
    }

    public void eliminar_clicked(View view){

        dbHandler.borrarPersona(Integer.parseInt(elimina_input.getText().toString()));
        confirmacion();


    }

    public void confirmacion(){

        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage(elimina_input.getText());
        dlgAlert.setTitle("Se ha eliminado satisfactoriamente!!!");
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
