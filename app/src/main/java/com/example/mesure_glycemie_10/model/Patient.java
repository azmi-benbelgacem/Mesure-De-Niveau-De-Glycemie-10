package com.example.mesure_glycemie_10.model;

import org.json.JSONArray;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class Patient {

        private Date dateMesure;
        private int age;
        private float valeurMesuree;
        private boolean isFasting;
        private static String reponse;

    public Patient() {
    }

    //Flèche "Update" Controller --> Model
        public Patient(Date date, int age, float valeurMesuree, boolean isFasting) {
            dateMesure = date;
            this.age = age;
            this.valeurMesuree = valeurMesuree;
            this.isFasting = isFasting;
            calculer();

        }
        private void calculer ()
        {
            if(isFasting) {
                if (age >= 13) {
                    if (valeurMesuree < 5.0)
                        reponse = "Niveau de glycémie est trop bas";
                    else if (valeurMesuree >= 5.0 && valeurMesuree <= 7.2)
                        reponse = "Niveau de glycémie est normale";
                    else
                        reponse = "Niveau de glycémie est trop élevé";
                } else if (age >= 6 && age <= 12) {
                    if (valeurMesuree < 5.0)
                        reponse = "Niveau de glycémie est trop bas";
                    else if (valeurMesuree >= 5.0 && valeurMesuree <= 10.0)
                        reponse = "Niveau de glycémie est normale";
                    else
                        reponse = "Niveau de glycémie est trop élevé";
                } else if (age < 6) {
                    if (valeurMesuree < 5.5)
                        reponse = "Niveau de glycémie est trop bas";
                    else if (valeurMesuree >= 5.5 && valeurMesuree <= 10.0)
                        reponse = "Niveau de glycémie est normale";
                    else
                        reponse = "Niveau de glycémie est trop élevé";
                }
            } else {
                if (valeurMesuree < 5.5)
                    reponse = "Niveau de glycémie est trop bas";
                else if (valeurMesuree > 10.5)
                    reponse = "Niveau de glycémie est trop élevé";
                else
                    reponse = "ce niveau de glycémie est normale après les repas";
            }
        }
        public int getAge() {
            return age;
        }
        public float getValeurMesuree() {
            return valeurMesuree;
        }
        public boolean isFasting() {
            return isFasting;
        }

        public Date getDate() {
        return dateMesure;
    }


        //Flèche "Notify" Model --> Controller
        public String getReponse() {
            return reponse;
        }


    public void setAge(int age) {
        this.age = age;
    }

    public void setValeurMesuree(float valeurMesuree) {
        this.valeurMesuree = valeurMesuree;
    }

    public void setFasting(boolean fasting) {
        isFasting = fasting;
    }

    /**
     * conversion du patient en format JSONArray
     * @return
     */
    public JSONArray convertToJSONArray (){
        List laListe = new ArrayList<>();
        laListe.add(dateMesure);
        laListe.add(age);
        laListe.add(isFasting);
        laListe.add(valeurMesuree);
        return new JSONArray(laListe);
    }



}
