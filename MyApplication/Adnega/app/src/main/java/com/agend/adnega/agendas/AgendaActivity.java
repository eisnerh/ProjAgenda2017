package com.agend.adnega.agendas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.agend.adnega.R;

public class AgendaActivity extends AppCompatActivity {

    public static final String EXTRA_AGENDAS_ID = "extra_agenda_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AgendasFragment fragment = (AgendasFragment)
                getSupportFragmentManager().findFragmentById(R.id.content_agenda);

        if (fragment == null) {
            fragment = AgendasFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.content_agenda, fragment)
                    .commit();
        }
    }

}
