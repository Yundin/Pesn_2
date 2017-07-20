package com.vladislavyundin.pesn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class NewTrack extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_track);

        Intent intent = getIntent();
        ArrayList<String> listOfCategories = intent.getStringArrayListExtra("Cats");
        int category = intent.getIntExtra("Category", -1);

        final EditText author = (EditText) findViewById(R.id.editText);
        final EditText track = (EditText) findViewById(R.id.editText2);

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listOfCategories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        if (category != -1)
            spinner.setSelection(category);

        Button ok_btn = (Button) findViewById(R.id.button);
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _author = author.getText().toString();
                String _track = track.getText().toString();
                String category = spinner.getSelectedItem().toString();
                if (_author.equals("") || _track.equals("")){
                    finish();
                }
                Intent intent = new Intent();
                intent.putExtra("author", _author);
                intent.putExtra("track", _track);
                intent.putExtra("category", category);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
