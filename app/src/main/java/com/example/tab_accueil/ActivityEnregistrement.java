package com.example.tab_accueil;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityEnregistrement extends AppCompatActivity {
    Button btnEnregistrement;
    EditText nomEdit, prenomEdit, societeEdit;
    TextView nomView;
    Spinner contactSpin;
    Map<String, String> MapContact = new HashMap<String, String>();
    List<String> arraycontact = new ArrayList<String>();
    String mNom;
    String mPrenom;
    String mSociete;
    String mNomSpin;
    SQLiteHelper sqlh = new SQLiteHelper(ActivityEnregistrement.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enregistrement);
        recupContacts();
        //Initialisation des variables

        nomEdit = (EditText) findViewById(R.id.nomEdit);
        prenomEdit = (EditText) findViewById(R.id.prenomEdit);
        societeEdit = (EditText) findViewById(R.id.societeEdit);
        //btnValider = (Button) findViewById(R.id.btnValider);

        btnEnregistrement = (Button) findViewById(R.id.btnEnregistrer);

        nomView = (TextView) findViewById(R.id.txtNom);

        contactSpin = (Spinner) findViewById(R.id.contactSpinner);
        //fin de déclaration

        //arrayadapter
        ArrayAdapter<String> acontact = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arraycontact);
        acontact.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        contactSpin.setAdapter(acontact);

        btnEnregistrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //on récupère les informations saisies
                mNom = nomEdit.getText().toString();
                mPrenom = prenomEdit.getText().toString();
                mSociete = societeEdit.getText().toString();
                mNomSpin = contactSpin.getSelectedItem().toString();

                //------------------------Ajout de visiteur dans la bdd----------------------//

                Date date = new Date();
                SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String c = DateFor.format(date);
                Visiteur v = new Visiteur(mNom, mPrenom, c);
                sqlh.insertVisiteur(v);

                Toast.makeText(ActivityEnregistrement.this, "Votre entrée est enregistrée !", Toast.LENGTH_LONG).show();
                Intent intentAccueil = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intentAccueil);
            }
        });
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);






                /*----------------------------------------------------------------------------*/
            }

            public void recupContacts() {
                //accès au contenu du mobile
                ContentResolver contentResolver = this.getContentResolver();
                //Récupération des contacts dans un curseur
                Cursor cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_ALTERNATIVE, ContactsContract.CommonDataKinds.Phone.NUMBER}, null, null, null);
                //Cursor cursor = contentResolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, new String[]{ContactsContract.CommonDataKinds.Email.DISPLAY_NAME_ALTERNATIVE, ContactsContract.CommonDataKinds.Email.ADDRESS}, null, null, null);
                if (cursor == null) {
                    Log.d("recup", "Error curseur");
                } else {
                    //EditText lstContacts = (EditText) findViewById(R.id.Lstcontact);
                    //parcours des contacts

                    arraycontact.clear();
                    MapContact.clear();
                    while (cursor.moveToNext() == true) {
                        String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DISPLAY_NAME_ALTERNATIVE));
                        //String mail = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));
                        String numtel = cursor.getString((cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                        MapContact.put(name, numtel);
                        arraycontact.add(name);
                        //lstContacts.setText(lstContacts.getText().toString() + "\n\r" + name + " : " + phone);
                    }
                    cursor.close();


                    //contactSpin.setOnItemSelectedListener(this);
                }


            }
        }
