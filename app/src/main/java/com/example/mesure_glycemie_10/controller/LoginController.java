package com.example.mesure_glycemie_10.controller;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.mesure_glycemie_10.model.User;

public class LoginController {
    private static LoginController instance = null;
    private static final String SHARED_PREFS = "mySharedPrefs";
    private static User user;
    private LoginController(){
        super();
    }
    public static final LoginController getInstance(Context context){
        if(LoginController.instance ==null){
            LoginController.instance = new LoginController();
        }
        recapUser(context);
        return LoginController.instance;
    }
    public void createUser(String userEmail, String password, Context context){
        user = new User(userEmail,password);
        SharedPreferences sharedPreferences= context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", userEmail);
        editor.putString("password", password);
        editor.apply();
    }
    public static void recapUser(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("email","");
        String password = sharedPreferences.getString("password","");
        user = new User(userEmail,password);
    }
    public String getUserEmail() {
        return user.getUserEmail();
    }

    public String getPassword() {
        return user.getPassword();
    }
}
