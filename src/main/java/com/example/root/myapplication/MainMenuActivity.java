package com.example.root.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainMenuActivity extends AppCompatActivity {

    Context context;
    Button logOutButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        context = MainMenuActivity.this;

        logOutButton = findViewById(R.id.mainMenuLogOutButton);

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, LogInActivity.class);
                startActivity(intent);
            }
        });
        //code
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