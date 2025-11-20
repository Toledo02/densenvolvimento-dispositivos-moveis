package com.example.inventariohardware;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DetalheActivity extends AppCompatActivity {

    private TextView txtNome, txtCategoria;
    private RatingBar ratingEstado;
    private DbHelper dbHelper;
    private long equipamentoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);

        txtNome = findViewById(R.id.txtDetalheNome);
        txtCategoria = findViewById(R.id.txtDetalheCategoria);
        ratingEstado = findViewById(R.id.ratingDetalheEstado);
        Button btnExcluir = findViewById(R.id.btnExcluir);
        dbHelper = new DbHelper(this);

        // 1. Recuperar o ID passado pela Activity anterior
        equipamentoId = getIntent().getLongExtra("ID_EQUIPAMENTO", -1);

        if (equipamentoId == -1) {
            Toast.makeText(this, "Erro ao carregar equipamento.", Toast.LENGTH_SHORT).show();
            finish(); // Fecha a tela se não houver ID
            return;
        }

        // 2. Carregar dados do banco
        carregarDados();

        // 3. Configurar botão de excluir
        btnExcluir.setOnClickListener(v -> {
            // Cria uma caixa de alerta
            new androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle("Excluir Equipamento")
                    .setMessage("Tem certeza que deseja remover este item?")
                    .setPositiveButton("Sim", (dialog, which) -> {
                        // Só apaga se clicar em SIM
                        dbHelper.deletarEquipamento(equipamentoId);
                        Toast.makeText(this, "Equipamento excluído.", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .setNegativeButton("Não", null) // Se clicar em não, não faz nada
                    .show();
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void carregarDados() {
        Equipamento e = dbHelper.buscarPorId(equipamentoId);
        if (e != null) {
            txtNome.setText(e.getNome());
            txtCategoria.setText(e.getCategoria());
            ratingEstado.setRating(e.getEstado());
        }
    }
}