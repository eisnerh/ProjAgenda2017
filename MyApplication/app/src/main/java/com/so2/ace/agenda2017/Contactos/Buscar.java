package com.so2.ace.agenda2017.Contactos;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;

import com.so2.ace.agenda2017.Contactos.dbContactos.MyDBHandler;
import com.so2.ace.agenda2017.R;
import com.so2.ace.agenda2017.TodoCursorAdapter;

// Hecho por Eisner López Acevedo
public class Buscar extends AppCompatActivity {

    EditText inputSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);

        MyDBHandler dbHandler;
        dbHandler = new MyDBHandler(this, null, null, 1);
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        Cursor cursor = dbHandler.listarpersonas();

        ListView lvl_items = (ListView) findViewById(R.id.lvl_items);
        lvl_items.setTextFilterEnabled(true);
        final TodoCursorAdapter todoAdapter = new TodoCursorAdapter(this, cursor);
        lvl_items.setAdapter(todoAdapter);
    }
}