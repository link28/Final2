package android.tectii.edu.final2;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

public class Segunda extends AppCompatActivity {

    SQLiteDatabase sqldbConnect;
    EditText edtTxtNombre, edtTxtNumero;
    TimePicker tp;
    Button btnCrear;
    Intent i2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda);
        i2 = getIntent();
        sqldbConnect = openOrCreateDatabase("Restaurant", MODE_PRIVATE, null);
        edtTxtNombre = (EditText) findViewById(R.id.edtTxtNombre);
        edtTxtNumero = (EditText) findViewById(R.id.edtTxtNumero);
        tp = (TimePicker) findViewById(R.id.timePicker2);
        btnCrear = (Button) findViewById(R.id.btnCrear);
        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] datos = new String[]{
                        edtTxtNombre.getText().toString(),
                        edtTxtNumero.getText().toString(),
                        tp.getHour() + ":" + tp.getMinute()
                };
                sqldbConnect.execSQL("INSERT INTO Restaurant(Nombre, Numero, Hora) VALUES (?,?,?);", datos);
                i2.putExtra("result", "Registro guardado exitosamente");
                setResult(RESULT_OK, i2);
                finish();
            }
        });
    }
}
