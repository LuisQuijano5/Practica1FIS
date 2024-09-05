package com.aza.vacationpreference;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RadioGroup radioGroupEdad, radioGroupActividad;
    private Button buttonSubmit;
    private boolean isClearing = false;
    private List<List<Integer>> matrix = new ArrayList<>();
    private int counter;
    private int counterLimit = 5; //lower it for debugging and testing

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroupEdad = findViewById(R.id.radioGroupEdad);
        radioGroupActividad = findViewById(R.id.radioGroupActividad);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        //Setting counter stuff
        createMatrix();
        counter = 0;

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

                    //Counter stuff
                    increment(edadValue, actividadValue);
                    if(counter >= counterLimit){
                        Intent intent = new Intent(MainActivity.this, Results.class);
                        intent.putExtra("matrix_key", (Serializable) matrix);
                        startActivity(intent);
                    }

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

    private void createMatrix(){
        for (int i = 0; i < 3; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                row.add(0);
            }
            matrix.add(row);
        }
    }

    private void increment(int r, int c) {
        if(counter >= 15){
            Toast.makeText(MainActivity.this, "Has superado el limite de respuestas", Toast.LENGTH_SHORT).show();
            return;
        }
        if (r >= 0 && r < matrix.size() && c >= 0 && c < matrix.get(0).size()) {
            int currentValue = matrix.get(r).get(c);
            matrix.get(r).set(c, currentValue + 1);
            counter++;
            Toast.makeText(MainActivity.this, "Tus respuestas fueron enviadas", Toast.LENGTH_SHORT).show();
        } else {
            System.out.println("Invalid row or column"); //in case something happens through development
        }
    }

}