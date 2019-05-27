package com.example.katherine.directoriomedico.datos;

import android.provider.BaseColumns;

public class PacientesContract {
    public static abstract class PacienteEntry implements BaseColumns {
        public static final String TABLE_NAME = "pacientes";
        public static final String ID = "id";
        public static final String NOMBRE = "nombres";
        public static final String APELLIDO = "apellido";
        public static final String EDAD = "edad";
        public static final String SEXO = "sexo";
        public static final String ENFERMEDAD = "enfermedad";
        public static final String IDMEDICO = "idMedico";
    }
}
