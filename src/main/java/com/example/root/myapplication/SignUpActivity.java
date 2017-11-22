package com.example.root.myapplication;

import android.content.Context;
import android.content.Intent;
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

public class SignUpActivity extends AppCompatActivity {

    Context context;
    EditText usernameEditText, passwordEditText, confirmPasswordEditText, emailEditText;;
    Button submitButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        context = SignUpActivity.this;

        usernameEditText = findViewById(R.id.signUpUsernameEditText);
        passwordEditText = findViewById(R.id.signUpPasswordEditText);
        confirmPasswordEditText = findViewById(R.id.signUpConfirmPasswordEditText);
        emailEditText = findViewById(R.id.signUpEmailEditText);
        submitButton = findViewById(R.id.signUpSubmitButton);

        final String url = "http://mehrdadseyfi.ir/pay/singup.php";
        final RequestParams params = new RequestParams();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (usernameEditText.getText().toString().equals("") || passwordEditText.getText().toString().equals("") || confirmPasswordEditText.getText().toString().equals("") || emailEditText.getText().toString().equals("")) {
                    Toast.makeText(context, "Please enter your info", Toast.LENGTH_SHORT).show();
//                } else if (!(passwordEditText.getText().toString().equals(confirmPasswordEditText.getText().toString()))) {
//                    Toast.makeText(context, "Password wrong", Toast.LENGTH_SHORT).show();
//                    passwordEditText.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
                } else {
                    params.put("username", usernameEditText.getText().toString());
                    params.put("password", passwordEditText.getText().toString());
                    params.put("email", emailEditText.getText().toString());
                    AsyncHttpClient client = new AsyncHttpClient();
                    client.post(url, params, new TextHttpResponseHandler() {
                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            Toast.makeText(context, throwable.toString(), Toast.LENGTH_LONG).show();
                        }
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String responseString) {
                            if (responseString.equals("same email")) {
                                Toast.makeText(context, "This Email Used", Toast.LENGTH_SHORT).show();
                            } else if (responseString.equals("same username")) {
                                Toast.makeText(context, "This USERNAME Used", Toast.LENGTH_SHORT).show();
                            } else  {
                                Intent intent = new Intent(context, DialogConfirmCodeActivity.class);
                                intent.putExtra("username", usernameEditText.getText().toString());
                                intent.putExtra("confirmCode", responseString);
                                startActivity(intent);

                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(context, LogInActivity.class);
        startActivity(intent);
    }
}
