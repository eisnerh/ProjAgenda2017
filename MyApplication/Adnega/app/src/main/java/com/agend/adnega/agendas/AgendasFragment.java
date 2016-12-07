package com.agend.adnega.agendas;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.agend.adnega.R;
import com.agend.adnega.addeditagenda.AddEditAgendaActivity;
import com.agend.adnega.agendadetail.AgendaDetailActivity;
import com.agend.adnega.bdData.AgendaContract;
import com.agend.adnega.bdData.AgendaDbHelper;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AgendasFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * //Hecho por Eisner López Acevedo
 */
public class AgendasFragment extends Fragment {
    public static final int REQUEST_UPDATE_DELETE_AGENDA = 2;

    private AgendaDbHelper mAgendaDbHelper;

    private ListView mAgendasList;
    private AgendaCursorAdapter mAgendasAdapter;
    private FloatingActionButton mAddButton;


    public AgendasFragment() {
        // Required empty public constructor
    }

    public static AgendasFragment newInstance() {

        return new AgendasFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_agendas,
                container, false);

        // Referencias UI
        mAgendasList = (ListView) root.findViewById(R.id.agendas_list);
        mAgendasAdapter = new AgendaCursorAdapter(getActivity(), null);
        mAddButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);

        // Setup
        mAgendasList.setAdapter(mAgendasAdapter);

        // Eventos
        mAgendasList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor currentItem = (Cursor) mAgendasAdapter.getItem(i);
                String currentLawyerId = currentItem.getString(
                        currentItem.getColumnIndex(AgendaContract.AgendaEntry.ID));

                showDetailScreen(currentLawyerId);
            }
        });
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddScreen();
            }
        });


        getActivity().deleteDatabase(AgendaDbHelper.DATABASE_NAME);

        // Instancia de helper
        mAgendaDbHelper = new AgendaDbHelper(getActivity());

        // Carga de datos
        loadAgendas();

        return root;
    }

    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode, Intent data) {

        //La aparición del método onActivityResult()
        // se debe a que la lista que tendrá el fragmento debe
        // refrescarse en caso de que las screens de inserción o detalle
        // hayan producido una modificación de la tabla agenda
        if (Activity.RESULT_OK == resultCode) {
            switch (requestCode) {
                case AddEditAgendaActivity.REQUEST_ADD_AGENDA:
                    showSuccessfullSavedMessage();
                    loadAgendas();
                    break;
                case REQUEST_UPDATE_DELETE_AGENDA:
                    loadAgendas();
                    break;
            }
        }
    }

    private void loadAgendas() {

        new AgendasLoadTask().execute();
    }

    private void showSuccessfullSavedMessage() {
        Toast.makeText(getActivity(),
                "Agenda guardado correctamente", Toast.LENGTH_SHORT).show();
    }

    private void showAddScreen() {
        Intent intent = new Intent(getActivity(), AddEditAgendaActivity.class);
        startActivityForResult(intent, AddEditAgendaActivity.REQUEST_ADD_AGENDA);
    }

    private void showDetailScreen(String lawyerId) {
        Intent intent = new Intent(getActivity(),
                AgendaDetailActivity.class);
        intent.putExtra(AgendaActivity.EXTRA_AGENDAS_ID, lawyerId);
        startActivityForResult(intent, REQUEST_UPDATE_DELETE_AGENDA);
    }

    private class AgendasLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mAgendaDbHelper.getAllData();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                mAgendasAdapter.swapCursor(cursor);
            } else {
                // Mostrar empty state
            }
        }
    }

}
