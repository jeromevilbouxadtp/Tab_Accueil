package com.example.tab_accueil;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
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
import java.util.Properties;
import javax.mail.Session;
import javax.mail.PasswordAuthentication;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
//import retrofit2.Retrofit;
//import retrofit2.Call;
//import retrofit2.converter.gson.GsonConverterFactory;
//import okhttp3.ResponseBody;




public class ActivityAvecRdv extends AppCompatActivity {

    Button btnValider, btnEnvoiMail, btnAvertir;
    EditText nomEdit, prenomEdit, societeEdit;
    TextView nomView;
    Spinner contactSpin;
    Map<String, String> MapContact = new HashMap<String, String>();
    List<String> arraycontact = new ArrayList<String>();
    String mNom ;
    String mPrenom;
    String mSociete;
    String madressdest;
    String mNomSpin;
    String mTelContact;
    SQLiteHelper sqlh = new SQLiteHelper(ActivityAvecRdv.this);
    String accessToken = "YOUR_ACCESS_TOKEN";
    String fromUser = "USER_A";
    String toUser = "USER_B";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avec_rdv);
        recupContacts();
        //Initialisation des variables

        nomEdit = (EditText) findViewById(R.id.nomEdit);
        prenomEdit = (EditText) findViewById(R.id.prenomEdit);
        societeEdit = (EditText) findViewById(R.id.societeEdit);
        //btnValider = (Button) findViewById(R.id.btnValider);

        btnEnvoiMail = (Button) findViewById(R.id.btnMail);
        btnAvertir = (Button) findViewById(R.id.btnAvertir);

        nomView = (TextView) findViewById(R.id.txtNom);

        contactSpin = (Spinner) findViewById(R.id.contactSpinner);
        //fin de déclaration

        //arrayadapter
        ArrayAdapter<String> acontact = new ArrayAdapter<String>(this , android.R.layout.simple_spinner_dropdown_item, arraycontact);
        acontact.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        contactSpin.setAdapter(acontact);

        btnEnvoiMail.setVisibility(View.INVISIBLE);


        /*btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //on récupère les informations saisies
                mNom = nomEdit.getText().toString();
                mPrenom = prenomEdit.getText().toString();
                String mNomSpin = contactSpin.getSelectedItem().toString();

                madressdest = MapContact.get(mNomSpin);
                //On affiche les informations
                nomView.setText("Bienvenue : " + mNom + " " + madressdest);
            }
        });*/

        btnAvertir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //on récupère les informations saisies
                mNom = nomEdit.getText().toString();
                mPrenom = prenomEdit.getText().toString();
                mSociete = societeEdit.getText().toString();
                mNomSpin = contactSpin.getSelectedItem().toString();

                //Configuration mail adtp

                final String username="adtp\\j.vilboux";
                final String password="3105vil+";
                String messageToSend= mPrenom + " " + mNom + "vous attend à l'accueil ";
                Properties props=new Properties();
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", "mail.adtp.com");
                props.put("mail.smtp.port","25");
                //props.put("mail.smtp.ssl.trust", "mail.adtp.com");
                //props.put("mail.smtp.connectiontimeout", "10000");

                Session session = Session.getDefaultInstance(props,
                        new javax.mail.Authenticator(){
                            protected PasswordAuthentication getPasswordAuthentication(){
                                return new PasswordAuthentication(username, password);
                            }
                        });

                //------------------------Ajout de visiteur dans la bdd----------------------//

                Date date = new Date();
                SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String c= DateFor.format(date);
                Visiteur v = new Visiteur(mNom, mPrenom, c);
                sqlh.insertVisiteur(v);
                btnEnvoiMail.setVisibility(View.VISIBLE);
                btnAvertir.setVisibility(View.INVISIBLE);
/*
                try {
                    Thread.sleep(15000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }


                Intent intentAccueil = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intentAccueil);

 */
            }
        });

        btnEnvoiMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Log.i("Send email", "");

                String[] TO = {"someone@gmail.com"};
                String[] CC = {"xyz@gmail.com"};
                //
                Intent mailIntent = new Intent(Intent.ACTION_VIEW);
                Uri data = Uri.parse("mailto:?subject=" + "RDV"+ "&body=" + prenomEdit.getText().toString() + " " + nomEdit.getText().toString() + " vous attends"+ "&to=" + "destination@mail.com");
                mailIntent.setData(data);
                startActivity(Intent.createChooser(mailIntent, "Send mail..."));
                //
*/
                //Test nouvel solution envoi mail



                //madressdest = MapContact.get(mNomSpin);
                mTelContact = MapContact.get(mNomSpin);
//                Toast.makeText(getApplicationContext(),madressdest, Toast.LENGTH_LONG).show();
                //Configuratioon Gmail
                /*final String username="jvilboux@gmail.com";
                final String password="pczmprpjluxlhpev";
                String messageToSend= mPrenom + " " + mNom + "vous attend à l'accueil ";
                Properties props=new Properties();
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.port","587");*/



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

                    //Passage d'un appel téléphonique
                    //Intent callIntent = new Intent(Intent.ACTION_CALL);
                    //callIntent.setData(Uri.parse("tel:"+ mTelContact));
                    //startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+ mTelContact)));

                }

                catch (MessagingException e)
                {
                    throw new RuntimeException(e);
                }
*/
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+ mTelContact)));

                //retour au menu Accueil
                /*
                String toUser = "antoine Viard";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("msteams://call?id=" + toUser));
                startActivity(intent);
                */

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





        /*----------------------------------------------------------------------------*/
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