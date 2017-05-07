package com.herprogramacion.movielife.net;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by root on 9/04/17.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MovieLife";
    private static final int DATABASE_VERSION = 1;

    //Sentencia para crear la BD
    private static final String DATABASE_CREATE_WORD = "CREATE TABLE words (word text primary key, datetime text not null"
            + ");";
    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    //Este metodo es llamado cuando se crea la BD
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE_WORD);
    }

    //Metodo que se llama cada vez que se actualiza la BD
    //Incrementa la version
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion,
                          int newVersion) {
        Log.w(DataBaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS todo");
        onCreate(database);
    }
}
