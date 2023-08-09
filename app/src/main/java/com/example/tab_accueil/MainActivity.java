package com.example.tab_accueil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnavecrdv, btnsansrdv, btnsortie, btnenregistrement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnavecrdv = (Button) findViewById(R.id.btnavecrdv);

        btnavecrdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentavecrdv = new Intent(getApplicationContext() , ActivityAvecRdv.class);
                startActivity(intentavecrdv);
            }
        });

        btnsansrdv = (Button) findViewById(R.id.btnsansrdv);

        btnsansrdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentsansrdv = new Intent(getApplicationContext(), ActivitySansRdv.class);
                startActivity(intentsansrdv);
            }
        });

        btnenregistrement = (Button) findViewById(R.id.btnenregistrement);

        btnenregistrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentenregistrement = new Intent(getApplicationContext(), ActivityEnregistrement.class);
                startActivity(intentenregistrement);
            }
        });
        btnsortie = (Button) findViewById(R.id.btnsortie);

        btnsortie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentsortie = new Intent(getApplicationContext(), Liste_Visiteur.class);
                startActivity(intentsortie);
            }
        });

    }
}

