package com.aza.vacationpreference;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class Results extends AppCompatActivity {
    private List<List<Integer>> matrix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_results);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //DO NOT ERASE, USED TO RECEIVE SERIALIZED MATRIX
        Intent intent = getIntent();
        matrix = (List<List<Integer>>) intent.getSerializableExtra("matrix_key");
        //showMatrix();
    }

    // Do nothing to avoid user adding more records than the 15
    @Override
    public void onBackPressed() {

        if(false){
            super.onBackPressed();
        }
    }

    //only for debugging purposes check logcat
    private void showMatrix(){
        for (List<Integer> row : matrix) {
            for (int element : row) {
                System.out.print(element + " ");
            }
            System.out.println();
        }
    }
}