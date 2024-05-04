package com.example.mesure_glycemie_10.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mesure_glycemie_10.R;


public class ConsultActivity extends AppCompatActivity {
    private TextView tvReponse;
    private String reponse;
    private Button btnReturn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult);
        init();
        Intent intent = getIntent();
        reponse = intent.getStringExtra("reponse");
        tvReponse.setText(reponse);

        btnReturn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                if(reponse != null)
                {
                    setResult(RESULT_OK,intent);
                }
                else
                {
                    setResult(RESULT_CANCELED,intent);
                }
                finish();
            }
        });
    }
    private void init() {
        tvReponse = findViewById(R.id.tvReponse);
        btnReturn = findViewById(R.id.btnReturn);
    }
}