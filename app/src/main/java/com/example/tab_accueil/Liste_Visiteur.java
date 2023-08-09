package com.example.tab_accueil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Liste_Visiteur extends AppCompatActivity{

    ListView ls;
    //Button btnevac;
    SQLiteHelper h = new SQLiteHelper(Liste_Visiteur.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_visiteur);

        ls = findViewById(R.id.lst);


        Cursor c = h.getAllVisiteursPresents();
        Map<String, String> MapVisiteur = new HashMap<String, String>();
        ArrayList<String> data = new ArrayList<>();
        ArrayList<String> data2 = new ArrayList<>();
        /*-------------------------------------*/

        /*--------------------------------------*/
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(Liste_Visiteur.this, R.layout.item, c,
                new String[]{c.getColumnName(0), c.getColumnName(1), c.getColumnName(2), c.getColumnName(3), c.getColumnName(4)},
                new int[]{R.id.id, R.id.nom, R.id.prenom, R.id.DateEntree}, 1);

        ls.setAdapter(adapter);


        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                int id_visiteur = ((Cursor) adapterView.getItemAtPosition(i)).getInt(0);
                h.updateVisiteurSortie(id_visiteur);
                Toast.makeText(Liste_Visiteur.this, "Merci de votre visite !", Toast.LENGTH_LONG).show();
                Intent intentAccueil = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intentAccueil);
            }
        });
    }
}