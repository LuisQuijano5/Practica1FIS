package com.aza.vacationpreference;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.List;

public class Results extends AppCompatActivity {
    private List<List<Integer>> matrix;

    BarChart mChart;
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
        showMatrix();
        GroupBarChart();
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

    public void GroupBarChart() {
        mChart = (BarChart) findViewById(R.id.bar_chart);
        mChart.setDrawBarShadow(false);
        mChart.getDescription().setEnabled(false);
        mChart.setPinchZoom(false);
        mChart.setDrawGridBackground(true);

        // Etiquetas del eje X
        String[] labels = {"", "18-19", "20-25", "26-30", ""};
        XAxis xAxis = mChart.getXAxis();
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setGranularity(1f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(12);
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setAxisMinimum(1f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTextColor(Color.BLACK);
        leftAxis.setTextSize(12);
        leftAxis.setAxisLineColor(Color.WHITE);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularity(2);
        leftAxis.setLabelCount(8, true);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);

        mChart.getAxisRight().setEnabled(false);
        mChart.getLegend().setEnabled(false);

        // Nuevos valores para 5 barras por grupo
        /*float[] valOne = {10, 20, 30}; // Grupo 1
        float[] valTwo = {15, 25, 35}; // Grupo 2
        float[] valThree = {5, 10, 15}; // Grupo 3
        float[] valFour = {12, 22, 32}; // Grupo 4
        float[] valFive = {18, 28, 38}; */// Grupo 5

        // Crear las entradas para cada conjunto de barras
        ArrayList<BarEntry> barOne = new ArrayList<>();
        ArrayList<BarEntry> barTwo = new ArrayList<>();
        ArrayList<BarEntry> barThree = new ArrayList<>();
        ArrayList<BarEntry> barFour = new ArrayList<>();
        ArrayList<BarEntry> barFive = new ArrayList<>();

        // Recorremos las filas de la matriz y asignamos valores a cada barra
        for (int i = 0; i < matrix.size(); i++) {
            List<Integer> row = matrix.get(i);
            // Asignamos cada valor de la fila a las barras correspondientes
            barOne.add(new BarEntry(i + 1, row.get(0)));
            barTwo.add(new BarEntry(i + 1, row.get(1)));
            barThree.add(new BarEntry(i + 1, row.get(2)));
            barFour.add(new BarEntry(i + 1, row.get(3)));
            barFive.add(new BarEntry(i + 1, row.get(4)));
        }

        BarDataSet set1 = new BarDataSet(barOne, "Dormir");
        set1.setColor(Color.BLUE);
        BarDataSet set2 = new BarDataSet(barTwo, "Hacer deporte");
        set2.setColor(Color.MAGENTA);
        BarDataSet set3 = new BarDataSet(barThree, "Videojuegos");
        set3.setColor(Color.GREEN);
        BarDataSet set4 = new BarDataSet(barFour, "Salir amigos");
        set4.setColor(Color.RED);
        BarDataSet set5 = new BarDataSet(barFive, "Viajar");
        set5.setColor(Color.CYAN);

        set1.setHighlightEnabled(false);
        set2.setHighlightEnabled(false);
        set3.setHighlightEnabled(false);
        set4.setHighlightEnabled(false);
        set5.setHighlightEnabled(false);
        set1.setDrawValues(true);
        set2.setDrawValues(true);
        set3.setDrawValues(true);
        set4.setDrawValues(true);
        set5.setDrawValues(true);

        // Agregar todos los datasets
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        dataSets.add(set2);
        dataSets.add(set3);
        dataSets.add(set4);
        dataSets.add(set5);

        BarData data = new BarData(dataSets);

        // Espacios entre grupos y barras
        float groupSpace = 0.2f;
        float barSpace = 0.02f;
        float barWidth = 0.15f;
        data.setBarWidth(barWidth);

        // Ajustar el rango del eje X
        xAxis.setAxisMaximum(labels.length - 0.9f);
        mChart.setData(data);

        // Configurar leyenda (debe ser después de setData)
        Legend legend = mChart.getLegend();
        legend.setForm(Legend.LegendForm.SQUARE);
        legend.setTextSize(10f);
        legend.setTextColor(Color.BLACK);
        legend.setXEntrySpace(10f);  // Espacio horizontal entre los elementos de la leyenda
        legend.setFormToTextSpace(5f); // Espacio entre el ícono de la leyenda y el texto

        // Habilitar la leyenda para los grupos
        legend.setEnabled(true);

        mChart.setScaleEnabled(false);
        mChart.setVisibleXRangeMaximum(3f); // Mostrar solo las 3 etiquetas
        mChart.groupBars(1f, groupSpace, barSpace);
        mChart.invalidate();
    }

}