package com.example.agendadeclientes.BaseDeDatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.widget.Toast;

public class FeedReaderDbHelper extends SQLiteOpenHelper
{
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedReaderContract.FeedEntry.TABLE_NAME + " (" +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_NOMBRE+ " TEXT," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_DIRECCION+ " TEXT," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_EMAIL+ " TEXT," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_TELEFONO+ " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedReaderContract.FeedEntry.TABLE_NAME;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";


    public FeedReaderDbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        System.out.println(SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1)
    {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public static final class FeedReaderContract
    {
        private FeedReaderContract() {}

        public static class FeedEntry implements BaseColumns
        {
            public static final String TABLE_NAME = "CLIENTE";
            public static final String COLUMN_NAME_NOMBRE = "NOMBRE";
            public static final String COLUMN_NAME_DIRECCION = "DIRECCION";
            public static final String COLUMN_NAME_EMAIL = "EMAIL";
            public static final String COLUMN_NAME_TELEFONO = "TELEFONO";
        }
    }
}
