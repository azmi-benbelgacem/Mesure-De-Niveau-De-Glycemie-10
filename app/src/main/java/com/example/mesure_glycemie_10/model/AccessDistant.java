package com.example.mesure_glycemie_10.model;

import android.util.Log;

import com.example.mesure_glycemie_10.controller.Controller;
import com.example.mesure_glycemie_10.model.outils.AccesHTTP;
import com.example.mesure_glycemie_10.model.outils.AsyncResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class AccessDistant implements AsyncResponse {

    // constante
    private static final String SERVERADDR= "http://192.168.194.51/mgappdb/serveurmgapp.php";

    private Controller controller;


    public AccessDistant(){
        super();
        controller = Controller.getInstance(null);
    }
    /**
     * retour du serveur distant
     * @param output
     */
    @Override
    public void processFinish(String output) throws JSONException {
        Log.d("serveur","***************"+output);
        // découpage du message réçu avec %
        String[] message = output.split("%");
        // dans message[0] : "enreg", "dernier", "Erreur !"
        // dans message[1] : reste du message
        // s'il y a 2 cases
        if(message.length>1){
            if(message[0].equals("enreg")){
                Log.d("enreg","***************"+message[1]);

            }else if(message[0].equals("dernier"))
                {
                    Log.d("dernier","***************"+message[1]);
                    JSONObject info = new JSONObject(message[1]);
                    int age = info.getInt("age");
                    boolean jeune = info.getBoolean("jeune");
                    Double valeur = info.getDouble("valeur");
                    //String datemesure = info.getString("datemesure");

                    Patient patient = new Patient(new Date(),age, valeur.floatValue(),jeune);
                   // homeControler.setPlayers(players);
                }else if(message[0].equals("Erreur !"))
                {
                    Log.d("Erreur !","***************"+message[1]);
                }
        }
    }

    public void envoi (String operation, JSONArray lesDonneesJSON){
        AccesHTTP accesDonnees = new AccesHTTP();
        // lien de délégation
        accesDonnees.delegate = this;
        // ajout des paramètres
        accesDonnees.addParam("operation",operation);
        accesDonnees.addParam("lesdonnees",lesDonneesJSON.toString());
        // appel au serveur
        accesDonnees.execute(SERVERADDR);
    }
}
