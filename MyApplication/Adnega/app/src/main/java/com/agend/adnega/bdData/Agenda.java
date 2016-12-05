package com.agend.adnega.bdData;

import android.content.ContentValues;
import android.database.Cursor;


import java.util.UUID;

/**
 * Created by eisne on 4/12/2016.
 */

public class Agenda {
    private String id;
    private String titulo;
    private String lugar;
    private String hora;
    private String desc;
    private String avatarUri;

    public Agenda(String titulo,
                  String lugar, String hora,
                  String desc, String avatarUri) {

        this.id = UUID.randomUUID().toString();
        this.titulo = titulo;
        this.lugar = lugar;
        this.hora = hora;
        this.desc = desc;
        this.avatarUri = avatarUri;

    }

    public Agenda(Cursor cursor) {
        id = cursor.getString(cursor.getColumnIndex(AgendaContract.AgendaEntry.ID));
        titulo = cursor.getString(cursor.getColumnIndex(AgendaContract.AgendaEntry.TITULO));
        lugar = cursor.getString(cursor.getColumnIndex(AgendaContract.AgendaEntry.LUGAR));
        hora = cursor.getString(cursor.getColumnIndex(AgendaContract.AgendaEntry.HORA));
        desc = cursor.getString(cursor.getColumnIndex(AgendaContract.AgendaEntry.DESC));
        avatarUri = cursor.getString(cursor.getColumnIndex(AgendaContract.AgendaEntry.AVATAR_URI));
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(AgendaContract.AgendaEntry.ID, id);
        values.put(AgendaContract.AgendaEntry.TITULO, titulo);
        values.put(AgendaContract.AgendaEntry.LUGAR, lugar);
        values.put(AgendaContract.AgendaEntry.HORA, hora);
        values.put(AgendaContract.AgendaEntry.DESC, desc);
        values.put(AgendaContract.AgendaEntry.AVATAR_URI, avatarUri);
        return values;
    }

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getLugar() {
        return lugar;
    }

    public String getHora() {
        return hora;
    }

    public String getDesc() {
        return desc;
    }

    public String getAvatarUri() {
        return avatarUri;
    }

}
