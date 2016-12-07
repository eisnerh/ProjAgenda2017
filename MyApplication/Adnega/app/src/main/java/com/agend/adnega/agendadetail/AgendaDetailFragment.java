package com.agend.adnega.agendadetail;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.agend.adnega.R;
import com.agend.adnega.addeditagenda.AddEditAgendaActivity;
import com.agend.adnega.agendas.AgendaActivity;
import com.agend.adnega.agendas.AgendasFragment;
import com.agend.adnega.bdData.Agenda;
import com.agend.adnega.bdData.AgendaDbHelper;
import com.bumptech.glide.Glide;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AgendaDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * //Hecho por Eisner López Acevedo
 */
public class AgendaDetailFragment extends Fragment {
    private static final String ARG_AGENDA_ID = "agendaId";

    private String mAgendaId;

    private CollapsingToolbarLayout mCollapsingView;
    private ImageView mAvatar;
    private TextView mLugar;
    private TextView mHora;
    private TextView mDesc;

    private AgendaDbHelper mAgendaDbHelper;


    public AgendaDetailFragment() {
        // Required empty public constructor
    }

    public static AgendaDetailFragment newInstance(String lawyerId) {
        AgendaDetailFragment fragment = new AgendaDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_AGENDA_ID, lawyerId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mAgendaId = getArguments().getString(ARG_AGENDA_ID);
        }

        //Habilita la contribución de AgendaDetailFragment a la Toolbar
        // con el método setHasOptionsMenu() con el valor.
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup viewGroup,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_agenda_detail,
                viewGroup, false);
        mCollapsingView = (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);
        mAvatar = (ImageView) getActivity().findViewById(R.id.iv_avatar);
        mLugar = (TextView) root.findViewById(R.id.tv_lugar);
        mHora = (TextView) root.findViewById(R.id.tv_hora);
        mDesc = (TextView) root.findViewById(R.id.tv_bio);

        mAgendaDbHelper = new AgendaDbHelper(getActivity());

        loadAgenda();

        // través de un método loadAgenda().

        return root;
    }

    private void loadAgenda() {

        new GetAgendaByIdTask().execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // En el abre una estructura switch y procesa los casos de edición y eliminación.
        switch (item.getItemId()) {
            case R.id.action_edit:
                showEditScreen();
                break;
            case R.id.action_delete:
                // Para manejar el evento de borrado, crea una nueva tarea asíncrona que llame a DeleteAgendaTask.


                new DeleteAgendaTask().execute();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode, Intent data) {
        //Se requiere onActivityResult() ya que cuando se intente eliminar o editar la
        // agenda se estará pendiente de cambios en la base de datos,
        // así que podremos reportar a AgendasFragment la necesidad de actualizar la lista.
        if (requestCode == AgendasFragment.REQUEST_UPDATE_DELETE_AGENDA) {

            //REQUEST_UPDATE_DELETE_AGENDA es una constante entera que representa la vía
            // de comunicación entre la screen de agendas y la de detalles.
            if (resultCode == Activity.RESULT_OK) {
                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();
            }
        }
    }

    private void showAgenda(Agenda agenda) {
        mCollapsingView.setTitle(agenda.getTitulo());
        Glide.with(this)
                .load(Uri.parse("file:///android_asset/" + agenda.getAvatarUri()))
                .centerCrop()
                .into(mAvatar);
        mLugar.setText(agenda.getLugar());
        mHora.setText(agenda.getHora());
        mDesc.setText(agenda.getDesc());
    }

    private void showEditScreen() {
        Intent intent = new Intent(getActivity(), AddEditAgendaActivity.class);
        intent.putExtra(AgendaActivity.EXTRA_AGENDAS_ID, mAgendaId);
        startActivityForResult(intent, AgendasFragment.REQUEST_UPDATE_DELETE_AGENDA);
    }

    private void showAgendasScreen(boolean requery) {
        if (!requery) {
            showDeleteError();
        }
        getActivity().setResult(requery ? Activity.RESULT_OK : Activity.RESULT_CANCELED);
        getActivity().finish();
    }

    private void showLoadError() {
        Toast.makeText(getActivity(),
                // De lo contrario muestra un error:
                "Error al cargar información", Toast.LENGTH_SHORT).show();
    }

    private void showDeleteError() {
        Toast.makeText(getActivity(),
                // De lo contrario muestra un error:
                "Error al eliminar información", Toast.LENGTH_SHORT).show();
    }

    private class GetAgendaByIdTask extends AsyncTask<Void, Void, Cursor> {

        // Carga el detalle de la agenda con el método getLawyerById() con una tarea asíncrona.




        @Override
        protected Cursor doInBackground(Void... voids) {
            return mAgendaDbHelper.getAgendaById(mAgendaId);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            // En onPostExecute() extrae cada uno de los valores de la columna y asígnalos
            // en los views de texto para poblar el detalle.
            if (cursor != null && cursor.moveToLast()) {
                showAgenda(new Agenda(cursor));
            } else {
                // En postExecute() cierra la actividad de detalle con un resultado favorable
                // hacia la actividad de agendas en caso de que la eliminación fuese exitosa.
                // De lo contrario muestra un error:
                showLoadError();
            }
        }

    }

    private class DeleteAgendaTask extends AsyncTask<Void, Void, Integer> {

        //En doInBackground() llama a LawyersDbHelper.deleteLawyer().
        @Override
        protected Integer doInBackground(Void... voids) {
            return mAgendaDbHelper.deleteAgenda(mAgendaId);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            showAgendasScreen(integer > 0);
        }

    }

}

