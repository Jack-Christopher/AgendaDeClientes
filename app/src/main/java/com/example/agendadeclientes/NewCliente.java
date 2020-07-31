package com.example.agendadeclientes;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.agendadeclientes.BaseDeDatos.FeedReaderDbHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NewCliente extends AppCompatActivity {

    private EditText editNombre;
    private EditText editDireccion;
    private EditText editEmail;
    private EditText editTelefono;

    private SQLiteDatabase conexion;
    private FeedReaderDbHelper feedReaderDbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_cliente);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editNombre = (EditText) findViewById(R.id.editTextNombre);
        editDireccion = (EditText) findViewById(R.id.editTextDireccion);
        editEmail = (EditText) findViewById(R.id.editTextEmail);
        editTelefono = (EditText) findViewById(R.id.editTextTelefono);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater  = getMenuInflater();
        inflater.inflate(R.menu.menu_new_cliente, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.action_OK:
                if(bCamposCorrectos())
                {
                    try
                    {
                        /*  //ESTE CÓDIGO FUE CAMBIADO POR UNO QUE SIGA BUENAS PRACTICAS DE PROGRAMACIÓN,
                            //EL CUAL ESTÁ UBICADO DESPUÉS DE LA PARTE COMENTADA

                        feedReaderDbHelper = new FeedReaderDbHelper(this);
                        conexion = feedReaderDbHelper.getWritableDatabase();
                        StringBuilder sql = new StringBuilder();
                        sql.append("INSERT INTO CLIENTE (NOMBRE, DIRECCION, EMAIL, TELEFONO) VALUES ('");
                        sql.append(editNombre.getText().toString().trim() + "', '");
                        sql.append(editDireccion.getText().toString().trim() + "', '");
                        sql.append(editEmail.getText().toString().trim() + "', '");
                        sql.append(editTelefono.getText().toString().trim() + "' )");

                        conexion.execSQL(sql.toString());
                        conexion.close();

                        finish();
                        */

                        feedReaderDbHelper = new FeedReaderDbHelper(this);
                        SQLiteDatabase db = feedReaderDbHelper.getWritableDatabase();

                        ContentValues values = new ContentValues();
                        values.put(FeedReaderDbHelper.FeedReaderContract.FeedEntry.COLUMN_NAME_NOMBRE, editNombre.getText().toString());
                        values.put(FeedReaderDbHelper.FeedReaderContract.FeedEntry.COLUMN_NAME_DIRECCION, editDireccion.getText().toString());
                        values.put(FeedReaderDbHelper.FeedReaderContract.FeedEntry.COLUMN_NAME_EMAIL, editEmail.getText().toString());
                        values.put(FeedReaderDbHelper.FeedReaderContract.FeedEntry.COLUMN_NAME_TELEFONO, editTelefono.getText().toString());

                        long newRowId = db.insert(FeedReaderDbHelper.FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
                        finish();
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
                else
                {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                    dlg.setTitle("Aviso");
                    dlg.setMessage("Existen campos vacíos");
                    dlg.setNeutralButton("OK", null);
                    dlg.show();
                }

                break;

            case R.id.action_cancelar:
                finish();

                break;
        }

        return super.onOptionsItemSelected(item);
    }


    private  boolean bCamposCorrectos()
    {
        boolean res = true;
        if (editNombre.getText().toString().trim().isEmpty())
        {
            editNombre.requestFocus();
            res = false;
        }
        else if (editDireccion.getText().toString().trim().isEmpty())
        {
            editDireccion.requestFocus();
            res = false;
        }
        else if (editEmail.getText().toString().trim().isEmpty())
        {
            editEmail.requestFocus();
            res = false;
        }
        else if (editTelefono.getText().toString().trim().isEmpty())
        {
            editTelefono.requestFocus();
            res = false;
        }

        return res;
    }
}