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

public class ForgetPasswordActivity extends AppCompatActivity {

    Context context;
    EditText emailEditText;
    Button submitButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        context = ForgetPasswordActivity.this;

        emailEditText = findViewById(R.id.forgetPasswordEmailEditText);
        submitButton = findViewById(R.id.forgetPasswordSubmitButton);

        final String url = "http://mehrdadseyfi.ir/pay/newpassword.php";
        final RequestParams params = new RequestParams();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                params.put("email", emailEditText.getText().toString());
                AsyncHttpClient client = new AsyncHttpClient();
                client.post(url, params, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Toast.makeText(context, throwable.toString(), Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        if (responseString.equals("password changed\r\n")) {
                            Toast.makeText(context, "New Password sent to  " + emailEditText.getText().toString() , Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, LogInActivity.class);
                            startActivity(intent);
                        } else if (responseString.equals("no user\r\n")) {
                            Toast.makeText(context, "No Account With This Email" , Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(context, LogInActivity.class);
        startActivity(intent);
    }
}
