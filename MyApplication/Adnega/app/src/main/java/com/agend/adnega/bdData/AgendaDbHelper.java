package com.agend.adnega.bdData;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by eisne on 4/12/2016.
 */

public class AgendaDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "agenda.db";

    public AgendaDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + AgendaContract.AgendaEntry.TABLE_NAME + " ("
                + AgendaContract.AgendaEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + AgendaContract.AgendaEntry.ID + " TEXT NOT NULL,"
                + AgendaContract.AgendaEntry.TITULO + " TEXT NOT NULL,"
                + AgendaContract.AgendaEntry.LUGAR + " TEXT NOT NULL,"
                + AgendaContract.AgendaEntry.HORA + " TEXT NOT NULL,"
                + AgendaContract.AgendaEntry.DESC + " TEXT NOT NULL,"
                + AgendaContract.AgendaEntry.AVATAR_URI + " TEXT,"
                + "UNIQUE (" + AgendaContract.AgendaEntry.ID + "))");



        // Insertar datos ficticios para prueba inicial
        mockData(db);

    }

    private void mockData(SQLiteDatabase sqLiteDatabase) {
        mockAgenda(sqLiteDatabase, new Agenda("Visita al Dentista", "San Carlos",
                "10:00:00", "Gran profesional con experiencia de 5 años.",
                "tareas_check.png"));
        mockAgenda(sqLiteDatabase, new Agenda("Claro", "Centro, San Carlos",
                "11:00:00", "Cambio de Plan.",
                "tareas_check.png"));
        mockAgenda(sqLiteDatabase, new Agenda("Visita al Pediatra", "Ebais San Carlos",
                "11:30:00", "Vacuna 4 años.",
                "tareas_check.png"));
        mockAgenda(sqLiteDatabase, new Agenda("Visita al Veterinario", "Barrio San Antonio",
                "13:00:00", "Vacunas",
                "tareas_check.png"));

    }

    public long mockAgenda(SQLiteDatabase db, Agenda agenda) {
        return db.insert(
                AgendaContract.AgendaEntry.TABLE_NAME,
                null,
                agenda.toContentValues());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,
                          int oldVersion, int newVersion) {
        // No hay operaciones
    }

    public long saveAgenda(Agenda agenda) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.insert(
                AgendaContract.AgendaEntry.TABLE_NAME,
                null,
                agenda.toContentValues());

    }

    //obtiene la lista de tareas
    public Cursor getAllData() {
        return getReadableDatabase()
                .query(
                        AgendaContract.AgendaEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
    }

    //obtiene la lista de tarea por el id.
    public Cursor getAgendaById(String agendaId) {
        Cursor c = getReadableDatabase().query(
                AgendaContract.AgendaEntry.TABLE_NAME,
                null,
                AgendaContract.AgendaEntry.ID + " LIKE ?",
                new String[]{agendaId},
                null,
                null,
                null);
        return c;
    }

    public int deleteAgenda(String agendaId) {
        return getWritableDatabase().delete(
                AgendaContract.AgendaEntry.TABLE_NAME,
                AgendaContract.AgendaEntry.ID + " LIKE ?",
                new String[]{agendaId});
    }

    public int updateAgenda(Agenda agenda, String agendaId) {
        return getWritableDatabase().update(
                AgendaContract.AgendaEntry.TABLE_NAME,
                agenda.toContentValues(),
                AgendaContract.AgendaEntry.ID + " LIKE ?",
                new String[]{agendaId}
        );
    }
}