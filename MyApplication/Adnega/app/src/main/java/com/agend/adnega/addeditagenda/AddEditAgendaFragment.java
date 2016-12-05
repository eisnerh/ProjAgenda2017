package com.agend.adnega.addeditagenda;


import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.agend.adnega.R;
import com.agend.adnega.bdData.Agenda;
import com.agend.adnega.bdData.AgendaDbHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddEditAgendaFragment extends Fragment {


    private static final String ARG_AGENDA_ID = "arg_agenda_id";

    private String mAgendId;

    private AgendaDbHelper mAgendaDbHelper;

    private FloatingActionButton mSaveButton;
    private TextInputEditText mTituloField;
    private TextInputEditText mLugarField;
    private TextInputEditText mHoraField;
    private TextInputEditText mDescField;
    private TextInputLayout mTituloLabel;
    private TextInputLayout mLugarLabel;
    private TextInputLayout mHoraLabel;
    private TextInputLayout mDescLabel;


    public AddEditAgendaFragment() {
        // Required empty public constructor
    }

    public static AddEditAgendaFragment newInstance(String agendaId) {
        AddEditAgendaFragment fragment = new AddEditAgendaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_AGENDA_ID, agendaId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAgendId = getArguments().getString(ARG_AGENDA_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_edit_agenda,
                container, false);

        // Referencias UI
        mSaveButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        mTituloField = (TextInputEditText) root.findViewById(R.id.et_name);
        mLugarField = (TextInputEditText) root.findViewById(R.id.et_phone_number);
        mHoraField = (TextInputEditText) root.findViewById(R.id.et_specialty);
        mDescField = (TextInputEditText) root.findViewById(R.id.et_bio);
        mTituloLabel = (TextInputLayout) root.findViewById(R.id.til_name);
        mLugarLabel = (TextInputLayout) root.findViewById(R.id.til_phone_number);
        mHoraLabel = (TextInputLayout) root.findViewById(R.id.til_specialty);
        mDescLabel = (TextInputLayout) root.findViewById(R.id.til_bio);

        // Eventos
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEditAgenda();
            }
        });

        mAgendaDbHelper = new AgendaDbHelper(getActivity());

        // Carga de datos
        if (mAgendId != null) {
            loadAgenda();
        }

        return root;
    }

    private void loadAgenda() {
        // AsyncTask
        new GetAgendaByIdTask().execute();
    }

    private void addEditAgenda() {
        boolean error = false;

        String titulo = mTituloField.getText().toString();
        String lugar = mLugarField.getText().toString();
        String hora = mHoraField.getText().toString();
        String desc = mDescField.getText().toString();

        if (TextUtils.isEmpty(titulo)) {
            mTituloLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (TextUtils.isEmpty(lugar)) {
            mLugarLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (TextUtils.isEmpty(hora)) {
            mHoraLabel.setError(getString(R.string.field_error));
            error = true;
        }


        if (TextUtils.isEmpty(desc)) {
            mDescLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (error) {
            return;
        }

        Agenda agenda = new Agenda(titulo, lugar, hora, desc, "");

        new AddEditAgendaTask().execute(agenda);

    }

    private void showAgendaScreen(Boolean requery) {
        if (!requery) {
            showAddEditError();
            getActivity().setResult(Activity.RESULT_CANCELED);
        } else {
            getActivity().setResult(Activity.RESULT_OK);
        }

        getActivity().finish();
    }

    private void showAddEditError() {
        Toast.makeText(getActivity(),
                "Error al agregar nueva información", Toast.LENGTH_SHORT).show();
    }

    private void showAgenda(Agenda agenda) {
        mTituloField.setText(agenda.getTitulo());
        mLugarField.setText(agenda.getLugar());
        mHoraField.setText(agenda.getHora());
        mDescField.setText(agenda.getDesc());
    }

    private void showLoadError() {
        Toast.makeText(getActivity(),
                "Error al editar agenda", Toast.LENGTH_SHORT).show();
    }

    private class GetAgendaByIdTask extends AsyncTask<Void,
            Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mAgendaDbHelper.getAgendaById(mAgendId);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToLast()) {
                showAgenda(new Agenda(cursor));
            } else {
                showLoadError();
                getActivity().setResult(Activity.RESULT_CANCELED);
                getActivity().finish();
            }
        }

    }

    private class AddEditAgendaTask extends AsyncTask<Agenda,
            Void, Boolean> {

        @Override
        protected Boolean doInBackground(Agenda... agendas) {
            if (mAgendId != null) {
                return mAgendaDbHelper.updateAgenda(agendas[0], mAgendId) > 0;

            } else {
                return mAgendaDbHelper.saveAgenda(agendas[0]) > 0;
            }

        }

        @Override
        protected void onPostExecute(Boolean result) {

            showAgendaScreen(result);
        }

    }

}