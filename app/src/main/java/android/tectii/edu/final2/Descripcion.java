package android.tectii.edu.final2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Descripcion extends AppCompatActivity {

    EditText edtTxtNombre, edtTxtNumero, edtTxtHora;
    Button btnActualizar, btnEliminar;
    Intent i2;
    SQLiteDatabase sqldbConnect;
    ContentValues cv = new ContentValues();
    String nombre, numero, hora;
    long id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descripcion);
        edtTxtNombre = (EditText) findViewById(R.id.edtTxtNombre);
        edtTxtNumero = (EditText) findViewById(R.id.edtTxtNumero);
        edtTxtHora = (EditText) findViewById(R.id.edtTxtHora);
        btnActualizar = (Button) findViewById(R.id.btnActualizar);
        btnEliminar = (Button) findViewById(R.id.btnEliminar);
        sqldbConnect = openOrCreateDatabase("Restaurant", MODE_PRIVATE, null);
        i2 = getIntent();
        id = i2.getLongExtra("id", 0);
        Cursor c1 = sqldbConnect.rawQuery("SELECT * FROM Restaurant WHERE _id = ?", new String[]{id + ""});
        c1.moveToFirst();
        edtTxtNombre.setText(c1.getString(c1.getColumnIndex("Nombre")));
        edtTxtNumero.setText(c1.getString(c1.getColumnIndex("Numero")));
        edtTxtHora.setText(c1.getString(c1.getColumnIndex("Hora")));
        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre = edtTxtNombre.getText().toString();
                numero = edtTxtNumero.getText().toString();
                hora = edtTxtNumero.getText().toString();
                cv.put("Nombre", nombre);
                cv.put("Numero", numero);
                cv.put("Hora", hora);
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sqldbConnect.update("Restaurant", cv, "_id = ?", new String[]{"" + id});
                        i2.putExtra("result", "Datos Actualizados");
                        setResult(RESULT_OK, i2);
                        finish();
                    }
                });
                t.start();
            }
        });
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sqldbConnect.delete("Restaurant", "_id = ?", new String[]{"" + id});
                        i2.putExtra("result", "Datos Eliminados");
                        setResult(RESULT_OK, i2);
                        finish();
                    }
                });
                t.start();
            }
        });
    }
}
