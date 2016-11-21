package com.so2.ace.agenda2017;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

// Hecho por Eisner López Acevedo

public class TodoCursorAdapter extends CursorAdapter {
    public TodoCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.activity_buscar, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView id_txt = (TextView) view.findViewById(R.id.id_txt);
        TextView apellido_txt = (TextView) view.findViewById(R.id.apellido_txt);
        TextView nombre_txt = (TextView) view.findViewById(R.id.nombre_txt);

        // Extracción de las propiedades del cursor


        int txt_id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
        String txt_apellido = cursor.getString(cursor.getColumnIndexOrThrow("apellido"));
        String txt_nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));

        id_txt.setText(String.valueOf(txt_id));
        apellido_txt.setText(txt_apellido);
        nombre_txt.setText(txt_nombre);

    }
}