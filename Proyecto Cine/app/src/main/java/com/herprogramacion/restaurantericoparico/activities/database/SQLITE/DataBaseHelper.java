package com.herprogramacion.restaurantericoparico.activities.database.SQLITE;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "applicationdata";
    private static final int DATABASE_VERSION = 1;

    //Sentencia para crear la BD
    private static final String DATABASE_CREATE = "create table todo (_id integer primary key autoincrement, "
            + "category text not null, summary text not null, description text not null);";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Este metodo es llamado cuando se crea la BD
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
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
