package com.example.inventariohardware;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView txtListaVazia; // Variável para o texto
    private DbHelper dbHelper;
    private EquipamentoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewEquipamentos);
        txtListaVazia = findViewById(R.id.txtListaVazia); // Linkamos com o XML
        FloatingActionButton fab = findViewById(R.id.fabAdicionar);
        dbHelper = new DbHelper(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CadastroActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        atualizarLista();
    }

    private void atualizarLista() {
        List<Equipamento> lista = dbHelper.listarTodos();

        // --- Verifica se a lista está vazia ---
        if (lista.isEmpty()) {
            // Se vazia: Esconde a lista, Mostra o texto
            recyclerView.setVisibility(View.GONE);
            txtListaVazia.setVisibility(View.VISIBLE);
        } else {
            // Se tem itens: Mostra a lista, Esconde o texto
            recyclerView.setVisibility(View.VISIBLE);
            txtListaVazia.setVisibility(View.GONE);
        }

        adapter = new EquipamentoAdapter(lista, itemClicado -> {
            Intent intent = new Intent(MainActivity.this, DetalheActivity.class);
            intent.putExtra("ID_EQUIPAMENTO", itemClicado.getId());
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);
    }
}