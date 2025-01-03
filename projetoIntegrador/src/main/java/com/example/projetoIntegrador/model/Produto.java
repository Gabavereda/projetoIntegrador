package com.example.projetoIntegrador.model;

import org.springframework.format.annotation.NumberFormat;

public class Produto {

    private int id;
    private String nome;
    @NumberFormat(pattern = "#,##0.00")
    private double preco;
    private int quantidade;
    private String descricao;

    public Produto() {
    }

    public Produto(int id, String nome, double preco, Integer quantidade, String descricao) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    // LOG
//    // foi necessário para encontrar conflitos de id's de filmes e analise
//    System.out.println ("produto adicionada: " + produto);// Verifique a análise adicionada
//    System.out.println ("Análises no filme após adição: " + produtos);  // Verifique as análises no filme
}
