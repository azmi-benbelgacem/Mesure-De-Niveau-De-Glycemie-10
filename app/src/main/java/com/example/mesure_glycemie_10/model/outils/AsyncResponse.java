package com.example.mesure_glycemie_10.model.outils;

import org.json.JSONException;

public interface AsyncResponse {
    void processFinish (String output) throws JSONException;
}
