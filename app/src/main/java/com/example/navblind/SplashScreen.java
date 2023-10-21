package com.example.navblind;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.os.Handler;


public class SplashScreen extends AppCompatActivity {

    // La durée de l'écran de démarrage en millisecondes
    private static final long SPLASH_DELAY = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Crée un gestionnaire pour retarder le passage à l'écran principal
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Démarre l'activité principale
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                finish(); // Ferme l'écran de démarrage pour qu'il ne puisse pas être revenir en arrière
            }
        }, SPLASH_DELAY);
    }
}
