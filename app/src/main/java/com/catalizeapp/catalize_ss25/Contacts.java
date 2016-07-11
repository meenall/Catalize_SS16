package com.catalizeapp.catalize_ss25;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

import android.app.Activity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.content.ContentResolver;
public class Contacts extends Activity {
    Context context = null;
    public static String person1 = "";
    public static String person2 = "";
    public static String numbers = "";
    ContactsAdapter objAdapter;
    ListView lv = null;
    EditText edtSearch = null;
    LinearLayout llContainer = null;
    Button btnOK = null;
    RelativeLayout rlPBContainer = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_contacts);
        rlPBContainer = (RelativeLayout) findViewById(R.id.pbcontainer);
        edtSearch = (EditText) findViewById(R.id.input_search);
        llContainer = (LinearLayout) findViewById(R.id.data_container);
        btnOK = (Button) findViewById(R.id.connect);
        btnOK.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                edtSearch.setText("");
                edtSearch.clearFocus();
                getSelectedContacts();
                startActivityForResult(new Intent(Contacts.this, Account.class), 10);
            }
        });
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2,
                                      int arg3) {
                // When user changed the Text
                String text = edtSearch.getText().toString()
                        .toLowerCase(Locale.getDefault());
                objAdapter.filter(text);
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }
            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });
        addContactsInList();
    }
    private void getSelectedContacts() {
        // TODO Auto-generated method stub
        numbers = "";
        person1 = "";
        person2 = "";
        StringBuffer sb = new StringBuffer();
        for (ContactObject bean : ContactsListClass.phoneList) {
            if (bean.isSelected()) {
                bean.setSelected(false);
                sb.append(bean.getName());
                numbers += bean.getNumber();
                sb.append(",");
                if (person1 == "") {
                    person1 = bean.getName();
                } else {
                    person2 = bean.getName();
                }
            }
        }
        String s = sb.toString().trim();
        if (TextUtils.isEmpty(s)) {
           // Toast.makeText(context, "Select atleast one Contact",
             //       Toast.LENGTH_SHORT).show();
        } else {
            s = s.substring(0, s.length() - 1);
            //Toast.makeText(context, "Selected Contacts : " + s,
              //      Toast.LENGTH_SHORT).show();
        }
    }
    private void addContactsInList() {
        // TODO Auto-generated method stub
        Thread thread = new Thread() {
            @Override
            public void run() {
                showPB();
                try {
                    Cursor cEmail = getContentResolver().query(
                            ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                            null, null, null);

                    Cursor phones = getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null, null, null, null);
                    try {
                        ContactsListClass.phoneList.clear();
                    } catch (Exception e) {
                    }
                    while (phones.moveToNext()) {
                        String phoneName = phones
                                .getString(phones
                                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        String phoneNumber = phones
                                .getString(phones
                                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        String phoneImage = phones
                                .getString(phones
                                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));

                        ContactObject cp = new ContactObject();


                        cp.setName(phoneName);
                        cp.setNumber(phoneNumber);
                        cp.setImage(phoneImage);
                        ContactsListClass.phoneList.add(cp);
                    }
                    while (cEmail.moveToNext()) {
                        if (cEmail.getCount() > 0) {
                            String email = cEmail
                                    .getString(cEmail
                                            .getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                            ContactObject cp2 = new ContactObject();
                            cp2.setName("Unknown");
                            cp2.setNumber(email);
                            cp2.setImage("");
                            ContactsListClass.phoneList.add(cp2);
                            //Toast.makeText(context, email,
                              //            Toast.LENGTH_SHORT).show();
                            //break;
                        }
                    }
                    phones.close();
                    cEmail.close();

                    lv = new ListView(context);
                    lv.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT));
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            llContainer.addView(lv);
                        }
                    });
                    Collections.sort(ContactsListClass.phoneList,
                            new Comparator<ContactObject>() {
                                @Override
                                public int compare(ContactObject lhs,
                                                   ContactObject rhs) {
                                    return lhs.getName().compareTo(
                                            rhs.getName());
                                }
                            });
                    objAdapter = new ContactsAdapter(Contacts.this,
                            ContactsListClass.phoneList);
                    lv.setAdapter(objAdapter);
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent,
                                                View view, int position, long id) {
                            CheckBox chk = (CheckBox) view
                                    .findViewById(R.id.contactcheck);
                            ContactObject bean = ContactsListClass.phoneList
                                    .get(position);
                            if (bean.isSelected()) {
                                bean.setSelected(false);
                                chk.setChecked(false);
                            } else {
                                bean.setSelected(true);
                                chk.setChecked(true);
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                hidePB();
            }
        };
        thread.start();
    }
    void showPB() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                rlPBContainer.setVisibility(View.VISIBLE);
                edtSearch.setVisibility(View.GONE);
                btnOK.setVisibility(View.GONE);
            }
        });
    }
    void hidePB() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                rlPBContainer.setVisibility(View.GONE);
                edtSearch.setVisibility(View.VISIBLE);
                btnOK.setVisibility(View.VISIBLE);
            }
        });
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
                Intent intentReportBug = new Intent(Contacts.this, ReportBug.class); //
                startActivity(intentReportBug);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}