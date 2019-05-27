package com.example.katherine.directoriomedico;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import com.example.katherine.directoriomedico.medicodetail.MedicoDetailFragment;

public class PacienteActivity extends AppCompatActivity {

    public static String EXTRA_PACIENTE_ID = "pacienteId";
    public static String EXTRA_MEDICO_ID = "medicoId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente);

        String medicoId = getIntent().getStringExtra(MedicoDetailFragment.ARG_MEDICO_ID);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        PacientesFragment fragment = (PacientesFragment)
            getSupportFragmentManager().findFragmentById(R.id.pacientes_container);

        if (fragment == null) {
            fragment = PacientesFragment.newInstance(medicoId);
            getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.pacientes_container, fragment)
                .commit();
        }

    }
}
