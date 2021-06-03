package com.example.pushups;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class Challenge extends AppCompatActivity {
    EditText btnMin, btnMax;
    Button generateBtn;
    Random random;
    int min,max,output;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);
        getSupportActionBar().setTitle("Back");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnMin = findViewById(R.id.min);
        btnMax = findViewById(R.id.max);
        generateBtn = findViewById(R.id.generate);
        random = new Random();

        generateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempMin, tempMax;
                tempMin =  btnMin.getText().toString();
                tempMax = btnMax.getText().toString();
                if (!tempMin.equals("") && !tempMax.equals("")){
                    min = Integer.parseInt(tempMin);
                    max = Integer.parseInt(tempMax);
                    if(max > min){
                        output = random.nextInt((max - min) +1) + min;
                        String strOutput = Integer.toString(output);
                        Log.d("Random broj: ", strOutput);
                        //Context context = getApplicationContext();
                        //int duration = Toast.LENGTH_SHORT;
                        //Toast toast = Toast.makeText(context, strOutput, duration);
                        //toast.show();

                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        intent.putExtra("random", output);
                        startActivity(intent);
                    }
                }
            }
        });
    }
}
