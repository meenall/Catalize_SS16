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
        final Intent sendIntent2 = new Intent(Intent.ACTION_VIEW);
        final EditText et2=(EditText)findViewById(R.id.editText2);
//        final TextView people = (TextView) findViewById(R.id.people);
//        String temp = Contacts.people;

        Button send = (Button) findViewById(R.id.send_report);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    SmsManager.getDefault().sendTextMessage("9154719427", null, et2.getText().toString(), null, null);
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