package com.example.tab_accueil;

import java.sql.Date;

public class Visiteur {

    int id;
    String Nom;
    String Prenom;
    String dateEntree;
    String dateSortie;


    public Visiteur(int id, String Nom, String Prenom, String dateEntree)
    {
        this.id = id;
        this.Nom = Nom;
        this.Prenom = Prenom;
        this.dateEntree = dateEntree;

    }

    public Visiteur(String Nom, String Prenom, String dateEntree)
    {
        this.Nom = Nom;
        this.Prenom = Prenom;
        this.dateEntree = dateEntree;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return Nom;
    }

    public String getDateEntree() {
        return dateEntree;
    }

    public void setNom(String nom) {
        this.Nom = nom;
    }

    public String getPrenom() {
        return Prenom;
    }



    public void setPrenom(String prenom) {
        Prenom = prenom;
    }

    public void setDateEntree(String dateEntree) {
        this.dateEntree = dateEntree;
    }
    public String getDateSortie() {
        return dateSortie;
    }

    public void setDateSortie(String dateSortie) {
        this.dateSortie = dateSortie;
    }



}
