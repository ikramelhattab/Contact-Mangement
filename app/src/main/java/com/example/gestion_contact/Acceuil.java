package com.example.gestion_contact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

   public class Acceuil extends AppCompatActivity {
    static ArrayList<Contact> data = new ArrayList<Contact>();

    Button aff,aj;
    TextView tvuserneme;
    FileWriter fw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceuil);

        tvuserneme=findViewById(R.id.acc);
        aff=findViewById(R.id.btnaff_acc);
        aj=findViewById(R.id.btnaj_acc);

        Intent x =this.getIntent();
        Bundle b = x.getExtras();
        String u=b.getString("USER");
        tvuserneme.setText("Acceuil de M." +u);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);
        aj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Acceuil.this,Ajout.class);
                startActivity(i);


            }
        });

        aff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Acceuil.this,Affichage.class);
                startActivity(i);
            }
        });
    }

       @Override
       protected void onStart() {
           super.onStart();

           //import files
           if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
           {

               load_data();
               //list.invalidateViews();
           }
           else{
               //demande de permission
               ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                       1);
           }


       }

       @Override
       protected void onStop() {

         /*  if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
           {
               save_data();
               Toast.makeText(this, "data saved ", Toast.LENGTH_SHORT).show();

           }
           else {
               Toast.makeText(this, "error save", Toast.LENGTH_SHORT).show();

           }

           //sauvgarde datas */
           super.onStop();

       }

    @Override
    protected void onDestroy() {
        Toast.makeText(this,"Destroyed",Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }



       private void save_data() {
           String dir = Environment.getExternalStorageDirectory().getPath();
           File f= new File(dir, "fichier.txt");

           try {

               FileWriter fw= new FileWriter(f,true);
               BufferedWriter bw = new BufferedWriter(fw);

               for(int i=0; i<Acceuil.data.size(); i++)
               {
                   bw.write(Acceuil.data.get(i).nom+"#"+Acceuil.data.get(i).prenom +"#"+Acceuil.data.get(i).numero+"\n");
               }
               bw.close();
               fw.close();
               Acceuil.data.clear();

           } catch (IOException e) {
               e.printStackTrace();
           }
       }


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



boolean permission_write=false;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==1){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                permission_write =true;
            }
            else{
                permission_write =false;

            }
            }
        }
    }
