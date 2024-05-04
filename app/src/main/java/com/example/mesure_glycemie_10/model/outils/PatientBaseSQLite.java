package com.example.mesure_glycemie_10.model.outils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class PatientBaseSQLite extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "table_patients";
    private static final String COL_DATE_MESURE = "DATE_MESURE";
    private static final String COL_AGE = "AGE";
    private static final String COL_VALEUR_MESUREE = "VALEUR_MESUREE";
    private static final String COL_IS_FASTING = "IS_FASTING";

    private static final String CREATE_TAB = "CREATE TABLE "+ TABLE_NAME +" ("
            + COL_DATE_MESURE+ " TEXT PRIMARY KEY, "
            + COL_AGE+ " INTEGER NOT NULL, "
            + COL_VALEUR_MESUREE+ " REAL NOT NULL, "
            + COL_IS_FASTING+ " INTEGER NOT NULL CHECK ("+ COL_IS_FASTING +" IN (0, 1)));";


    public PatientBaseSQLite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }


    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TAB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE " + TABLE_NAME);
        onCreate(db);
    }
}

