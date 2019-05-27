package com.example.revobanga;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingsActivityN extends AppCompatActivity {

    Button deleteButton;

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
        deleteButton = (Button)findViewById(R.id.rm_acc_button);

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

        delete();

    }

    private void delete(){
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(SettingsActivityN.this);
                dialog.setTitle("Are you sure?");
                dialog.setMessage("Deleting this account will result in completely removing your profile from the system.");
                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(SettingsActivityN.this, "Account Deleted", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(SettingsActivityN.this, LoginActivity.class));
                                    finish();
                                }
                                else{
                                    Toast.makeText(SettingsActivityN.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                    }
                });

                dialog.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = dialog.create();
                alertDialog.show();


            }
        });
    }
}
