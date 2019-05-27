package com.example.katherine.directoriomedico.pacientedetail;


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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.katherine.directoriomedico.PacientesFragment;
import com.example.katherine.directoriomedico.R;
import com.example.katherine.directoriomedico.addeditpaciente.AddEditPacienteActivity;
import com.example.katherine.directoriomedico.datos.MedicosDbHelper;
import com.example.katherine.directoriomedico.datos.Pacientes;

/**
 * A simple {@link Fragment} subclass.
 */
public class PacienteDetailFragment extends Fragment {
    public static final String ARG_PACIENTE_ID = "pacienteId";
    public static final String ARG_MEDICO_ID = "medicoId";
    private String mPacienteId;
    private String mMedicoId;

    private TextView tvNombres;
    private TextView tvApellidos;
    private TextView tvEdad;
    private TextView tvSexo;
    private TextView tvEnfermeddad;
    private Button btEditar;
    private Button btEliminar;

    private MedicosDbHelper mMedicosDBHelper;


    public PacienteDetailFragment() {
        // Required empty public constructor
    }

    public static PacienteDetailFragment newInstance(String pacienteId, String medicoId) {
        PacienteDetailFragment fragment = new PacienteDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PACIENTE_ID, pacienteId);
        args.putString(ARG_MEDICO_ID, medicoId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mPacienteId = getArguments().getString(ARG_PACIENTE_ID);
            mMedicoId = getArguments().getString(ARG_MEDICO_ID);
        }

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_paciente_detail, container, false);

        tvNombres = view.findViewById(R.id.tv_nombres);
        tvApellidos = view.findViewById(R.id.tv_apellidos);
        tvEdad = view.findViewById(R.id.tv_edad);
        tvSexo = view.findViewById(R.id.tv_sexo);
        tvEnfermeddad = view.findViewById(R.id.tv_enfermeddad);
        btEditar = view.findViewById(R.id.bt_editar);
        btEliminar = view.findViewById(R.id.bt_eliminar);

        mMedicosDBHelper = new MedicosDbHelper(getActivity());

        btEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditScreen();
            }
        });

        btEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DeletePacienteTask().execute();
            }
        });

        loadPaciente();

        return view;
    }

    private void showLoadError() {
        Toast.makeText(getActivity(), "Error al cargar información", Toast.LENGTH_LONG).show();
    }

    private void showPaciente(Pacientes pacientes) {
        tvNombres.setText(pacientes.getNombre());
        tvApellidos.setText(pacientes.getApellido());
        tvEdad.setText(pacientes.getEdad());
        tvSexo.setText(pacientes.getSexo());
        tvEnfermeddad.setText(pacientes.getEnfermedad());
    }

    private class GetPacienteByIdTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... params) {
            return mMedicosDBHelper.getPacienteById(mPacienteId);
        }

        @Override
        protected void onPostExecute(Cursor c) {
            if (c != null && c.moveToLast()) {
                showPaciente(new Pacientes(c));
            } else {
                showLoadError();
            }
        }
    }

    private void loadPaciente() {
        new GetPacienteByIdTask().execute();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PacientesFragment.REQUEST_UPDATE_DELETE_PACIENTE) {
            if (resultCode == Activity.RESULT_OK) {
                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();
            }
        }
    }

    private void showDeleteError() {
        Toast.makeText(getActivity(), "Error al eliminar paciente", Toast.LENGTH_SHORT).show();
    }

    private void showPacienteScreen(boolean requery) {
        if (!requery) {
            showDeleteError();
        }
        getActivity().setResult(requery ? Activity.RESULT_OK : Activity.RESULT_CANCELED);
        getActivity().finish();
    }

    private class DeletePacienteTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... params) {
            return mMedicosDBHelper.deletePaciente(mPacienteId);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            showPacienteScreen(integer > 0);
        }
    }

    private void showEditScreen() {
        Intent intent = new Intent(getActivity(), AddEditPacienteActivity.class);
        intent.putExtra(PacienteDetailFragment.ARG_PACIENTE_ID, mPacienteId);
        intent.putExtra(PacienteDetailFragment.ARG_MEDICO_ID, mMedicoId);
        startActivityForResult(intent, PacientesFragment.REQUEST_UPDATE_DELETE_PACIENTE);
    }
}
