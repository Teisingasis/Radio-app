package com.example.revobanga;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class SettingsActivityN extends AppCompatActivity {

    TextView tekstas;
    Switch colorSw;
    ConstraintLayout ekranas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ekranas = (ConstraintLayout)findViewById(R.id.ekranas);
        tekstas = findViewById(R.id.spalvos_tekstas);
        colorSw = (Switch)findViewById(R.id.color_switch);

        colorSw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    ekranas.setBackgroundColor(Color.GREEN);
                    tekstas.setText("Ne baltas");
                }
                else{
                    ekranas.setBackgroundColor(Color.WHITE);
                    tekstas.setText("Baltas");
                }
            }
        });

    }
}
