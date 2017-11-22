package com.example.root.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class DialogConfirmCodeActivity extends Activity {

    Context context;
    EditText codeEditText;
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_confirm_code);
        context = DialogConfirmCodeActivity.this;
        this.setFinishOnTouchOutside(false);
        codeEditText = findViewById(R.id.dialogConfirmCodeEditText);
        submitButton = findViewById(R.id.dialogConfirmCodeSubmitButton);

        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");
        final String confirmCode = intent.getStringExtra("confirmCode");


        final String url = "http://mehrdadseyfi.ir/pay/singupok.php";
        final RequestParams params = new RequestParams();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (codeEditText.getText().toString().equals("")){
                    Toast.makeText(context, "Please enter your info", Toast.LENGTH_SHORT).show();
                } else if (confirmCode.equals(codeEditText.getText().toString())){
                    params.put("username", username);
                    AsyncHttpClient client = new AsyncHttpClient();
                    client.post(url, params, new TextHttpResponseHandler() {
                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String responseString) {
                            if (responseString.equals("ok")){
                                Toast.makeText(context, "Registered", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(context, LogInActivity.class);
                                startActivity(intent);
                            }
                        }
                    });
                } else {
                    Toast.makeText(context, "WRONG CODE", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}
