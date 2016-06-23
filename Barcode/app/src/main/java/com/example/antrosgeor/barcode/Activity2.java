package com.example.antrosgeor.barcode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Activity2 extends AppCompatActivity {
    DatabaseHelper myDb;

    String contents, values, notes;
    TextView textView1;
    Button install;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);
        Bundle extras = getIntent().getExtras();
        myDb = new DatabaseHelper(this);

        textView1 = (TextView) findViewById(R.id.textView1);
        editText = (EditText) findViewById(R.id.editText);
        install = (Button) findViewById(R.id.install);

        if(extras !=null)
        {
            contents = extras.getString("PersonID");
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss  dd-MM-yyyy");
            String currentDateandTime = sdf.format(new Date());
            values = currentDateandTime;
        }
        textView1.setText(contents + " \n " +values);
        Install();

    }

    public void Install() {
        install.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Calling Application class (see application tag in
                // AndroidManifest.xml)
                notes = editText.getText().toString();
                if (notes.isEmpty()){
                    Toast.makeText(getBaseContext(), "Write a note", Toast.LENGTH_SHORT).show();
                }else{
                boolean isInserted = myDb.insertData(contents, values, notes);
                if (isInserted = true) {
                    Toast.makeText(getApplicationContext(), "ok install data", Toast.LENGTH_SHORT).show();
                    // if ok then go to MainActivity.
                    Intent act2 = new Intent(view.getContext(), MainActivity.class);
                    startActivity(act2);
                } else {
                    // else show the message.
                    Toast.makeText(getBaseContext(), "Data Not Inserted", Toast.LENGTH_LONG).show();
                }
            }
            }
        });
    }
}
