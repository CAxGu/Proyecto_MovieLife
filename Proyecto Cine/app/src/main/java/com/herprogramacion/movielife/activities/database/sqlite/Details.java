package com.herprogramacion.movielife.activities.database.sqlite;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.herprogramacion.movielife.R;
import com.herprogramacion.movielife.adapters.database.sqlite.DBAdapter;

public class Details extends Activity {
    private EditText mTitleText;
    private EditText mBodyText;
    private Long mRowId;
    private DBAdapter mDbHelper;
    private Spinner mCategory;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        mDbHelper = new DBAdapter(this);
        mDbHelper.open();
        setContentView(R.layout.edit);

        mCategory = (Spinner) findViewById(R.id.category);
        mTitleText = (EditText) findViewById(R.id.todo_edit_summary);
        mBodyText = (EditText) findViewById(R.id.todo_edit_description);


        Button confirmButton = (Button) findViewById(R.id.todo_edit_button);
        mRowId = null;
        Bundle extras = getIntent().getExtras();
        mRowId = (bundle == null) ? null : (Long) bundle.getSerializable(DBAdapter.KEY_ROWID);
        if (extras != null) {
            mRowId = extras.getLong(DBAdapter.KEY_ROWID);
        }
        populateFields();
        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setResult(RESULT_OK);
                finish();
            }

        });
    }

    private void populateFields() {
        if (mRowId != null) {
            Cursor todo = mDbHelper.fetchTodo(mRowId);
            startManagingCursor(todo);
            String category = todo.getString(todo.getColumnIndexOrThrow(DBAdapter.KEY_CATEGORY));
            for (int i=0; i<mCategory.getCount();i++){
                String s = (String) mCategory.getItemAtPosition(i);
                Log.e(null, s +" " + category);
                if (s.equalsIgnoreCase(category)){
                    mCategory.setSelection(i);
                }
            }
            mTitleText.setText(todo.getString(todo.getColumnIndexOrThrow(DBAdapter.KEY_SUMMARY)));
            mBodyText.setText(todo.getString(todo.getColumnIndexOrThrow(DBAdapter.KEY_DESCRIPTION)));
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveState();
        outState.putSerializable(DBAdapter.KEY_ROWID, mRowId);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveState();
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateFields();
    }

    private void saveState() {
        String category = (String) mCategory.getSelectedItem();
        String summary = mTitleText.getText().toString();
        String description = mBodyText.getText().toString();
        if (mRowId == null) {
            long id = mDbHelper.createTodo(category, summary, description);
            if (id > 0) {
                mRowId = id;
            }
        } else {
            mDbHelper.updateTodo(mRowId, category, summary, description);
        }
    }
}
