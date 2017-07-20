package com.vladislavyundin.pesn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewCat extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_cat);

        final EditText text = (EditText) findViewById(R.id.editText);

        Button ok_btn = (Button) findViewById(R.id.button);
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = text.getText().toString();
                if (name.equals("")){
                    finish();
                }
                Intent intent = new Intent();
                intent.putExtra("name", name);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
