package com.example.gestion_contact;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Affichage extends AppCompatActivity implements AdapterView.OnItemClickListener, DialogInterface.OnClickListener {

    public static final int REQUEST_READ_CONTACTS = 79;

    SearchView edrec;
    static ListView lv;
    ArrayAdapter adapter;
    ArrayList mobileArray;
     MonAdapter adap;


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage);

        edrec=findViewById(R.id.rec);
         lv=findViewById(R.id.lv);

       // adapter = new ArrayAdapter(Affichage.this,android.R.layout.simple_list_item_1,Acceuil.data);
         mobileArray = new ArrayList();

         adap =new MonAdapter(Affichage.this,Acceuil.data);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, mobileArray);



        lv.setAdapter(adap);

       lv.setOnItemClickListener(this);




       edrec.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
           @Override
           public boolean onQueryTextSubmit(String query) {
               return false;
           }

           @Override
           public boolean onQueryTextChange(String s) {
               adap.getFilter().filter(s);
               return false;

           }
       });

       /*
       edrec.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Toast.makeText(getApplicationContext(),"before text change",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adap.getFilter().filter(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {
                Toast.makeText(getApplicationContext(),"after text change",Toast.LENGTH_SHORT).show();
            }
        });

*/

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED) {
                mobileArray = getAllContacts();
        } else {
            requestPermission();
        }


    }


    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_CONTACTS)) {
            // show UI part if you want here to show some rationale !!!
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_CONTACTS},
                    REQUEST_READ_CONTACTS);
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_CONTACTS)) {

        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_CONTACTS},
                    REQUEST_READ_CONTACTS);
        }
    }

    int indice;
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        indice = position;
        AlertDialog.Builder alert = new AlertDialog.Builder(Affichage.this);
        alert.setTitle("Attention !");
        alert.setMessage("choisir une action");
        alert.setPositiveButton("Supprimer",this);
        alert.setNegativeButton("Modifier",this);
        alert.setNeutralButton("Supprimer tout",this);
        alert.show();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if(which==dialog.BUTTON_POSITIVE){
            Acceuil.data.remove(indice);
            lv.invalidateViews();
        }

        if(which==dialog.BUTTON_NEGATIVE){
            Intent i=new Intent(Affichage.this,Mod.class);
            Contact item = (Contact) lv.getItemAtPosition(indice);
      //  i.putExtra("info", (Serializable) item);
            i.putExtra("position",indice);
            startActivity(i);
        }
        if(which==dialog.BUTTON_NEUTRAL){
            Acceuil.data.clear();
            lv.invalidateViews();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mobileArray = getAllContacts();
                } else {
                    // permission denied,Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }
    private ArrayList getAllContacts() {
        ArrayList<String> nameList = new ArrayList<>();
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));
                nameList.add(name);
                if (cur.getInt(cur.getColumnIndex( ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                    }
                    pCur.close();
                }
            }
        }
        if (cur != null) {
            cur.close();
        }
        return nameList;}

    private void load_data() {
        Acceuil.data.clear();
        String dir = Environment.getExternalStorageDirectory().getPath().toString();
        File f = new File(dir, "fichier.txt");
        if (f.exists()) {
            try {
                FileReader fr = new FileReader(f);
                BufferedReader br = new BufferedReader(fr);
                String ligne = null;
                while ((ligne = br.readLine()) != null) {

                    if (ligne != null) {
                        String[] t = ligne.split("#");
                        Contact c = new Contact(t[0], t[1], t[2]);
                        Acceuil.data.add(c);
                    }

                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }






}

