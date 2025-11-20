package com.example.inventariohardware;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CadastroActivity extends AppCompatActivity {

    private EditText edtNome;
    private Spinner spinnerCategoria;
    private RatingBar ratingEstado;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        // Inicializa componentes
        edtNome = findViewById(R.id.edtNome);
        spinnerCategoria = findViewById(R.id.spinnerCategoria);
        ratingEstado = findViewById(R.id.ratingEstado);
        Button btnSalvar = findViewById(R.id.btnSalvar);
        dbHelper = new DbHelper(this);

        // Configura o Spinner com algumas categorias padrão
        String[] categorias = {"Notebook", "Periférico", "Cabo/Adaptador", "Monitor", "Outro"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categorias);
        spinnerCategoria.setAdapter(adapter);

        // Ação do botão Salvar
        btnSalvar.setOnClickListener(v -> salvar());

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // Fecha a tela atual e volta para a anterior
        return true;
    }

    private void salvar() {
        String nome = edtNome.getText().toString();
        String categoria = spinnerCategoria.getSelectedItem().toString();
        int estado = (int) ratingEstado.getRating(); // Pega valor de 1 a 5

        if (nome.isEmpty()) {
            Toast.makeText(this, "Por favor, digite o nome.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Cria o objeto e salva no banco
        Equipamento e = new Equipamento(nome, categoria, estado);
        long id = dbHelper.salvarEquipamento(e);

        if (id != -1) {
            Toast.makeText(this, "Salvo com sucesso!", Toast.LENGTH_SHORT).show();
            finish(); // Fecha a tela e volta para a anterior (MainActivity)
        } else {
            Toast.makeText(this, "Erro ao salvar.", Toast.LENGTH_SHORT).show();
        }
    }
}