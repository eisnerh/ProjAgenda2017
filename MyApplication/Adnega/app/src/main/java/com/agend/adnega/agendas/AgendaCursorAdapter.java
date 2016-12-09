package com.agend.adnega.agendas;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.agend.adnega.R;
import com.agend.adnega.bdData.AgendaContract;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

//Hecho por Eisner López Acevedo

class AgendaCursorAdapter extends CursorAdapter {

    // CursorAdapter es una clase abstracta de la cual se ha de crear tu adaptador personalizado.
    // Con ArrayAdapter teníamos que sobrescribir el método getView()
    // para inflar nuestras filas con los datos de la lista.
    AgendaCursorAdapter(Context context, Cursor c) {

        // constructor que transmita los parámetros a través de super para mantener la herencia.
        super(context, c, 0);
    }

    @Override
    public View newView(Context context,
                        Cursor cursor, ViewGroup viewGroup) {
        // newView() es quien infla cada view de la lista.
        //
        // no es necesario repetir ya que esto es manejado internamente.
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.list_item_agenda, viewGroup, false);

    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        // bindView() es el encargado de poblar la lista con los datos del cursor
        // no es necesario repetir ya que esto es manejado internamente.

        // Referencias UI.
        TextView nameText = (TextView) view.findViewById(R.id.tv_titulo);
        final ImageView avatarImage = (ImageView) view.findViewById(R.id.iv_avatar);
        TextView horaText = (TextView) view.findViewById(R.id.tv_hora);


        // Get valores.
        String name = cursor.getString(cursor.getColumnIndex(AgendaContract.AgendaEntry.TITULO));
        String avatarUri = "agenda.jpg";
        String hora = cursor.getString(cursor.getColumnIndex(AgendaContract.AgendaEntry.HORA));


        // Setup.
        nameText.setText(name);
        horaText.setText(hora);

        Glide
                //Glide hace parte de una librería con el mismo nombre,
                // cuyo objetivo es cargar imágenes de forma eficiente.

                //Básicamente ese código carga la imagen desde la carpeta assets en forma de Bitmap
                // sobre el view avatarImage.
                .with(context)
                .load(Uri.parse("file:///android_asset/" + avatarUri))
                .asBitmap()
                .error(R.drawable.ic_account_circle)
                .centerCrop()
                .into(new BitmapImageViewTarget(avatarImage) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable drawable
                                = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        drawable.setCircular(true);
                        avatarImage.setImageDrawable(drawable);
                    }
                });

    }

}