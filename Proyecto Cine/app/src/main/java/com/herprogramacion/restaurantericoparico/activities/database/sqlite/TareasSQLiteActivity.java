package com.herprogramacion.restaurantericoparico.activities.database.sqlite;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.herprogramacion.restaurantericoparico.R;
import com.herprogramacion.restaurantericoparico.adapters.database.sqlite.DBAdapter;


public class TareasSQLiteActivity extends ListActivity {
    private DBAdapter dbHelper;
    private static final int ACTIVITY_CREATE = 0;
    private static final int ACTIVITY_EDIT = 1;
    private static final int DELETE_ID = Menu.FIRST + 1;
    private Cursor cursor;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tareas_sqlite);

        this.getListView().setDividerHeight(2);
        dbHelper = new DBAdapter(this);
        dbHelper.open();
        fillData();
        registerForContextMenu(getListView());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createTodo();
                Snackbar.make(view, "Item Creado", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    // Creamos el menu desde el archivo XML
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.listmenu, menu);
        return true;
    }

    // Codificamos la accion al seleccionar el item del menu
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.insert:
                createTodo();
                return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.insert:
                createTodo();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case DELETE_ID:
                AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
                dbHelper.deleteTodo(info.id);
                fillData();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void createTodo() {
        Intent i = new Intent(this, Details.class);
        startActivityForResult(i, ACTIVITY_CREATE);
    }

    //ListView y la accion al seleccionar un item
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent i = new Intent(this, Details.class);
        i.putExtra(DBAdapter.KEY_ROWID, id);
        //La actividad retorna un resultado cuando se llama startActivityForResult
        startActivityForResult(i, ACTIVITY_EDIT);
    }

    //El siguiente metodo se llama con el resultado de otra actividad
    // requestCode es el codigo original que se manda a la actividad
    // resultCode es el codigo de retorno, 0 significa que todo sali� bien
    // intent es usado para obtener alguna informaci�n del caller
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        fillData();
    }

    private void fillData() {
        cursor = dbHelper.fetchAllTodos();
        startManagingCursor(cursor);

        String[] from = new String[] { DBAdapter.KEY_SUMMARY };
        int[] to = new int[] { R.id.label };

        //Creamos un array adapter para desplegar cada una de las filas
        SimpleCursorAdapter notes = new SimpleCursorAdapter(this, R.layout.row, cursor, from, to);
        setListAdapter(notes);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, DELETE_ID, 0, R.string.menu_delete);
    }

    @Override
    protected void onDestroy() {
        Toast.makeText(getApplicationContext(), "onDestroy()", Toast.LENGTH_SHORT).show();
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}