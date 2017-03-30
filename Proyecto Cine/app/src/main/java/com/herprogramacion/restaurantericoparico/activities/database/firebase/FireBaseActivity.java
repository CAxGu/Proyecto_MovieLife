package com.herprogramacion.restaurantericoparico.activities.database.firebase;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.herprogramacion.restaurantericoparico.R;
import com.herprogramacion.restaurantericoparico.adapters.database.firebase.ToDoItemsRecyclerAdapter;
import com.herprogramacion.restaurantericoparico.models.ToDoItem;

import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class FireBaseActivity extends AppCompatActivity {
    @InjectView(R.id.recycler_view_items) RecyclerView recyclerView;
    @InjectView(R.id.editTextItem) EditText editTextItem;

    private DatabaseReference databaseReference;
    private FirebaseRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fire_base);
        ButterKnife.inject(this);
        setToolbar();

        setupUsername();
        SharedPreferences prefs = getApplication().getSharedPreferences("ToDoPrefs", 0);
        String username = prefs.getString("username", null);
        setTitle(username);

        Toast.makeText(getApplicationContext(),"Hola, he entrado",Toast.LENGTH_LONG);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("items");
        adapter = new ToDoItemsRecyclerAdapter(R.layout.row2, databaseReference);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    private void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null)// Habilitar Up Button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupUsername() {
        SharedPreferences prefs = getApplication().getSharedPreferences("ToDoPrefs", 0);
        String username = prefs.getString("username", null);
        if (username == null) {
            Random r = new Random();
            username = "AndroidUser" + r.nextInt(100000);
            prefs.edit().putString("username", username).commit();
        }
    }

    @OnClick(R.id.fab)
    public void addToDoItem() { //databaseReference.push().setValue(toDoItem);
        SharedPreferences prefs = getApplication().getSharedPreferences("ToDoPrefs", 0);
        String username = prefs.getString("username", null);

        String itemText = editTextItem.getText().toString();
        editTextItem.setText("");

        InputMethodManager inputMethodManager = (InputMethodManager)  getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        if (!itemText.isEmpty()) {
            ToDoItem toDoItem = new ToDoItem(itemText.trim(), username);
            //databaseReference.push().setValue(toDoItem);
            databaseReference.child(itemText.trim()).setValue(toDoItem,new DatabaseReference.CompletionListener(){
                public void onComplete(DatabaseError error, DatabaseReference ref) {
                    if(error == null)
                        Toast.makeText(getApplicationContext(), "Alta OK", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}