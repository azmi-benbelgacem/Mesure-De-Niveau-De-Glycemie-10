package com.example.mesure_glycemie_10.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mesure_glycemie_10.R;
import com.example.mesure_glycemie_10.controller.Controller;

public class MainActivity extends AppCompatActivity {

    private final int REQUEST_CODE = 1;
    private EditText etValeur;
    private TextView tvAge;
    private SeekBar sbAge;
    private RadioButton rbIsFasting, rbIsNotFasting;
    private Button btnConsulter;
    private Controller controller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        if(controller.getPatient()!= null) {
            sbAge.setProgress(controller.getPatient().getAge());
            tvAge.setText("Votre âge : " + sbAge.getProgress());

            etValeur.setText(String.valueOf(controller.getPatient().getValeurMesuree()));
            if (controller.getPatient().isFasting())
                rbIsFasting.setChecked(true);
            else
                rbIsNotFasting.setChecked(true);
        }

        sbAge.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("Information", "onProgressChanged "+progress);
                tvAge.setText("Votre âge : "+ progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar){}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar){}

        });

        btnConsulter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int age;
                float valeur;

                Log.i("Information", "button btnConsulter cliqué");
                boolean verifAge = false, verifValeur = false;

                if(sbAge.getProgress()!=0)
                    verifAge = true;
                else
                    Toast.makeText(MainActivity.this, "Veuillez saisir votre age !", Toast.LENGTH_SHORT).show();

                if(!etValeur.getText().toString().isEmpty())
                    verifValeur = true;
                else
                    Toast.makeText(MainActivity.this, "Veuillez saisir votre valeur mesurée !", Toast.LENGTH_LONG).show();

                if(verifAge && verifValeur)
                {
                    age = sbAge.getProgress();
                    valeur = Float.valueOf(etValeur.getText().toString());

                    //Flèche "UserAction" View --> Controller
                    controller.createPatient(age, valeur, rbIsFasting.isChecked());

                    //Flèche "Notify"  Controller --> View

                    Intent intent = new Intent (MainActivity.this, ConsultActivity.class);
                    intent.putExtra("reponse",controller.getReponse());
                    startActivityForResult(intent, REQUEST_CODE);

                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE)
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(MainActivity.this, "ERROR : RESULT_CANCELED", Toast.LENGTH_SHORT).show();
            }
    }
    private void init()
    {
        controller = Controller.getInstance(getApplicationContext());

        sbAge = findViewById(R.id.sbAge);
        tvAge = findViewById(R.id.tvAge);
        etValeur = findViewById(R.id.etValeur);
        rbIsFasting = findViewById(R.id.rbtOui);
        btnConsulter = findViewById(R.id.btnConsulter);
        rbIsNotFasting = findViewById(R.id.rbtNon);
    }
}
