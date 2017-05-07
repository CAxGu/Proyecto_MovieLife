package com.herprogramacion.movielife.net;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBAdapter {

    //Campos de la BD words
    private static final String KEY_WORD = "word";
    private static final String KEY_DATETIME = "datetime";
    private static final String DATEBASE_TABLE_WORDS = "words";

    private Context context;
    private SQLiteDatabase database;
    private DataBaseHelper dbHelper;

    public DBAdapter(Context context) {
        this.context = context;
    }

    public DBAdapter open() throws SQLException {
        dbHelper = new DataBaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }


    public boolean createTodo_words(String word) {
        String query = "INSERT INTO " + DATEBASE_TABLE_WORDS + " Values ('"+word+"',datetime('now'));";
        //comprueba si ya existe , Si existe se eliminara y se le asignara un datetime nuevo
        boolean exist = find_word(word);
        if(exist)
        {
            deleteTodo_word(word);
        }
        database.execSQL(query);
        boolean row = find_word(word);
        return !row;
    }



    //Borra la tarea

    public boolean deleteTodo_word(String word) {
        return database.delete(DATEBASE_TABLE_WORDS, KEY_WORD + "=" + "'"+word+"'", null) > 0;
    }


    //Returna un Cursor que contiene todos los items
    public Cursor fetchAllTodos_word(String word) {
        return database.query(DATEBASE_TABLE_WORDS, new String[] {KEY_WORD, KEY_DATETIME},KEY_WORD +" LIKE '"+word+"%' ORDER BY "+KEY_DATETIME+" DESC", null, null, null,
                null, null);
    }

    private boolean find_word(String word) throws SQLException{
        Cursor res = database.rawQuery("SELECT * FROM "+DATEBASE_TABLE_WORDS+" WHERE word ='"+word+"'",null);
        Log.v("SQL",String.valueOf(res.getCount()));
        if(res.getCount()==1)
            return true;
        res.close();
        return false;
    }
}