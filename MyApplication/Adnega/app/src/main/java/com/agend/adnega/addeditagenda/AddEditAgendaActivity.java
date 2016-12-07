package com.agend.adnega.addeditagenda;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.agend.adnega.R;
import com.agend.adnega.agendas.AgendaActivity;

//Hecho por Eisner López Acevedo

public class AddEditAgendaActivity extends AppCompatActivity {

    public static final int REQUEST_ADD_AGENDA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_agenda);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String agendaId = getIntent().getStringExtra(AgendaActivity.EXTRA_AGENDAS_ID);
        //se verifica el contenido del ID del agenda para
        // determinar si se cargan los datos de un elemento existente.

        setTitle(agendaId == null ? "Añadir Agenda" : "Editar Agenda");

        AddEditAgendaFragment addEditAgendaFragment = (AddEditAgendaFragment)
                getSupportFragmentManager().findFragmentById(R.id.content_add_edit_agenda);
        if (addEditAgendaFragment == null) {
            addEditAgendaFragment = addEditAgendaFragment.newInstance(agendaId);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.content_add_edit_agenda, addEditAgendaFragment)
                    .commit();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
