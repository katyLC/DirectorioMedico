package com.example.katherine.directoriomedico.datos;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import com.example.katherine.directoriomedico.R;

public class PacientesCursorAdapter extends CursorAdapter {

    public PacientesCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.list_item_paciente, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        TextView tvNombre = (TextView) view.findViewById(R.id.tv_nombre);

        String nombre = cursor.getString(cursor.getColumnIndex(PacientesContract.PacienteEntry.NOMBRE));
        String apellido = cursor.getString(cursor.getColumnIndex(PacientesContract.PacienteEntry.APELLIDO));
        tvNombre.setText(String.format("%s %s", nombre, apellido));
    }
}
