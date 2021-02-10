package com.example.gestion_contact;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Ajout extends AppCompatActivity {

    EditText ednom,edp,ph;
    Button val,qte;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout);
        ednom=findViewById(R.id.ednom);
        edp=findViewById(R.id.edpro);
        ph=findViewById(R.id.edphone);
        val=findViewById(R.id.val);
        qte=findViewById(R.id.qte);

        qte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ajout.this.finish();
            }
        });

        val.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom =ednom.getText().toString();
                String mp =edp.getText().toString();
                String pho =ph.getText().toString();

                Toast.makeText(Ajout.this,"taille:"+ Acceuil.data.size() ,Toast.LENGTH_SHORT).show();

                Contact c =new Contact (nom,mp,pho);
                Acceuil.data.add(c);


                // stockage interne de telephone
                String dir= Environment.getExternalStorageDirectory().getPath().toString();
                //declaration fichier.txt
                File f=new File(dir,"fichier.txt");
                try {
                    //append : nzidou f contenu : true or  delete and  add  new : false
                    FileWriter fw = new FileWriter(f,true);
                    //memoire tompon
                    BufferedWriter bw =new BufferedWriter(fw);

                    bw.write(nom+"#"+mp+"#"+pho+"\n");

                    bw.close();
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
               load_data();
                Intent i = new Intent(Ajout.this, Affichage.class);
                startActivity(i);

            }
        });


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
}