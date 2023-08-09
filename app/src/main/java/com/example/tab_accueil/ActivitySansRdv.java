package com.example.tab_accueil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Calendar;


public class ActivitySansRdv extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button btnValider, btnEnreg;
    EditText nomEdit, prenomEdit, societeEdit;
    TextView nomView;
    Spinner motifSpin;
    String mNom;
    String mPrenom;
    String mSociete;
    String mMotif;
    Map<String, String> MapContact = new HashMap<String, String>();
    List<String> arraycontact = new ArrayList<String>();

    String mTelContactDefaut = "0450102690";


    SQLiteHelper h = new SQLiteHelper(ActivitySansRdv.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sans_rdv);

        //Initialisation des variables

        motifSpin = (Spinner) findViewById(R.id.motifSpinner);
        motifSpin.setOnItemSelectedListener(this);

        nomEdit = (EditText) findViewById(R.id.nomEdit);
        prenomEdit = (EditText) findViewById(R.id.prenomEdit);
        societeEdit = (EditText) findViewById(R.id.societeEdit);

        btnValider = (Button) findViewById(R.id.btnValider);
        btnEnreg = (Button) findViewById(R.id.btnAvertirSansRdv);

        nomView = (TextView)findViewById(R.id.txtNom);
        recupContacts();
        //arrayadapter
        ArrayAdapter aa = ArrayAdapter.createFromResource(this, R.array.motif, android.R.layout.simple_spinner_item);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        motifSpin.setAdapter(aa);
        motifSpin.setOnItemSelectedListener(this);

        btnValider.setVisibility(View.INVISIBLE);

        btnEnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNom = nomEdit.getText().toString();
                mPrenom = prenomEdit.getText().toString();
                mSociete = societeEdit.getText().toString();
                mMotif = motifSpin.getSelectedItem().toString();

                final String username="adtp\\j.vilboux";
                final String password="3105vil+";
                String messageToSend= mPrenom + " " + mNom + "vous attend à l'accueil ";
                Properties props=new Properties();
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", "mail.adtp.com");
                props.put("mail.smtp.port","25");

                Session session = Session.getDefaultInstance(props,
                        new javax.mail.Authenticator(){
                            protected PasswordAuthentication getPasswordAuthentication(){
                                return new PasswordAuthentication(username, password);
                            }
                        });
                /*
                try {
                    Message message = new MimeMessage(session);
                    //configuration gmail
                    //message.setFrom(new InternetAddress(username));
                    //configuration exchange
                    message.setFrom(new InternetAddress("j.vilboux@adtp.com"));
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("j.vilboux@adtp.com"));
                    message.setSubject("RDV est arrivé !");
                    message.setText(messageToSend);
                    Transport.send(message);
                    Toast.makeText(getApplicationContext(),"email ok", Toast.LENGTH_LONG).show();

                }
                catch (MessagingException e)
                {
                    throw new RuntimeException(e);
                }
*/
                //------------------------Ajout de visiteur dans la bdd----------------------//

                Date date = new Date();
                SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String c= DateFor.format(date);
                Visiteur v = new Visiteur(mNom, mPrenom, c);
                h.insertVisiteur(v);

                btnValider.setVisibility(View.VISIBLE);
                btnEnreg.setVisibility(View.INVISIBLE);
/*
                try {
                    Thread.sleep(15000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Intent intentAccueil = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intentAccueil);

 */
                /*----------------------------------------------------------------------------*/

                //retour au menu Accueil
                //Intent intentAccueil = new Intent(getApplicationContext(), MainActivity.class);
                //startActivity(intentAccueil);
            }
        });

        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Appel du contact

                mTelContactDefaut = MapContact.get("Accueil_Menoge");
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+ mTelContactDefaut)));


                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Intent intentAccueil = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intentAccueil);
            }
        });

        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


    }




    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String selectedMotif = motifSpin.getSelectedItem().toString();
        Toast.makeText(this,selectedMotif, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    //Récupération des contact du téléphone
    public void recupContacts(){
        //accès au contenu du mobile
        ContentResolver contentResolver = this.getContentResolver();
        //Récupération des contacts dans un curseur
        Cursor cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_ALTERNATIVE, ContactsContract.CommonDataKinds.Phone.NUMBER}, null, null, null);
        //Cursor cursor = contentResolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, new String[]{ContactsContract.CommonDataKinds.Email.DISPLAY_NAME_ALTERNATIVE, ContactsContract.CommonDataKinds.Email.ADDRESS}, null, null, null);
        if (cursor == null){
            Log.d("recup","Error curseur");
        }else {
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