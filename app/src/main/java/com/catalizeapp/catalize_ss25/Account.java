package com.catalizeapp.catalize_ss25;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Account extends AppCompatActivity {

    Context context = null;
    boolean flag = false;
    private String result;
    private String result2;
    private SharedPreferences sharedPreferences;
    private String personName;
    private String personEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);
        context = this;
        sharedPreferences = this.getSharedPreferences("com.catalizeapp.catalize_ss25", Context.MODE_PRIVATE);
        //final Dialog dialog = new Dialog(Account.this);
        final EditText prompt = (EditText) findViewById(R.id.prompt);
        if (Contacts.person1.contains("@") && Contacts.person2.contains("@")) {
            TextView name = (TextView) findViewById(R.id.nameerror);
            //name.setText("Set a name for " + Contacts.person1 + ": ");
            LayoutInflater li = LayoutInflater.from(context);
            View promptsView = li.inflate(R.layout.layout_prompts, null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    context);

            alertDialogBuilder.setView(promptsView);

            final EditText userInput = (EditText) promptsView
                    .findViewById(R.id.editTextDialogUserInput);
            final EditText userInput2 = (EditText) promptsView
                    .findViewById(R.id.input2);

            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    flag = true;
                                    // get user input and set it to result
                                    // edit text
                                    result = userInput.getText().toString();
                                    result2 = userInput2.getText().toString();
                                    prompt.setText("Hello " + result + ", meet " + result2 + ". I am introducing you two because...");
                                    //Contacts.person1 = result;
                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();

        } else if (Contacts.person1.contains("@") && !Contacts.person2.contains("@") || Contacts.person2.contains("@") && !Contacts.person1.contains("@")) {
            LayoutInflater li = LayoutInflater.from(context);
            View promptsView = li.inflate(R.layout.layout_prompt, null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    context);

            alertDialogBuilder.setView(promptsView);

            final EditText userInput = (EditText) promptsView
                    .findViewById(R.id.editTextDialogUserInput);

            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    flag = true;
                                    // get user input and set it to result
                                    // edit text
                                    result = userInput.getText().toString();
                                    if (Contacts.person1.contains("@")) {
                                        prompt.setText("Hello " + result + ", meet " + Contacts.person2 + ". I am introducing you two because...");
                                    } else {
                                        prompt.setText("Hello " + Contacts.person1 + ", meet " + result + ". I am introducing you two because...");
                                    }
                                    //Contacts.person1 = result;
                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();

        } else {
            prompt.setText("Hello " + Contacts.person1 + ", meet " + Contacts.person2 + ". I am introducing you two because...");
        }




        final Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        final EditText et=(EditText)findViewById(R.id.prompt);
        final TextView people = (TextView) findViewById(R.id.people);
        //people.setText(Contacts.people);

       /* Contacts.numbers = Contacts.numbers.replaceAll("[^0-9]","");
        String temp = Contacts.numbers;
        if (temp.length()==14){ //this is for numbers of length 7 w/o area code (ex: 471-9427)
            String person1 = temp.substring(0,7);
            String person2 = temp.substring(7);
            //Toast.makeText(getApplicationContext(), person1+" "+person2 + "length 7 & 7", Toast.LENGTH_LONG).show();
        }
//this does not work for numbers with parenthesis or no "-"s
        else if (temp.length() == 17){ //this is for a number of len 7 and a number of len 10
            String temp2 = Contacts.numbers;
            String firstThing = temp2.substring(0,2);
            temp2 = temp2.trim();
            int index = temp2.indexOf("-");
            String check = temp2.substring(index+1,index+6);
            if (firstThing.compareTo("(")==1){
                String person1 = temp.substring(0,10);
                String person2 = temp.substring(10);
                //Toast.makeText(getApplicationContext(), person1+" "+person2 + "length 10 and 7 with parenthesis", Toast.LENGTH_LONG).show();
            }
            else if (check.contains("-")){
                temp2 = temp2.replaceAll("[^0-9]","");
                String person1 = temp2.substring(0,10);
                String person2 = temp2.substring(10);
                //Toast.makeText(getApplicationContext(), person1+" "+person2 + "length 10 and 7", Toast.LENGTH_LONG).show();
            }
            else{
                temp2 = temp2.replaceAll("[^0-9]","");
                String person1 = temp2.substring(0,7);
                String person2 = temp2.substring(7);
                //Toast.makeText(getApplicationContext(), person1+" "+person2 + "length 7 and 10", Toast.LENGTH_LONG).show();
            }
        } else if (temp.length()==18){ //for numbers of length 7 and 11
            String firstNum = temp.substring(0,2);
            if (firstNum.compareTo("1")==1){
                String person1 = temp.substring(0,11);
                String person2 = temp.substring(11);
                //Toast.makeText(getApplicationContext(), person1+" "+person2 + "length 11 and 7", Toast.LENGTH_LONG).show();
            }
            else{
                String person1 = temp.substring(0,7);
                String person2 = temp.substring(7);
                //Toast.makeText(getApplicationContext(), person1+" "+person2 + "length 7 and 11", Toast.LENGTH_LONG).show();
            }
        } else if (temp.length()==20){
            String person1 = temp.substring(0,10);
            String person2 = temp.substring(10);
            //Toast.makeText(getApplicationContext(), person1+" "+person2 + "length 20", Toast.LENGTH_LONG).show();
        } else if ( temp.length()== 21) {
            String firstNum = temp.substring(0,2);
            if (firstNum.compareTo("1")==1){
                String person1 = temp.substring(0, 11);
                String person2 = temp.substring(11);
                //Toast.makeText(getApplicationContext(), person1+" "+person2 + "length 21 #1", Toast.LENGTH_LONG).show();
            }
            else {
                String person1 = temp.substring(0, 10);
                String person2 = temp.substring(10);
                String index = temp.substring(0);
                //Toast.makeText(getApplicationContext(),person1 + " " + person2 + "length 21 #2", Toast.LENGTH_LONG).show();
            }
        }
        else if (temp.length() == 22) {
            String person1 = temp.substring(0, 11);
            String person2 = temp.substring(11);
            //Toast.makeText(getApplicationContext(), person1+" "+person2 +"length 22", Toast.LENGTH_LONG).show();
        }
        else if (temp.length() > 22){
            Toast.makeText(getApplicationContext(), "Please only select two people to introduce", Toast.LENGTH_SHORT).show();
        }
        else {
            //Toast.makeText(getApplicationContext(), "One of these numbers is invalid", Toast.LENGTH_SHORT).show();
        }*/

        //people.setText(temp.substring(0,10));
        //sendIntent.putExtra(et.getText().toString(), "default content");
        //sendIntent.setType("vnd.android-dir/mms-sms");


        //Toast.makeText(context, Contacts.numbers,
          //      Toast.LENGTH_SHORT).show();
        //sendIntent.putExtra(et.getText().toString(), "default content");
        //sendIntent.setType("vnd.android-dir/mms-sms");

        Button send = (Button) findViewById(R.id.send);
        personName= sharedPreferences.getString("name","");
        personEmail  = sharedPreferences.getString("email", "");
        send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    SmsManager.getDefault().sendTextMessage("9154719427", null, "Introduction made by: "+ personName + " " + personEmail+ "\n"+ "Contacts: " + Contacts.person1 + "\n" + Contacts.person2 + "\n" + Contacts.numbers + "\n" + et.getText().toString(), null, null);
                    LayoutInflater li = LayoutInflater.from(context);
                    View promptsView = li.inflate(R.layout.sent, null);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            context);

                    alertDialogBuilder.setView(promptsView);

                    alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton("Ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // get user input and set it to result
                                            // edit text
                                            finish();
                                        }
                                    });

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();
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

