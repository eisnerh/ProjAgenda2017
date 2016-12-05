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

        return root;
    }

    private void loadAgenda() {

        new GetAgendaByIdTask().execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                showEditScreen();
                break;
            case R.id.action_delete:
                new DeleteAgendaTask().execute();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode, Intent data) {
        if (requestCode == AgendasFragment.REQUEST_UPDATE_DELETE_AGENDA) {
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
                "Error al cargar información", Toast.LENGTH_SHORT).show();
    }

    private void showDeleteError() {
        Toast.makeText(getActivity(),
                "Error al eliminar información", Toast.LENGTH_SHORT).show();
    }

    private class GetAgendaByIdTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mAgendaDbHelper.getAgendaById(mAgendaId);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToLast()) {
                showAgenda(new Agenda(cursor));
            } else {
                showLoadError();
            }
        }

    }

    private class DeleteAgendaTask extends AsyncTask<Void, Void, Integer> {

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

