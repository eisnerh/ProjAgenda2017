package com.agend.adnega.bdData;

import android.provider.BaseColumns;

//Hecho por Eisner López Acevedo

public class AgendaContract {
    public static abstract class AgendaEntry implements BaseColumns {

        /*La documentación de Android nos recomienda crear una
    clase llamada Contract Class,
    la cual guarda como constantes todas las características de la base de datos.*/

        //Creamos la clase interna AgendaEntry para guardar el
        // nombre de las columnas de la tabla.
        //Se implementó la interfaz BaseColumns
        // con el fin de agregar una columna extra que se recomienda tenga toda tabla.

        public static final String TABLE_NAME ="agenda";

        public static final String ID = "id";
        public static final String TITULO = "titulo";
        public static final String LUGAR = "lugar";
        public static final String HORA = "hora";
        public static final String AVATAR_URI = "avatarUri";
        public static final String DESC = "desc";
    }
}
