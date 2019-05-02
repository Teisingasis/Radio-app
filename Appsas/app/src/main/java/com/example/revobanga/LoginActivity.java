package com.example.revobanga;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    public String user;
    EditText username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Login();
        MainActivity.name=user;
    }

    private void Login() {
        Button logIn = findViewById(R.id.button);
        username=findViewById(R.id.login);

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user=username.getText().toString();
                MainActivity.name=user;
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });
    }
}
