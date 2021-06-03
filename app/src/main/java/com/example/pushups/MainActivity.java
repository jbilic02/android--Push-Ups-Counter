package com.example.pushups;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

public class MainActivity extends AppCompatActivity {
    private int mCounter = 0;
    Button btnClick;
    Button btnReset;
    TextView sensorStatusCounter;
    TextView descriptionText;
    SensorManager sensorManager;
    Sensor proximitySensor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorStatusCounter = findViewById(R.id.sensorCounter);
        descriptionText = findViewById(R.id.descText);
        btnClick = findViewById(R.id.btnClick);
        btnReset = findViewById(R.id.btnReset);


        // pozivanje servisa senzora
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // iz servisa senzora zovemo senzor blizine
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        // ispis u slučaju ako senzor blizine nije pronaden u uređaju
        if (proximitySensor == null) {
            Toast.makeText(this, "Sorry there is no proximity sensor found in device. :(", Toast.LENGTH_LONG).show();
            finish();
        } else {
            // registriranje senzora preko sensorManager
            sensorManager.registerListener(proximitySensorEventListener,
                    proximitySensor,
                    SensorManager.SENSOR_DELAY_FASTEST);
        }


        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(MainActivity.this, Challenge.class);
                startActivity(intent);
            }
        });


    }

    // pozivanje SensorEventListener radi promjene podataka kad senzor počne raditi
    SensorEventListener proximitySensorEventListener = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // metoda za provjeru točnosti promijenjene u senzoru
            Log.d("PUSH_UPS_APP", sensor.toString() + " - " + accuracy);
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            // provjera senzora blizine
            if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                if (event.values[0] == 0) {
                    // povecaj brojac te ispisi
                    mCounter ++;
                    Intent intent = getIntent();
                    Integer rnd = intent.getIntExtra("random",0);

                    if (rnd == 0)
                    {
                        sensorStatusCounter.setText(Integer.toString(mCounter));

                    }
                    else
                    {
                        sensorStatusCounter.setText(String.format("%d/%d",mCounter,rnd));
                        if(mCounter == rnd)
                        {
                            sensorStatusCounter.setText(String.format("%d/%d",mCounter,rnd));
                            final KonfettiView konfettiView = findViewById(R.id.viewKonfetti);
                            konfettiView.animate();
                            konfettiView.build()
                                    .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA,Color.BLUE, Color.RED)
                                    .setDirection(0.0, 359.0)
                                    .setSpeed(1f, 5f)
                                    .setFadeOutEnabled(true)
                                    .setTimeToLive(2000L)
                                    .addShapes(Shape.Square.INSTANCE, Shape.Circle.INSTANCE)
                                    .addSizes(new Size(12, 5f))
                                    .setPosition(-50f, konfettiView.getWidth() + 50f, -50f, -50f)
                                    .streamFor(300, 5000L);

                            mCounter = 0;
                            //sensorStatusCounter.setText(Integer.toString(mCounter));
                        }
                    }

                }
                else{
                    btnClick.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mCounter ++;
                            Intent intent = getIntent();
                            Integer rnd = intent.getIntExtra("random",0);
                            if(rnd == 0){
                                sensorStatusCounter.setText(Integer.toString(mCounter));
                            }
                            else {
                                sensorStatusCounter.setText(String.format("%d/%d",mCounter,rnd));
                                if(mCounter == rnd)
                                {
                                    sensorStatusCounter.setText(String.format("%d/%d",mCounter,rnd));
                                    final KonfettiView konfettiView = findViewById(R.id.viewKonfetti);
                                    konfettiView.animate();
                                    konfettiView.build()
                                            .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA,Color.BLUE, Color.RED)
                                            .setDirection(0.0, 359.0)
                                            .setSpeed(1f, 5f)
                                            .setFadeOutEnabled(true)
                                            .setTimeToLive(2000L)
                                            .addShapes(Shape.Square.INSTANCE, Shape.Circle.INSTANCE)
                                            .addSizes(new Size(12, 5f))
                                            .setPosition(-50f, konfettiView.getWidth() + 50f, -50f, -50f)
                                            .streamFor(300, 5000L);

                                    mCounter = 0;
                                    //sensorStatusCounter.setText(Integer.toString(mCounter));
                                }
                            }
                        }
                    });

                    btnReset.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mCounter = 0;
                            final KonfettiView konfettiView = findViewById(R.id.viewKonfetti);
                            konfettiView.stopGracefully();
                            getIntent().removeExtra("random");
                            sensorStatusCounter.setText(Integer.toString(mCounter));
                        }
                    });
                }

            }

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        if (proximitySensor != null) {
            sensorManager.registerListener(proximitySensorEventListener, proximitySensor,
                    SensorManager.SENSOR_DELAY_FASTEST);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (proximitySensor != null) {
            sensorManager.unregisterListener(proximitySensorEventListener);
        }
    }

}
