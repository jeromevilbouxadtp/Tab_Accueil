package com.example.tab_accueil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;


public class SQLiteHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME="MySQLite";
    protected static final String TABLE_VISITEUR = "visiteurs";
    public static final String KEY_ID = "id";
    public static final String KEY_NOM= "name";
    public static final String KEY_PRENOM = "prenom";

    public SQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase  db){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VISITEUR);
        String CREATE_VISITEURS_TABLE = "CREATE TABLE visiteurs (_id INTEGER PRIMARY KEY, nom TEXT, prenom TEXT, dateentree TEXT, datesortie TEXT)";
        db.execSQL(CREATE_VISITEURS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VISITEUR);
        onCreate(db);
    }

    public void insertVisiteur(Visiteur v)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("nom", v.getNom());
        cv.put("prenom", v.getPrenom());
        cv.put("dateentree", v.getDateEntree());

        db.insert("visiteurs", null,cv);
        db.close();
    }

    public void updateVisiteur(Visiteur v)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nom", v.getNom());
        cv.put("prenom", v.getPrenom());

        db.update("visiteurs", cv, "_id=?", new String[] {String.valueOf(v.getId())});
        db.close();
    }

    public void deleteVisiteur(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("visiteurs", "_id=?",  new String[]{String.valueOf(id)});
        db.close();
    }

    public Cursor getAllVisiteurs()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM visiteurs", null);
        return c;

    }

    public Cursor getAllVisiteursPresents()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM visiteurs where datesortie IS NULL", null);
        return c;

    }

    public void updateVisiteurSortie(int id_visiteur)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        Date datesortie = new Date();
        SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String c= DateFor.format(datesortie);
        cv.put("datesortie", String.valueOf(datesortie));

        db.update("visiteurs", cv, "_id=?", new String[]{String.valueOf(id_visiteur)});
        db.close();
    }


    public Visiteur getOneVisiteur(int id)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor c=db.query("visiteurs", new String[]{"_id", "nom", "prenom"}, "_id=?",
                new String[]{String.valueOf(id)}, null, null,null);
        c.moveToFirst();
        Visiteur v = new Visiteur(c.getInt(0), c.getString(1), c.getString(2), c.getString(3));
        return v;

    }
}
