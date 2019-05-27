package com.example.katherine.directoriomedico.pacientedetail;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.example.katherine.directoriomedico.PacienteActivity;
import com.example.katherine.directoriomedico.R;

public class PacienteDetailActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String id = getIntent().getStringExtra(PacienteActivity.EXTRA_PACIENTE_ID);
        String medicoId = getIntent().getStringExtra(PacienteActivity.EXTRA_MEDICO_ID);

        PacienteDetailFragment fragment =
            (PacienteDetailFragment)getSupportFragmentManager().findFragmentById(R.id.paciente_detail_container);

        if(fragment == null){
            fragment = PacienteDetailFragment.newInstance(id, medicoId);
            getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.paciente_detail_container,fragment)
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
