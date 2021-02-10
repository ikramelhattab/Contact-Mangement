package com.example.gestion_contact;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Mod extends AppCompatActivity {
    EditText n,p,number;
    Button annu,mod;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mod);
        n=findViewById(R.id.tvn);
        p=findViewById(R.id.tvp);
        number=findViewById(R.id.num);
        mod=findViewById(R.id.mod);
        annu=findViewById(R.id.annu);

        Intent x=this.getIntent();
        Contact contact = (Contact) x.getSerializableExtra("info");
        Bundle b=x.getExtras();
        final int indice=b.getInt("position");
        n.setText(Contact.nom);
        p.setText(Contact.prenom);
        number.setText(Contact.numero);



        annu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Mod.this.finish();
            }
        });
        mod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom =n.getText().toString();
                String pre =p.getText().toString();
                String num =number.getText().toString();
                Contact c =new Contact(nom,pre,num);
                Acceuil.data.set(indice,c);
                Affichage.lv.invalidateViews();
                Intent i=new Intent(Mod.this,Affichage.class);
                startActivity(i);


            }
        });



    }
}