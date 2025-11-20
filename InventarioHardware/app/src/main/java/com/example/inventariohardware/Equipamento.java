package com.example.inventariohardware;

public class Equipamento {
    private long id;
    private String nome;
    private String categoria; // Ex: PC, Monitor, Cabo
    private int estado; // 1 a 5 estrelas

    // Construtor vazio (útil para algumas operações)
    public Equipamento() { }

    // Construtor para criar novos itens (sem ID, pois o banco gera)
    public Equipamento(String nome, String categoria, int estado) {
        this.nome = nome;
        this.categoria = categoria;
        this.estado = estado;
    }

    // Construtor completo (para ler do banco)
    public Equipamento(long id, String nome, String categoria, int estado) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.estado = estado;
    }

    // Getters e Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public int getEstado() { return estado; }
    public void setEstado(int estado) { this.estado = estado; }
}