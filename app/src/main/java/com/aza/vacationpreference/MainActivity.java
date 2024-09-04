package com.aza.vacationpreference;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private RadioGroup radioGroupEdad, radioGroupActividad;
    private Button buttonSubmit;
    private boolean isClearing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroupEdad = findViewById(R.id.radioGroupEdad);
        radioGroupActividad = findViewById(R.id.radioGroupActividad);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        radioGroupEdad.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (!isClearing) {
                    int edadValue = getSelectedEdadValue();
                    System.out.println("Edad seleccionada: " + edadValue);
                }
            }
        });

        radioGroupActividad.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (!isClearing) {
                    int actividadValue = getSelectedActividadValue();
                    System.out.println("Actividad seleccionada: " + actividadValue);
                }
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioGroupEdad.getCheckedRadioButtonId() == -1 || radioGroupActividad.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(MainActivity.this, "Por favor, selecciona una opción para cada pregunta", Toast.LENGTH_SHORT).show();
                } else {
                    int edadValue = getSelectedEdadValue();
                    int actividadValue = getSelectedActividadValue();

                    System.out.println("Valores enviados - Edad: " + edadValue + ", Actividad: " + actividadValue);

                    Toast.makeText(MainActivity.this, "Tus respuestas fueron enviadas", Toast.LENGTH_SHORT).show();

                    // Limpiar las selecciones
                    isClearing = true;
                    radioGroupEdad.clearCheck();
                    radioGroupActividad.clearCheck();
                    isClearing = false;
                }
            }
        });

    }

    private int getSelectedEdadValue() {
        int selectedId = radioGroupEdad.getCheckedRadioButtonId();
        if (selectedId == R.id.radio_18_19) return 0;
        if (selectedId == R.id.radio_20_25) return 1;
        if (selectedId == R.id.radio_26_30) return 2;
        return -1;
    }

    private int getSelectedActividadValue() {
        int selectedId = radioGroupActividad.getCheckedRadioButtonId();
        if (selectedId == R.id.radio_dormir) return 0;
        if (selectedId == R.id.radio_deporte) return 1;
        if (selectedId == R.id.radio_videojuegos) return 2;
        if (selectedId == R.id.radio_amigos) return 3;
        if (selectedId == R.id.radio_viajar) return 4;
        return -1;
    }

}