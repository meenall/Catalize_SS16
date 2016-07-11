package com.catalizeapp.catalize_ss25;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ReportBug extends AppCompatActivity {

    private String personName;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_bug);

        sharedPreferences = this.getSharedPreferences("com.catalizeapp.catalize_ss25", Context.MODE_PRIVATE);

        //Intent intent = getIntent();
        //final String personName = intent.getStringExtra("name_value").toString();
        final Intent sendIntent2 = new Intent(Intent.ACTION_VIEW);
        final EditText et2=(EditText)findViewById(R.id.editText2);
        Button sendText = (Button) findViewById(R.id.send_report);
        //final String name = LoginActivity.personName;

        personName= sharedPreferences.getString("name","");

        sendText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    SmsManager.getDefault().sendTextMessage("9154719427", null, "Bug Report from" + " " + personName  + "\n" +et2.getText().toString()+ "\n", null, null);
                    Toast.makeText(getApplicationContext(),"Bug Report Sent!", Toast.LENGTH_LONG).show();
                    finish();
                } catch (Exception e) {
                    AlertDialog.Builder alertDialogBuilder = new
                            AlertDialog.Builder(ReportBug.this);
                    AlertDialog dialog = alertDialogBuilder.create();


                    dialog.setMessage(e.getMessage());


                    dialog.show();

                    startActivity(sendIntent2);
                }
            }
        });
    }
}