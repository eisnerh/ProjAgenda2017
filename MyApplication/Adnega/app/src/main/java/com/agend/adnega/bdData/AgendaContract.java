package com.agend.adnega.bdData;

import android.provider.BaseColumns;

/**
 * Created by eisne on 4/12/2016.
 */

public class AgendaContract {
    public static abstract class AgendaEntry implements BaseColumns {

        public static final String TABLE_NAME ="agenda";

        public static final String ID = "id";
        public static final String TITULO = "titulo";
        public static final String LUGAR = "lugar";
        public static final String HORA = "hora";
        public static final String AVATAR_URI = "avatarUri";
        public static final String DESC = "desc";
    }
}
