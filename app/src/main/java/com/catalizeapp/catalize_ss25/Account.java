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

public class Account extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);

        final Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        final EditText et=(EditText)findViewById(R.id.editText);
        final TextView people = (TextView) findViewById(R.id.people);
        String temp = Contacts.people;
        temp = temp.replaceAll("[^0-9]","");
        people.setText(temp.substring(0,10));
        //sendIntent.putExtra(et.getText().toString(), "default content");
        //sendIntent.setType("vnd.android-dir/mms-sms");

        Button send = (Button) findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    SmsManager.getDefault().sendTextMessage("2013751471", null, et.getText().toString()+ "\n" + Contacts.people, null, null);
                } catch (Exception e) {
                    AlertDialog.Builder alertDialogBuilder = new
                            AlertDialog.Builder(Account.this);
                    AlertDialog dialog = alertDialogBuilder.create();


                    dialog.setMessage(e.getMessage());


                    dialog.show();
                    startActivity(sendIntent);
                }
            }
        });
    }
}

