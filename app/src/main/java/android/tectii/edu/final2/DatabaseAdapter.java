package android.tectii.edu.final2;


import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class DatabaseAdapter extends CursorAdapter {
    public DatabaseAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.my_list, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView txtVwNombre = (TextView) view.findViewById(R.id.txtVwNombre);
        TextView txtVwNumero = (TextView) view.findViewById(R.id.txtVwNumero);
        TextView txtVwHora = (TextView) view.findViewById(R.id.txtVwHora);
        String nombre = cursor.getString(cursor.getColumnIndexOrThrow("Nombre"));
        String numero = cursor.getString(cursor.getColumnIndexOrThrow("Numero"));
        String hora = cursor.getString(cursor.getColumnIndexOrThrow("Hora"));
        txtVwNombre.setText(nombre);
        txtVwNumero.setText("Numero de reserva: " + numero);
        txtVwHora.setText("Hora: " + hora);
    }
}
