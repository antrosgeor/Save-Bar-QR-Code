package com.example.antrosgeor.barcode;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;

    String contents = null, format;
    TextView test;
    Button go,show_All;
    Context CTX = this;
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        test = (TextView)findViewById(R.id.textView2);
        go = (Button) findViewById(R.id.go);
        show_All = (Button) findViewById(R.id.show_All);
        go.setVisibility(View.INVISIBLE);

        viewAll();
    }
/**Show all data from database. **/
    public void viewAll() {
        show_All.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Cursor res = myDb.getAllData();
                if (res.getCount() == 0) {
                    // show message
                    showMessage("Error", "Nothing found");
                    return;
                }
                // edo i arithmisi prepi na einia -1 gt 3ekiname apo to 0.
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("Id     : " + res.getString(0) + "\n");
                    buffer.append("Code   : " + res.getString(1) + "\n");
                    buffer.append("Values : " + res.getString(2) + "\n");
                    buffer.append("Note   : " + res.getString(3) + "\n\n\n");
                }
                // show all data
                showMessage("Data", buffer.toString());
            }
        });
    }
/**Stop Show all data from database. **/

    /** to show the message. **/
    public void showMessage(String title, String Message) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
    /**Stop ShowMessage **/


    public void scanBar(View v){
        try {
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
            startActivityForResult(intent, 0);
        }catch (ActivityNotFoundException e){
            showDialog(MainActivity.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }
    }

    public void scanQR(View v){
        try{
            Intent in = new Intent(ACTION_SCAN);
            in.putExtra("SCAN_MODE", "OR_CODE_MODE");
            startActivityForResult(in, 0);
        }catch (ActivityNotFoundException e){
            showDialog(MainActivity.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }
    }

    private Dialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence Yes, CharSequence No) {
        AlertDialog.Builder download = new AlertDialog.Builder(act);
        download.setTitle(title);
        download.setMessage(message);
        download.setPositiveButton(Yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                }catch (ActivityNotFoundException anfe){}
            }
        });

        download.setNegativeButton(No, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return download.show();
    }

    protected  void onActivityResult(int requestCode, int resultCode, Intent in){
        if(requestCode == 0){
            if (resultCode == RESULT_OK){
                contents = in.getStringExtra("SCAN_RESULT");
                format = in.getStringExtra("SCAN_RESULT_FORMA");
                Toast toast = Toast.makeText(this, "Content" + contents +"Format:" + format, Toast.LENGTH_LONG);
                toast.show();
                go.setVisibility(View.VISIBLE);
                test.setText("Code is : \n" + contents);
              }
        }
    }
/**Start go**/
    public void go(View v){

        DatabaseHelper DOP = new DatabaseHelper(CTX);
        Cursor CR = DOP.getInformation(DOP);
        CR.moveToFirst();
        Cursor res = myDb.getAllData();
         if(contents.isEmpty()) {
            Toast toast = Toast.makeText(this, "you dont have get value for BarCode", Toast.LENGTH_LONG);
            toast.show();
        }else if (res.getCount() == 0) {
             Intent i = new Intent(getBaseContext(), Activity2.class);
             i.putExtra("PersonID", contents);
             startActivity(i);
         }else{
            boolean loginstatus = false;
            do {
                if (contents.equals(CR.getString(1))) {
                    loginstatus = true;
                }
            }while (CR.moveToNext());
            if (loginstatus) {
                Toast.makeText(getBaseContext(), "this BarCode is in the data!!!",
                        Toast.LENGTH_LONG).show();
            } else {
                Intent i = new Intent(getBaseContext(), Activity2.class);
                i.putExtra("PersonID", contents);
                startActivity(i);
            }
        }
    }
/**Stop go**/

}
