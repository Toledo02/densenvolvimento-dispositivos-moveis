package com.example.inventariohardware;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EquipamentoAdapter extends RecyclerView.Adapter<EquipamentoAdapter.EquipamentoViewHolder> {

    private List<Equipamento> listaEquipamentos;
    private OnItemClickListener listener;

    // Interface para lidar com o clique no item (A Activity vai implementar isso)
    public interface OnItemClickListener {
        void onItemClick(Equipamento item);
    }

    // Construtor: recebe a lista e quem vai tratar o clique
    public EquipamentoAdapter(List<Equipamento> lista, OnItemClickListener listener) {
        this.listaEquipamentos = lista;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EquipamentoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Aqui nós "inflamos" o XML item_equipamento
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_equipamento, parent, false);
        return new EquipamentoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EquipamentoViewHolder holder, int position) {
        // Aqui pegamos os dados e colocamos na tela
        Equipamento itemAtual = listaEquipamentos.get(position);

        holder.txtNome.setText(itemAtual.getNome());
        holder.txtCategoria.setText(itemAtual.getCategoria());
        holder.txtEstado.setText(itemAtual.getEstado() + "/5 estrelas");

        // Configura o clique no item inteiro
        holder.itemView.setOnClickListener(v -> listener.onItemClick(itemAtual));
    }

    @Override
    public int getItemCount() {
        return listaEquipamentos.size();
    }

    // ViewHolder: Guarda as referências dos componentes do XML para não buscar toda hora
    static class EquipamentoViewHolder extends RecyclerView.ViewHolder {
        TextView txtNome, txtCategoria, txtEstado;

        public EquipamentoViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNome = itemView.findViewById(R.id.txtNomeItem);
            txtCategoria = itemView.findViewById(R.id.txtCategoriaItem);
            txtEstado = itemView.findViewById(R.id.txtEstadoItem);
        }
    }
}