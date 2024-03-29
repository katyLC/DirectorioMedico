package com.example.katherine.directoriomedico.addeditpaciente;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.example.katherine.directoriomedico.R;
import com.example.katherine.directoriomedico.medicodetail.MedicoDetailFragment;

public class AddEditPacienteActivity extends AppCompatActivity {

    public static final int REQUEST_ADD_PACIENTE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_paciente);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String medicoId = getIntent().getStringExtra(MedicoDetailFragment.ARG_MEDICO_ID);
        String pacienteId = getIntent().getStringExtra(MedicoDetailFragment.ARG_PACIENTE_ID);

        setTitle(pacienteId == null? "Añadir":"Editar");

        AddEditPacienteFragment fragment =
            (AddEditPacienteFragment) getSupportFragmentManager().findFragmentById(R.id.add_edit_paciente_container);

        if (fragment == null) {
            fragment = AddEditPacienteFragment.newInstance(medicoId, pacienteId);
            getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.add_edit_paciente_container, fragment)
                .commit();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            }
        });

    }

}
