package com.example.navblind;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import java.util.concurrent.Executor;

public class FingerPrintActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingerprint);

        // Initialize msgtext
        TextView msgtex = findViewById(R.id.txtmsg);

        // Create a variable for our BiometricManager and check if the user can use biometric sensor
        BiometricManager biometricManager = androidx.biometric.BiometricManager.from(this);
        switch (biometricManager.canAuthenticate()) {

            // Signification : Cela signifie que le capteur biométrique a réussi.
            //Explication : Vous pouvez utiliser le capteur d'empreintes digitales pour vous connecter.
            //Action suggérée : Aucune action particulière n'est requise, car le capteur biométrique fonctionne correctement.
            case BiometricManager.BIOMETRIC_SUCCESS:
                msgtex.setText("You can use the fingerprint sensor to login");
                msgtex.setTextColor(Color.parseColor("#fafafa"));
                break;

            // Signification : Cela signifie que le dispositif ne possède pas de capteur d'empreintes digitales.
            //Explication : Ce dispositif ne dispose pas d'un capteur d'empreintes digitales.
            //Action suggérée : Vous ne pourrez pas utiliser la biométrie pour vous connecter sur ce dispositif, car il ne dispose pas d'un capteur d'empreintes digitales.
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                msgtex.setText("This device does not have a fingerprint sensor");
                break;

            // Signification : Cela signifie que le capteur biométrique n'est pas disponible actuellement.
            //Explication : Le capteur biométrique n'est pas accessible pour le moment.
            //Action suggérée : Attendez un moment et essayez à nouveau, car le capteur biométrique peut devenir disponible ultérieurement.
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                msgtex.setText("The biometric sensor is currently unavailable");
                break;

            // TSignification : Cela signifie que le dispositif n'a pas enregistré votre empreinte digitale.
            //Explication : Votre dispositif n'a pas enregistré votre empreinte digitale. Vous devez vérifier vos paramètres de sécurité et enregistrer une empreinte digitale.
            //Action suggérée : Accédez aux paramètres de sécurité de votre dispositif et ajoutez une empreinte digitale pour pouvoir utiliser la biométrie pour vous connecter.
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                msgtex.setText("Your device doesn't have a fingerprint saved, please check your security settings");
                break;
        }

        //Créer une variable pour notre exécuteur
        Executor executor = ContextCompat.getMainExecutor(this);

        // Cela nous donnera le résultat de l'AUTHENTIFICATION
        final BiometricPrompt biometricPrompt = new BiometricPrompt(FingerPrintActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            // Cette méthode est appelée lorsque l'authentification réussit
            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(), "Login with Empreint is Success", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(FingerPrintActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });

        // Créez une variable pour notre promptInfo DIALOGUE BIOMÉTRIQUE
        final BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("GFG")
                .setDescription("Use your fingerprint to login")
                .setNegativeButtonText("Cancel")
                .build();

        //Déclenchez l'authentification dès le démarrage de l'activité
        biometricPrompt.authenticate(promptInfo);
    }
}
