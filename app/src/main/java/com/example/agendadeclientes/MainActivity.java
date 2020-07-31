package com.example.agendadeclientes;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.agendadeclientes.BaseDeDatos.FeedReaderDbHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView lstDatos;
    private ArrayAdapter<String> adaptador;
    private ArrayList<String> clientes;

    private SQLiteDatabase conexion;
    private FeedReaderDbHelper feedReaderDbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this, NewCliente.class);
                startActivityForResult(it, 0);
            }
        });

        actualizar();
    }

    private void actualizar()
    {
        lstDatos = (ListView) findViewById(R.id.LstDatos);
        clientes = new ArrayList<String>();

        try
        {
            /*   //ESTE CÓDIGO FUE CAMBIADO POR UNO QUE SIGA BUENAS PRACTICAS DE PROGRAMACIÓN,
                 //EL CUAL ESTÁ UBICADO DESPUÉS DE LA PARTE COMENTADA

            feedReaderDbHelper = new FeedReaderDbHelper(this);
            conexion = feedReaderDbHelper.getWritableDatabase();
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM CLIENTE");
            String sNombre;
            String sTelefono;

            Cursor resultado = conexion.rawQuery(sql.toString(), null);

            if (resultado.getCount() > 0)
            {
                resultado.moveToFirst();
                do
                {
                    sNombre = resultado.getString(resultado.getColumnIndex("NOMBRE"));
                    sTelefono = resultado.getString(resultado.getColumnIndex("TELEFONO"));
                    clientes.add(sNombre + ": " + sTelefono);
                }
                while (resultado.moveToNext());
            }

            adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, clientes);
            lstDatos.setAdapter(adaptador);

            */



            feedReaderDbHelper = new FeedReaderDbHelper(this);

            SQLiteDatabase db = feedReaderDbHelper.getReadableDatabase();

            String[] projection = {
                    FeedReaderDbHelper.FeedReaderContract.FeedEntry.COLUMN_NAME_NOMBRE,
                    FeedReaderDbHelper.FeedReaderContract.FeedEntry.COLUMN_NAME_TELEFONO
            };

            String sortOrder =
                    FeedReaderDbHelper.FeedReaderContract.FeedEntry.COLUMN_NAME_DIRECCION + " DESC";

            Cursor cursor = db.query(
                    FeedReaderDbHelper.FeedReaderContract.FeedEntry.TABLE_NAME,
                    projection,
                    null,
                    null,
                    null,
                    null,
                    sortOrder
            );


            while(cursor.moveToNext()) {
                String nombre = cursor.getString(
                        cursor.getColumnIndexOrThrow(FeedReaderDbHelper.FeedReaderContract.FeedEntry.COLUMN_NAME_NOMBRE));
                String telefono = cursor.getString(
                        cursor.getColumnIndexOrThrow(FeedReaderDbHelper.FeedReaderContract.FeedEntry.COLUMN_NAME_TELEFONO));

                clientes.add(nombre + ": "+ telefono);

            }

            adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, clientes);

            lstDatos.setAdapter(adaptador);
            cursor.close();
        }
        catch (Exception ex)
        {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Aviso");
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        actualizar();
    }
}