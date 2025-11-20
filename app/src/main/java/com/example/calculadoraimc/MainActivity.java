package com.example.calculadoraimc;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText etPeso, etAltura;
    TextView tvResultado;
    Button btnCalcular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etPeso = findViewById(R.id.etPeso);
        etAltura = findViewById(R.id.etAltura);
        tvResultado = findViewById(R.id.tvResultado);
        btnCalcular = findViewById(R.id.btnCalcular);

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcularIMC();
            }
        });
    }

    private void calcularIMC() {
        String pesoStr = etPeso.getText().toString();
        String alturaStr = etAltura.getText().toString();

        if (pesoStr.isEmpty() || alturaStr.isEmpty()) {
            Toast.makeText(this, "Preencha peso e altura", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double peso = Double.parseDouble(pesoStr);
            double altura = Double.parseDouble(alturaStr);
            double imc = peso / (altura * altura);

            String classificacao;
            if (imc < 18.5) classificacao = "Abaixo do peso";
            else if (imc < 24.9) classificacao = "Peso normal";
            else if (imc < 29.9) classificacao = "Sobrepeso";
            else if (imc < 34.9) classificacao = "Obesidade grau 1";
            else if (imc < 39.9) classificacao = "Obesidade grau 2";
            else classificacao = "Obesidade grau 3";

            String resultado = String.format("IMC: %.2f\n%s", imc, classificacao);
            tvResultado.setText(resultado);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Digite valores válidos", Toast.LENGTH_SHORT).show();
        }
    }
}
