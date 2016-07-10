package com.catalizeapp.catalize_ss25;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.Collections;

import android.app.Activity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.SearchView;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.content.ContentResolver;
public class Contacts extends AppCompatActivity {
    ListView list;
    LinearLayout ll;
    Button connect;
    public static String people = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        final ArrayList<String> contacts = new ArrayList<String>();
        //search bar below
        Intent searchIntent = getIntent();
        if(Intent.ACTION_SEARCH.equals(searchIntent.getAction())){

            String query = searchIntent.getStringExtra(SearchManager.QUERY);
            Toast.makeText(Contacts.this,query, Toast.LENGTH_LONG).show();
        }

        ////////////////////////////////////////////////////// PULLING CONTACTS
        Cursor c = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                null, null, null);
        while (c.moveToNext()) {

            String contactName = c
                    .getString(c
                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phNumber = c
                    .getString(c
                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            contacts.add("  " + contactName + "\n" + "  " + phNumber);

        }
        c.close();
        Cursor cEmail = getContentResolver().query(
                ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                null, null, null);
        while (cEmail.moveToNext()) {

            String email = cEmail
                    .getString(cEmail
                            .getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));

            contacts.add("  " + "\n" + email);

        }
        cEmail.close();
        Collections.sort(contacts);

        ///////////////////////////////////////////////////////

        CheckBox master_cb = new CheckBox(getApplicationContext());
        master_cb.setText("Check All");

        final ListView lv = (ListView) findViewById(R.id.listView1);
        //final TextView people = (TextView) findViewById(R.id.people);

        /*lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
                String selectedFromList =(String) (lv.getItemAtPosition(myItemInt));
                Toast.makeText(Contacts.this, selectedFromList,
                        Toast.LENGTH_LONG).show();
            }
        });*/


        //ListView lv = (ListView) findViewById(R.id.listView1);

        my_custom_adapter adapter = new my_custom_adapter(this, android.R.layout.simple_list_item_1, contacts);
        lv.setAdapter(adapter);
        master_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Intent my_intent = new Intent("master_check_change");
                my_intent.putExtra("check_value", isChecked);
                sendBroadcast(my_intent);
            }
        });



        Button connect = (Button) findViewById(R.id.connect);
        connect.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                int len = lv.getCount();
                people = "";
                String item = "";
                SparseBooleanArray checked = lv.getCheckedItemPositions();
                //checked = my_custom_adapter.itemChecked;
                for (int i = 0; i < len; i++)
                    if (my_custom_adapter.itemChecked.get(i)) {
                        item += contacts.get(i) + " ";
                    }
                //Toast.makeText(Contacts.this, item,
                  //      Toast.LENGTH_LONG).show();
                people=item;
                startActivityForResult(new Intent(Contacts.this, Account.class), 10);
            }
        });
    }

    class LoadContactsAyscn extends AsyncTask<Void, Void, ArrayList<String>> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            pd = ProgressDialog.show(Contacts.this, "Loading Contacts",
                    "Please Wait");
        }

        @Override
        protected ArrayList<String> doInBackground(Void... params) {
            // TODO Auto-generated method stub
            ArrayList<String> contacts = new ArrayList<String>();



            return contacts;
        }


        @Override
        protected void onPostExecute(ArrayList<String> contacts) {
            //TODO Auto-generated method stub
            super.onPostExecute(contacts);

            pd.cancel();

            //ll.removeView(loadBtn);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getApplicationContext(), R.layout.text, contacts);

            list.setAdapter(adapter);
            list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);



        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){

       getMenuInflater().inflate(R.menu.main_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch(item.getItemId()){
            case(R.id.menu_1):
                Toast.makeText(Contacts.this, "option 1 clicked", Toast.LENGTH_LONG).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
/*//-----------------

            ContentResolver cr = getContentResolver();
            Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

            if (cur.getCount() > 0) {
                while (cur.moveToNext()) {
                    String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                        // This inner cursor is for contacts that have multiple numbers.
                        Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[] { id }, null);
                        while (pCur.moveToNext()) {
                            contacts.add(name);
                            Log.i("Contact List", name);
                        }
                        pCur.close();
                    }
                }

                Collections.sort(contacts);
                int cnt = contacts.size();

            }
            cur.close();


            *///----------------
           /* Cursor c = getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    null, null, null);
            while (c.moveToNext()) {

                String contactName = c
                        .getString(c
                                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phNumber = c
                        .getString(c
                                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                contacts.add(contactName + "\n" + phNumber);

            }
            c.close();

            return contacts;
        }*/