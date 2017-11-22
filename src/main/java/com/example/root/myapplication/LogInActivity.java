package com.example.root.myapplication;

import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class LogInActivity extends AppCompatActivity {

    Context context;
    EditText usernameEditText, passwordEditText;
    Button submitButton, signUpButton, forgetPasswordButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = LogInActivity.this;

        usernameEditText = findViewById(R.id.logInUsernameEditText);
        passwordEditText = findViewById(R.id.logInPasswordEditText);
        submitButton = findViewById(R.id.logInSubmitButton);
        signUpButton = findViewById(R.id.logInGoToSignUpButton);
        forgetPasswordButton = findViewById(R.id.logInForgetPasswordButton);

        final String url = "http://mehrdadseyfi.ir/pay/login.php";
        final RequestParams params = new RequestParams();


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(usernameEditText.getText().toString().equals("") || passwordEditText.getText().toString().equals("")){
                    Toast.makeText(context, "Please enter your info", Toast.LENGTH_SHORT).show();
                } else {
                    params.put("username", usernameEditText.getText().toString());
                    params.put("password", passwordEditText.getText().toString());
                    AsyncHttpClient client = new AsyncHttpClient();
                    client.post(url, params, new TextHttpResponseHandler() {
                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            Toast.makeText(context, throwable.toString(), Toast.LENGTH_LONG).show();
                        }
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String responseString) {
                            switch (responseString) {
                                case "registered" :
                                    PreferenceManager.getDefaultSharedPreferences(context).edit()
                                            .putString("username", usernameEditText.getText().toString()).commit();
                                    Intent intent = new Intent(context, MainMenuActivity.class);
                                    startActivity(intent);
                                    break;
                                case "wrong password" :
                                    Toast.makeText(context, "Wrong passWord", Toast.LENGTH_SHORT).show();
                                    break;
                                case "not registered" :
                                    Toast.makeText(context, "Not registered yet", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                    });
                }
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SignUpActivity.class);
                startActivity(intent);
            }
        });
        forgetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });
    }
    private boolean doubleBackToExitPressedOnce = false;
    private static final int TIME_INTERVAL = 2000;
    private static long time;
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce && System.currentTimeMillis() - time < TIME_INTERVAL) {
            this.moveTaskToBack(true);
            this.doubleBackToExitPressedOnce = false;
        } else {
            this.doubleBackToExitPressedOnce = true;
            time = System.currentTimeMillis();
            Toast.makeText(getApplicationContext(), "Click Again", Toast.LENGTH_SHORT).show();
        }
    }
}
