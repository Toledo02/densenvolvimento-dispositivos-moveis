package com.example.inventariohardware;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "inventario.db";
    private static final int VERSAO_BANCO = 1;
    private static final String TABELA_EQUIPAMENTOS = "equipamentos";

    // Nomes das colunas
    private static final String COL_ID = "_id";
    private static final String COL_NOME = "nome";
    private static final String COL_CATEGORIA = "categoria";
    private static final String COL_ESTADO = "estado";

    public DbHelper(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL para criar a tabela
        String criarTabela = "CREATE TABLE " + TABELA_EQUIPAMENTOS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NOME + " TEXT, " +
                COL_CATEGORIA + " TEXT, " +
                COL_ESTADO + " INTEGER)";
        db.execSQL(criarTabela);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Se atualizar o app, deleta a tabela antiga e cria nova (simples)
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_EQUIPAMENTOS);
        onCreate(db);
    }

    // --- MÉTODOS CRUD ---

    // 1. Salvar novo item
    public long salvarEquipamento(Equipamento e) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NOME, e.getNome());
        values.put(COL_CATEGORIA, e.getCategoria());
        values.put(COL_ESTADO, e.getEstado());

        long id = db.insert(TABELA_EQUIPAMENTOS, null, values);
        db.close();
        return id;
    }

    // 2. Listar todos (Para a Activity 1 - RecyclerView)
    public List<Equipamento> listarTodos() {
        List<Equipamento> lista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Select * From equipamentos
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELA_EQUIPAMENTOS, null);

        if (cursor.moveToFirst()) {
            do {
                Equipamento e = new Equipamento();
                e.setId(cursor.getLong(cursor.getColumnIndexOrThrow(COL_ID)));
                e.setNome(cursor.getString(cursor.getColumnIndexOrThrow(COL_NOME)));
                e.setCategoria(cursor.getString(cursor.getColumnIndexOrThrow(COL_CATEGORIA)));
                e.setEstado(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ESTADO)));
                lista.add(e);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return lista;
    }

    // 3. Buscar um único item por ID (Para a Activity 3 - Detalhes)
    public Equipamento buscarPorId(long idBuscado) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELA_EQUIPAMENTOS + " WHERE " + COL_ID + " = ?",
                new String[]{String.valueOf(idBuscado)});

        Equipamento e = null;
        if (cursor.moveToFirst()) {
            e = new Equipamento();
            e.setId(cursor.getLong(cursor.getColumnIndexOrThrow(COL_ID)));
            e.setNome(cursor.getString(cursor.getColumnIndexOrThrow(COL_NOME)));
            e.setCategoria(cursor.getString(cursor.getColumnIndexOrThrow(COL_CATEGORIA)));
            e.setEstado(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ESTADO)));
        }
        cursor.close();
        db.close();
        return e;
    }

    // 4. Deletar item (Para o botão de Excluir na Activity 3)
    public void deletarEquipamento(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABELA_EQUIPAMENTOS, COL_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}