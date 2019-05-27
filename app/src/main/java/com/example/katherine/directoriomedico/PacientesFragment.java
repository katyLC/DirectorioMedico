package com.example.katherine.directoriomedico;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.example.katherine.directoriomedico.addeditmedico.AddEditMedicoActivity;
import com.example.katherine.directoriomedico.addeditpaciente.AddEditPacienteActivity;
import com.example.katherine.directoriomedico.datos.MedicosDbHelper;
import com.example.katherine.directoriomedico.datos.PacientesContract;
import com.example.katherine.directoriomedico.datos.PacientesCursorAdapter;
import com.example.katherine.directoriomedico.pacientedetail.PacienteDetailActivity;


public class PacientesFragment extends Fragment {
    private static final String ARG_MEDICO_ID = "arg_medico_id";
    private String mMedicoId;

    ListView mPacientesList;
    PacientesCursorAdapter mPacientesAdatper;
    MedicosDbHelper mMedicosDbHelper;

    public static final int REQUEST_UPDATE_DELETE_PACIENTE = 2;

    public PacientesFragment() {
        // Required empty public constructor
    }

    public static PacientesFragment newInstance(String medicoId) {
        PacientesFragment fragment = new PacientesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MEDICO_ID, medicoId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMedicoId = getArguments().getString(ARG_MEDICO_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_pacientes, container, false);


        mPacientesList = (ListView) root.findViewById(R.id.pacientes_list);
        mPacientesAdatper = new PacientesCursorAdapter(getActivity(), null, 0);

        mPacientesList.setAdapter(mPacientesAdatper);

        mMedicosDbHelper = new MedicosDbHelper(getActivity());

        mPacientesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor currentItem = (Cursor) mPacientesAdatper.getItem(position);
                String currentPacienteId =
                    currentItem.getString(currentItem.getColumnIndex(PacientesContract.PacienteEntry.ID));
                showDetailScreen(currentPacienteId);
            }
        });

        loadPacientes();
        return root;
    }

    private void showAddScreen() {
        Intent intent = new Intent(getActivity(), AddEditPacienteActivity.class);
        startActivityForResult(intent, AddEditPacienteActivity.REQUEST_ADD_PACIENTE);
    }

    private void showDetailScreen(String pacienteId) {
        Intent intent = new Intent(getActivity(), PacienteDetailActivity.class);
        intent.putExtra(PacienteActivity.EXTRA_PACIENTE_ID, pacienteId);
        intent.putExtra(PacienteActivity.EXTRA_MEDICO_ID, mMedicoId);
        startActivityForResult(intent, REQUEST_UPDATE_DELETE_PACIENTE);
    }

    private void loadPacientes() {
        new MedicosLoadTask().execute();
    }

    private class MedicosLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... params) {
            return mMedicosDbHelper.getAllPacientesByIdMedico(mMedicoId);
            //            return mMedicosDbHelper.getAllPacientes();
        }

        @Override
        protected void onPostExecute(Cursor c) {
            if (c != null && c.getCount() > 0) {
                mPacientesAdatper.swapCursor(c);
            } else {
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Activity.RESULT_OK == resultCode) {
            switch (requestCode) {
                case AddEditMedicoActivity.REQUEST_ADD_MEDICO:
                    showSuccessfullSavedMessage();
                    loadPacientes();
                    break;
                case REQUEST_UPDATE_DELETE_PACIENTE:
                    loadPacientes();
                    break;
            }
        }
    }

    private void showSuccessfullSavedMessage() {
        Toast.makeText(getActivity(), "Paciente guardado correctamente", Toast.LENGTH_SHORT).show();
    }
}
