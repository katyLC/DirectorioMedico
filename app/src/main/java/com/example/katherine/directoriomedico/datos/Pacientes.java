package com.example.katherine.directoriomedico.datos;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.UUID;

public class Pacientes {
    private String id;
    private String nombre;
    private String apellido;
    private String edad;
    private String sexo;
    private String enfermedad;
    private String idMedico;

    public Pacientes(String nombre, String apellido, String edad, String sexo, String enfermedad,
                     String idMedico) {
        this.id = UUID.randomUUID().toString();
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.sexo = sexo;
        this.enfermedad = enfermedad;
        this.idMedico = idMedico;
    }

    public Pacientes(Cursor cursor) {
        id = cursor.getString(cursor.getColumnIndex
            (PacientesContract.PacienteEntry.ID));
        nombre = cursor.getString(cursor.getColumnIndex
            (PacientesContract.PacienteEntry.NOMBRE));
        apellido = cursor.getString(cursor.getColumnIndex
            (PacientesContract.PacienteEntry.APELLIDO));
        edad = cursor.getString(cursor.getColumnIndex
            (PacientesContract.PacienteEntry.EDAD));
        sexo = cursor.getString(cursor.getColumnIndex
            (PacientesContract.PacienteEntry.SEXO));
        enfermedad = cursor.getString(cursor.getColumnIndex
            (PacientesContract.PacienteEntry.ENFERMEDAD));
        idMedico = cursor.getString(cursor.getColumnIndex((PacientesContract.PacienteEntry.IDMEDICO)));
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(PacientesContract.PacienteEntry.ID, id);
        values.put(PacientesContract.PacienteEntry.NOMBRE, nombre);
        values.put(PacientesContract.PacienteEntry.APELLIDO, apellido);
        values.put(PacientesContract.PacienteEntry.EDAD, edad);
        values.put(PacientesContract.PacienteEntry.SEXO, sexo);
        values.put(PacientesContract.PacienteEntry.ENFERMEDAD, enfermedad);
        values.put(PacientesContract.PacienteEntry.IDMEDICO, idMedico);
        return values;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getEdad() {
        return edad;
    }

    public String getSexo() {
        return sexo;
    }

    public String getEnfermedad() {
        return enfermedad;
    }

    public String getIdMedico() {
        return idMedico;
    }
}
