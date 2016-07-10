package com.catalizeapp.catalize_ss25;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
        temp = temp.trim();

        if (temp.length()==14){ //this is for numbers of length 7 w/o area code (ex: 471-9427)
            String person1 = temp.substring(0,7);
            String person2 = temp.substring(7);
            Toast.makeText(getApplicationContext(), person1+" "+person2 + "length 7 & 7", Toast.LENGTH_LONG).show();
        }
//this does not work for numbers with parenthesis or no "-"s
        else if (temp.length() == 17){ //this is for a number of len 7 and a number of len 10
            String temp2 = Contacts.people;
            String firstThing = temp2.substring(0,2);
            temp2 = temp2.trim();
            int index = temp2.indexOf("-");
            String check = temp2.substring(index+1,index+6);
            if (firstThing.compareTo("(")==1){
                String person1 = temp.substring(0,10);
                String person2 = temp.substring(10);
                Toast.makeText(getApplicationContext(), person1+" "+person2 + "length 10 and 7 with parenthesis", Toast.LENGTH_LONG).show();
            }
            else if (check.contains("-")){
                temp2 = temp2.replaceAll("[^0-9]","");
                String person1 = temp2.substring(0,10);
                String person2 = temp2.substring(10);

                Toast.makeText(getApplicationContext(), person1+" "+person2 + "length 10 and 7", Toast.LENGTH_LONG).show();
            }
            else{
                temp2 = temp2.replaceAll("[^0-9]","");
                String person1 = temp2.substring(0,7);
                String person2 = temp2.substring(7);
                Toast.makeText(getApplicationContext(), person1+" "+person2 + "length 7 and 10", Toast.LENGTH_LONG).show();
            }
        } else if (temp.length()==18){ //for numbers of length 7 and 11
            String firstNum = temp.substring(0,2);
            if (firstNum.compareTo("1")==1){
                String person1 = temp.substring(0,11);
                String person2 = temp.substring(11);
                Toast.makeText(getApplicationContext(), person1+" "+person2 + "length 11 and 7", Toast.LENGTH_LONG).show();
            }
            else{
                String person1 = temp.substring(0,7);
                String person2 = temp.substring(7);
                Toast.makeText(getApplicationContext(), person1+" "+person2 + "length 7 and 11", Toast.LENGTH_LONG).show();

            }
        } else if (temp.length()==20){
            String person1 = temp.substring(0,10);
            String person2 = temp.substring(10);
            Toast.makeText(getApplicationContext(), person1+" "+person2 + "length 20", Toast.LENGTH_LONG).show();
        } else if ( temp.length()== 21) {
            String firstNum = temp.substring(0,2);
            if (firstNum.compareTo("1")==1){
                String person1 = temp.substring(0, 11);
                String person2 = temp.substring(11);
                Toast.makeText(getApplicationContext(), person1+" "+person2 + "length 21 #1", Toast.LENGTH_LONG).show();
            }
            else {
                String person1 = temp.substring(0, 10);
                String person2 = temp.substring(10);
                String index = temp.substring(0);
                Toast.makeText(getApplicationContext(),person1 + " " + person2 + "length 21 #2", Toast.LENGTH_LONG).show();
            }
        }

        else if (temp.length() == 22) {
            String person1 = temp.substring(0, 11);
            String person2 = temp.substring(11);
            Toast.makeText(getApplicationContext(), person1+" "+person2 +"length 22", Toast.LENGTH_LONG).show();
        }

        else if (temp.length() > 22){
            Toast.makeText(getApplicationContext(), "Please only select two people to introduce", Toast.LENGTH_SHORT).show();
        }

        else {
            Toast.makeText(getApplicationContext(), "One of these numbers is invalid", Toast.LENGTH_SHORT).show();
        }

        //people.setText(temp.substring(0,10));
        //sendIntent.putExtra(et.getText().toString(), "default content");
        //sendIntent.setType("vnd.android-dir/mms-sms");

        Button send = (Button) findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    SmsManager.getDefault().sendTextMessage("9154719427", null, et.getText().toString()+ "\n" + Contacts.people, null, null);
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

