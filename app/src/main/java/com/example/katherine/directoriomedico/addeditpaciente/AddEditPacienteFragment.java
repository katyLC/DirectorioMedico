package com.example.katherine.directoriomedico.addeditpaciente;


import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.katherine.directoriomedico.R;
import com.example.katherine.directoriomedico.datos.MedicosDbHelper;
import com.example.katherine.directoriomedico.datos.Pacientes;

public class AddEditPacienteFragment extends Fragment {

    private static final String ARG_MEDICO_ID = "arg_medico_id";
    private static final String ARG_PACIENTE_ID = "arg_paciente_id";
    private String mMedicoId;
    private String mPacienteId;
    private MedicosDbHelper mMedicosDBHelper;
    private FloatingActionButton mSaveButton;
    private TextInputLayout tilNombre;
    private TextInputEditText etNombre;
    private TextInputLayout tilApellido;
    private TextInputEditText etApellido;
    private TextInputLayout tilEdad;
    private TextInputEditText etEdad;
    private TextInputLayout tilSexo;
    private TextInputEditText etSexo;
    private TextInputLayout tilEnfermedad;
    private TextInputEditText etEnfemedad;

    public AddEditPacienteFragment() {
        // Required empty public constructor
    }

    public static AddEditPacienteFragment newInstance(String medicoId, String pacienteId) {
        AddEditPacienteFragment fragment = new AddEditPacienteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MEDICO_ID, medicoId);
        args.putString(ARG_PACIENTE_ID, pacienteId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            mMedicoId = getArguments().getString(ARG_MEDICO_ID);
            mPacienteId = getArguments().getString(ARG_PACIENTE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_edit_paciente, container, false);

        mSaveButton = getActivity().findViewById(R.id.fab);
        tilNombre = view.findViewById(R.id.til_nombre);
        etNombre = view.findViewById(R.id.et_nombre);
        tilApellido = view.findViewById(R.id.til_apellido);
        etApellido = view.findViewById(R.id.et_apellido);
        tilEdad = view.findViewById(R.id.til_edad);
        etEdad = view.findViewById(R.id.et_edad);
        tilSexo = view.findViewById(R.id.til_sexo);
        etSexo = view.findViewById(R.id.et_sexo);
        tilEnfermedad = view.findViewById(R.id.til_enfermedad);
        etEnfemedad = view.findViewById(R.id.et_enfemedad);

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEditPaciente();
            }
        });

        mMedicosDBHelper = new MedicosDbHelper(getActivity());

        if (mPacienteId != null) {
            loadPaciente();
        }
        return view;

    }

    private void loadPaciente(){
        new GetPacienteByIdTask().execute();
    }

    private void showPaciente(Pacientes pacientes){
        etNombre.setText(pacientes.getNombre());
        etApellido.setText(pacientes.getApellido());
        etEdad.setText(pacientes.getEdad());
        etEnfemedad.setText(pacientes.getEnfermedad());
        etSexo.setText(pacientes.getSexo());
    }

    private class GetPacienteByIdTask extends AsyncTask<Void,Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... params) {
            return mMedicosDBHelper.getPacienteById(mPacienteId);
        }
        @Override
        protected void onPostExecute(Cursor c){
            if(c!=null && c.moveToLast()){
                showPaciente(new Pacientes(c));
            }
        }
    }

    private class AddEditPacienteTask extends AsyncTask<Pacientes,Void,Boolean>{

        @Override
        protected Boolean doInBackground(Pacientes... params) {
            if(mPacienteId != null){
                return mMedicosDBHelper.updatePaciente(params[0],mPacienteId)>0;
            }else{
                return mMedicosDBHelper.savePaciente(params[0])>0;
            }
        }

        @Override
        protected void onPostExecute(Boolean result){
            showPacientesScreen(result);
        }
    }

    private void showPacientesScreen(Boolean requery){
        if(!requery){
            showAddeditError();
            getActivity().setResult(Activity.RESULT_CANCELED);
        }else{
            getActivity().setResult(Activity.RESULT_OK);
        }
        getActivity().finish();
    }

    private void showAddeditError(){
        Toast.makeText(getActivity(),"error",Toast.LENGTH_SHORT).show();
    }

    private void addEditPaciente(){
        boolean error = false;
        String nombre = etNombre.getText().toString();
        String apellido = etApellido.getText().toString();
        String edad = etEdad.getText().toString();
        String sexo = etSexo.getText().toString();
        String enfermedad = etEnfemedad.getText().toString();

        if(TextUtils.isEmpty(nombre)){
            tilNombre.setError(getString(R.string.field_error));
            error = true;
        }
        if(TextUtils.isEmpty(apellido)){
            tilApellido.setError(getString(R.string.field_error));
            error = true;
        }
        if(TextUtils.isEmpty(edad)){
            tilEdad.setError(getString(R.string.field_error));
            error = true;
        }
        if(TextUtils.isEmpty(enfermedad)){
            tilEnfermedad.setError(getString(R.string.field_error));
            error = true;
        }

        if(TextUtils.isEmpty(sexo)){
            tilSexo.setError(getString(R.string.field_error));
            error = true;
        }

        if(error){
            return;
        }
        Pacientes pacientes = new Pacientes(nombre,apellido, edad, sexo, enfermedad, mMedicoId);
        new AddEditPacienteTask().execute(pacientes);
    }
}
