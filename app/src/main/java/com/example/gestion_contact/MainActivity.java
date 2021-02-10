package com.example.gestion_contact;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
EditText ednom,edmp;
Button val,qte;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ednom=findViewById(R.id.ednom);
        edmp=findViewById(R.id.edpro);
        val=findViewById(R.id.btnval_auth);
        qte=findViewById(R.id.btnqte_auth);

        qte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               MainActivity.this.finish();
            }
        });

        val.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom =ednom.getText().toString();
                String mp =edmp.getText().toString();
                if(nom.equalsIgnoreCase("ikram") && mp.equals("0000")){

                    Intent i =new Intent(MainActivity.this,Acceuil.class);
                    i.putExtra("USER",nom);
                    startActivity(i);
                }
                else{

                    Toast.makeText(MainActivity.this,"valeur non valide",Toast.LENGTH_LONG).show();
                }

            }
        });


      }

    @Override
    public void onClick(View v) {

    }
}