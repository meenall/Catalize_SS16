package com.catalizeapp.catalize_ss25;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ReportBug extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_bug);
        final Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        final TextView people = (TextView) findViewById(R.id.people);
        final EditText et2=(EditText)findViewById(R.id.editText2);

        Button sendThis = (Button) findViewById(R.id.send_report);
        final Bundle extras = getIntent().getExtras();
        final String personName = (String) extras.get("name_value");

        sendThis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), personName.toString(), Toast.LENGTH_LONG).show();
                try {
                    SmsManager.getDefault().sendTextMessage("9154719427", null, personName.toString() + "\n" + "Bug Report" + "\n"+et2.getText().toString(), null, null);
                } catch (Exception e) {
                    AlertDialog.Builder alertDialogBuilder = new
                            AlertDialog.Builder(ReportBug.this);
                    AlertDialog dialog = alertDialogBuilder.create();


                    dialog.setMessage(e.getMessage());


                    dialog.show();
                }
                Toast.makeText(getApplicationContext(), "Bug report submitted!", Toast.LENGTH_LONG).show();
                startActivity(sendIntent);
            }
        });
    }
}
