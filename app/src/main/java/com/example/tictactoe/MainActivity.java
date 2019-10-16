package com.example.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnInstruction;
    private Button btnStartPlay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnInstruction = findViewById(R.id.btnInstID);
        btnStartPlay = findViewById(R.id.btnPlayID);
        btnInstruction.setOnClickListener(this);
        btnStartPlay.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Log.d("debug" , "button clicked");
        switch (v.getId())
        {
            case R.id.btnInstID:{
                //open InstructionsActivity
                Log.d("debug" , "btnInstID clicked");
                Intent intent = new Intent(this, InstructionsActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btnPlayID:{
                //open GameActivity
                Log.d("debug" , "btnPlayID clicked");
                Intent intent = new Intent(this, GameActivity.class);
                startActivity(intent);
                break;
            }

        }
    }
}
