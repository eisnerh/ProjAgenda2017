package com.agend.adnega.bdData;

import android.content.Context;
import android.database.Cursor;
//Cursor es un apuntador al conjunto de valores obtenidos de la consulta.
// Al inicio el cursor apunta a una dirección previa a la primera fila.
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//Hecho por Eisner López Acevedo


public class AgendaDbHelper extends SQLiteOpenHelper {


    //Crea nueva clase que extienda de SQLiteOpenHelper y llamala AgendaDbHelper.



    public static final int DATABASE_VERSION = 1; //versión de la base de datos iniciada en 1.
    public static final String DATABASE_NAME = "agenda.db"; //Nombre de la base de datos.

    //constructor y usa super para mantener la herencia del helper.
    public AgendaDbHelper(Context context) {
        //guarda el contexto de la base de datos, llamamando a las variables arriba indicadas
        //Contexto de acción para el helper.
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Tabla de la Base de Datos

        //id, titulo, lugar, hora, desc, foto.

        db.execSQL("CREATE TABLE " + AgendaContract.AgendaEntry.TABLE_NAME + " ("
                //Es recomendable que la llave primaria sea AgendaEntry._ID,
                // ya que el framework de Android usa esta referencia internamente en varios procesos.
                + AgendaContract.AgendaEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + AgendaContract.AgendaEntry.ID + " TEXT NOT NULL,"
                + AgendaContract.AgendaEntry.TITULO + " TEXT NOT NULL,"
                + AgendaContract.AgendaEntry.LUGAR + " TEXT NOT NULL,"
                + AgendaContract.AgendaEntry.HORA + " TEXT NOT NULL,"
                + AgendaContract.AgendaEntry.DESC + " TEXT NOT NULL,"
                + AgendaContract.AgendaEntry.AVATAR_URI + " TEXT,"
                //índice UNIQUE para mantener la unicidad de filas.
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

    //inserta los datos de datos fictisios en la tabla.
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

    //guarda la agenda
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
                // En cuestiones de lectura getReadableDatabase().

                // obtener los registros de nuestra tabla usaremos el método query().
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

        //Cursor es un apuntador al conjunto de valores obtenidos de la consulta.
        // Al inicio el cursor apunta a una dirección previa a la primera fila.
        //
        //Usa getWritableDatabase() para obtener el manejador de la base de datos para operaciones de escritura.



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

    //elimana el valor indicado por medio de la id de la agenda
    public int deleteAgenda(String agendaId) {
        return getWritableDatabase().delete(
                AgendaContract.AgendaEntry.TABLE_NAME,
                AgendaContract.AgendaEntry.ID + " LIKE ?",
                new String[]{agendaId});
    }

    //actualiza los datos de un valor existente en la base de datos.
    public int updateAgenda(Agenda agenda, String agendaId) {
        return getWritableDatabase().update(
                AgendaContract.AgendaEntry.TABLE_NAME,
                agenda.toContentValues(),
                AgendaContract.AgendaEntry.ID + " LIKE ?",
                new String[]{agendaId}
        );
    }
}