package com.example.mesure_glycemie_10.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.Log;

import com.example.mesure_glycemie_10.model.outils.PatientBaseSQLite;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AccessLocal {

    private static final int VERSION = 1;
    private static final String NOM_BDD = "MGSQLiteDatabase.db";
    private static final String TABLE_NAME = "table_patients";
    private static final String COL_DATE_MESURE = "DATE_MESURE";
    private static final int NUM_COL_DATE_MESURE = 0;
    private static final String COL_AGE = "AGE";
    private static final int NUM_COL_AGE = 1;
    private static final String COL_VALEUR_MESUREE = "VALEUR_MESUREE";
    private static final int NUM_COL_VALEUR_MESUREE = 2;
    private static final String COL_IS_FASTING = "IS_FASTING";
    private static final int NUM_COL_IS_FASTING = 3;


    private SQLiteDatabase bd;
    private PatientBaseSQLite patients;

    public AccessLocal(Context context) {
        patients = new PatientBaseSQLite (context, NOM_BDD, null, VERSION);
    }
    public void openForWrite() {
        bd = patients.getWritableDatabase();
    }
    public void openForRead() {
        bd = patients.getReadableDatabase();
    }
    public void close() {
        bd.close();
    }
    public SQLiteDatabase getBd() {
        return bd;
    }

    public void insertPatient (Patient patient) {

        // COL_DATE_MESURE : TEXT PRIMARY KEY
        // COL_AGE :  INTEGER NOT NULL
        // COL_VALEUR_MESUREE : REAL NOT NULL
        // COL_IS_FASTING : INTEGER NOT NULL (0,1)

        openForWrite();
        ContentValues content = new ContentValues ();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String dateString = dateFormat.format(patient.getDate());
        content.put(COL_DATE_MESURE, dateString);


        content.put (COL_AGE, patient.getAge());
        content.put (COL_VALEUR_MESUREE, patient.getValeurMesuree());

        if(patient.isFasting())
            content.put (COL_IS_FASTING, 1);
        else
            content.put (COL_IS_FASTING, 0);

        long result = bd.insert(TABLE_NAME, null, content);
        if (result == -1) {
            Log.e("Error", "Insertion failed");
        } else {
            Log.i("Info", "Insertion successful");
        }

        close();
    }

    public Patient getPatient () {

        // COL_DATE_MESURE : TEXT PRIMARY KEY
        // COL_AGE :  INTEGER NOT NULL
        // COL_VALEUR_MESUREE : REAL NOT NULL
        // COL_IS_FASTING : INTEGER NOT NULL (0,1)

        openForRead();
        Cursor c = bd.query(
                TABLE_NAME,
                new String[] {COL_DATE_MESURE, COL_AGE, COL_VALEUR_MESUREE, COL_IS_FASTING},
                null, // selection: filter which rows to return; null returns all rows
                null, // selectionArgs: arguments for the selection; null if no arguments
                null, // groupBy: grouping the results; null means no grouping
                null, // having: conditions for the groups; null if no conditions
                COL_DATE_MESURE + " DESC", // orderBy: order the results by date_mesure in descending order
                "1" // limit: limit the results to just one row (the most recent one)
        );

        return cursorToChapter (c);
    }


    public Patient cursorToChapter (Cursor c) { // version 1
        if (c.getCount() == 0) {
            c.close();
            return null;
        }

        if (c != null && c.moveToFirst()) {

            int age = c.getInt(c.getColumnIndexOrThrow(COL_AGE));
            float valeurMesuree = c.getFloat(c.getColumnIndexOrThrow(COL_VALEUR_MESUREE));

            boolean isFasting = c.getInt(c.getColumnIndexOrThrow(COL_IS_FASTING)) == 1;


            Patient lastPatient = new Patient(new Date(), age, valeurMesuree, isFasting);
            c.close();
            return lastPatient;
        }
        return null;
    }


    /*
    public Patient getPatient() // version 2
    {
        // COL_DATE_MESURE : TEXT PRIMARY KEY
        // COL_AGE :  INTEGER NOT NULL
        // COL_VALEUR_MESUREE : REAL NOT NULL
        // COL_IS_FASTING : INTEGER NOT NULL (0,1)

        openForRead();
        Patient patient = null;

        String req = "select * from "+ TABLE_NAME;
        Cursor curseur = bd.rawQuery(req, null); // lire ligne par ligne
        curseur.moveToLast(); // se possitionner sur la dernière ligne du table
        if (!curseur.isAfterLast()) {
            int age = curseur.getInt(1);
            float valeur_mesure = curseur.getFloat(2);
            boolean is_fasting = false;
            if(curseur.getInt(3)==1)
                is_fasting = true;

            patient = new Patient(new Date(),age,valeur_mesure,is_fasting);
        }

        close();
        return patient;
    }

    */


}
