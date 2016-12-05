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

/**
 * Created by eisne on 4/12/2016.
 */

class AgendaCursorAdapter extends CursorAdapter {
    AgendaCursorAdapter(Context context, Cursor c) {

        super(context, c, 0);
    }

    @Override
    public View newView(Context context,
                        Cursor cursor, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.list_item_agenda, viewGroup, false);

    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {

        // Referencias UI.
        TextView nameText = (TextView) view.findViewById(R.id.tv_titulo);
        final ImageView avatarImage = (ImageView) view.findViewById(R.id.iv_avatar);
        TextView horaText = (TextView) view.findViewById(R.id.tv_hora);

        // Get valores.
        String name = cursor.getString(cursor.getColumnIndex(AgendaContract.AgendaEntry.TITULO));
        String avatarUri = cursor.getString(cursor.getColumnIndex(AgendaContract.AgendaEntry.AVATAR_URI));
        String hora = cursor.getString(cursor.getColumnIndex(AgendaContract.AgendaEntry.HORA));

        // Setup.
        nameText.setText(name);
        horaText.setText(hora);
        Glide
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