package android.tectii.edu.final2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class Principal extends AppCompatActivity {

    ListView lsVwListaDatos;
    //Crear base de datos
    SQLiteDatabase sqldbConnect;
    Cursor c1;
    DatabaseAdapter da;
    static final int RESULT = 1000;
    static final int CREAR = 2000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        sqldbConnect = openOrCreateDatabase("Restaurant", MODE_PRIVATE, null);
        try {
            sqldbConnect.execSQL("CREATE TABLE IF NOT EXISTS Restaurant(" +
                    "_id integer PRIMARY KEY autoincrement," +
                    "Nombre text," +
                    "Numero integer," +
                    "Hora   text);");
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        lsVwListaDatos = (ListView) findViewById(R.id.lsVwListaDatos);
        c1 = sqldbConnect.rawQuery("SELECT * FROM Restaurant", null);
        da = new DatabaseAdapter(this, c1);
        lsVwListaDatos.setAdapter(da);
        //Responder al click
        lsVwListaDatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), Descripcion.class);
                i.putExtra("id", id);
                startActivityForResult(i, RESULT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT || requestCode == CREAR) {
            if (resultCode == RESULT_OK) {
                c1 = sqldbConnect.rawQuery("SELECT * FROM Restaurant", null);
                da = new DatabaseAdapter(this, c1);
                lsVwListaDatos.setAdapter(da);
                Toast.makeText(getApplicationContext(), data.getStringExtra("result"), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Cancelado", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_create) {
            Intent i = new Intent(getApplicationContext(), Segunda.class);
            startActivityForResult(i, CREAR);
        }
        return super.onOptionsItemSelected(item);
    }
}
