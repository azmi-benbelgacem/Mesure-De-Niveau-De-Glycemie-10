package com.example.mesure_glycemie_10.controller;
import android.content.Context;
import android.util.Log;

import com.example.mesure_glycemie_10.model.AccessDistant;
import com.example.mesure_glycemie_10.model.AccessLocal;
import com.example.mesure_glycemie_10.model.Patient;

import org.json.JSONArray;

import java.util.Date;

public class Controller {
    private static Controller instance = null;
    private static Patient patient;

    //private static AccessDistant accessDistant;
    private static AccessLocal accessLocal;
    private Controller(){
        super();
    }
    public static final Controller getInstance( Context context){
        if(Controller.instance ==null){
            Controller.instance = new Controller();
            accessLocal=new AccessLocal(context);
            //récuper
            patient = accessLocal.getPatient();

            //accessDistant = new AccessDistant();
            //accessDistant.envoi("dernier", new JSONArray());
        }
        return Controller.instance;
    }
    public void createPatient(int age, float valeurMesuree, boolean isFasting){
        patient = new Patient(new Date(), age, valeurMesuree, isFasting);

        accessLocal.insertPatient(patient);

        //accessDistant.envoi("enreg", patient.convertToJSONArray());
    }

    public String getReponse() {
        return patient.getReponse();
    }

    public static Patient getPatient() {
        return patient;
    }
}
