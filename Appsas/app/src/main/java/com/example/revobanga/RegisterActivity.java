package com.example.revobanga;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    EditText Email,Password,Username,Name;
    Button registerButton,loginButton;
    FirebaseAuth firebaseAuth;
    DatabaseReference userRef;
    String currentUserID;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Email =  findViewById(R.id.email);
        Password = (EditText) findViewById(R.id.password);
        Username=findViewById(R.id.username);
        Name=findViewById(R.id.RegName);
        registerButton = (Button) findViewById(R.id.btnRegister);
        loginButton = (Button) findViewById(R.id.btnLinkToLoginScreen);

        firebaseAuth = FirebaseAuth.getInstance();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Email.getText().toString();
                String password = Password.getText().toString();
                final String username=Username.getText().toString();
                final String name=Name.getText().toString();
                if(TextUtils.isEmpty(username)){
                    Toast.makeText(getApplicationContext(),"Please fill in the required fields",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(name)){
                    Toast.makeText(getApplicationContext(),"Please fill in the required fields",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(),"Please fill in the required fields",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(),"Please fill in the required fields",Toast.LENGTH_SHORT).show();
                }

                if(password.length()<6){
                    Toast.makeText(getApplicationContext(),"Password must be at least 6 characters",Toast.LENGTH_SHORT).show();
                }
                firebaseAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    saveInfo(username,name);
                                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                    finish();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),"E-mail is wrong",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });

        if(firebaseAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }

        MainActivity.setHideKeyboardOnTouch(this,findViewById(R.id.registerparent));

    }
    public void saveInfo(String username,String name){
currentUserID=firebaseAuth.getCurrentUser().getUid();
        userRef= FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);
        HashMap userMap=new HashMap();
        userMap.put("username",username);
        userMap.put("fullname",name);
        userMap.put("Gender","none");
userRef.updateChildren(userMap);
    }
}



//public class RegisterActivity extends AppCompatActivity {
//    private static final String TAG = RegisterActivity.class.getSimpleName();
//
//    private Button btnRegister;
//    private Button btnLinkToLogin;
//    private EditText inputFullName;
//    private EditText inputEmail;
//    private EditText inputPassword;
//
//    private ProgressDialog pDialog;
//    private SessionManager session;
//    private SQLiteHandler db;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_register);
//
//        inputFullName = (EditText) findViewById(R.id.name);
//        inputEmail = (EditText) findViewById(R.id.email);
//        inputPassword = (EditText) findViewById(R.id.password);
//        btnRegister = (Button) findViewById(R.id.btnRegister);
//        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);
//
//        // Progress dialog
//        pDialog = new ProgressDialog(this);
//        pDialog.setCancelable(false);
//
//        // Session manager
//        session = new SessionManager(getApplicationContext());
//
//        // SQLite database handler
//        db = new SQLiteHandler(getApplicationContext());
//
//        // Check if user is already logged in or not
//        if (session.isLoggedIn()) {
//            // User is already logged in. Take him to main activity
//            Intent intent = new Intent(RegisterActivity.this,
//                    MainActivity.class);
//            startActivity(intent);
//            finish();
//        }
//
//        // Register Button Click event
//        btnRegister.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                String name = inputFullName.getText().toString().trim();
//                String email = inputEmail.getText().toString().trim();
//                String password = inputPassword.getText().toString().trim();
//
//                if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
//                    registerUser(name, email, password);
//                } else {
//                    Toast.makeText(getApplicationContext(),
//                            "Please enter your details!", Toast.LENGTH_LONG)
//                            .show();
//                }
//                InputMethodManager inputManager = (InputMethodManager)
//                        getSystemService(Context.INPUT_METHOD_SERVICE);
//
//                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
//                        InputMethodManager.HIDE_NOT_ALWAYS);
//            }
//
//        });
//
//        // Link to Login Screen
//        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View view) {
//                Intent i = new Intent(getApplicationContext(),
//                        LoginActivity.class);
//                startActivity(i);
//                finish();
//            }
//        });
//
//    }
//
//    /**
//     * Function to store user in WordPress API will post params(tag, name,
//     * email, password) to register url
//     * */
//    private void registerUser(final String name, final String email,
//                              final String password) {
//        // Tag used to cancel the request
//        String tag_string_req = "req_register";
//
//        pDialog.setMessage("Registering ...");
//        showDialog();
//
//        StringRequest strReq = new StringRequest(Request.Method.POST,
//                AppConfig.URL + AppConfig.ENDPOINT_REGISTER, new Response.Listener<String>() {
//
//            @Override
//            public void onResponse(String response) {
//                Log.d(TAG, "Register Response: " + response.toString());
//                hideDialog();
//
//                try {
//                    JSONObject jObj = new JSONObject(response);
//                    int status = jObj.getInt("status");
//                    if (status == 2){
//                        // User successfully stored in WordPress
//
//                        // Now, storing the user in sqlite
//                        String user_id = jObj.getString("user_id");
//
//                        JSONObject user = jObj.getJSONObject("user");
//                        String name = user.getString("name");
//                        String email = user.getString("email");
//                        String created_at = user
//                                .getString("created_at");
//
//                        // Inserting row in users table
//                        db.addUser(name, email, user_id, created_at);
//
//                        Toast.makeText(getApplicationContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();
//
//                        // Launch login activity
//                        Intent intent = new Intent(
//                                RegisterActivity.this,
//                                LoginActivity.class);
//                        startActivity(intent);
//                        finish();
//                    }
//                    else if (status == 1){
//
//                        // Error occurred in registration. Get the error
//                        // message
//                        String errorMsg = "Error";
//                        Toast.makeText(getApplicationContext(),
//                                errorMsg, Toast.LENGTH_LONG).show();
//                    }
//                    else{
//
//                        // Error occurred in registration. Get the error
//                        // message
//                        String errorMsg = "Error";
//                        Toast.makeText(getApplicationContext(),
//                                errorMsg, Toast.LENGTH_LONG).show();
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e(TAG, "Registration Error: " + error.getMessage());
//                Toast.makeText(getApplicationContext(),
//                        error.getMessage(), Toast.LENGTH_LONG).show();
//                hideDialog();
//            }
//        }) {
//
//            @Override
//            protected Map<String, String> getParams() {
//                // Posting params to register url
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("username", name);
//                params.put("email", email);
//                params.put("pass", password);
//
//                return params;
//            }
//
//        };
//
//        // Adding request to request queue
//        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
//    }
//
//    private void showDialog() {
//        if (!pDialog.isShowing())
//            pDialog.show();
//    }
//
//    private void hideDialog() {
//        if (pDialog.isShowing())
//            pDialog.dismiss();
//    }
//}