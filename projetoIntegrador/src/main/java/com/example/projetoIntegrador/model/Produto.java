package com.example.projetoIntegrador.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

@Data
@Entity
@Table(name = "Produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String nome;
    @NumberFormat(pattern = "#,##0.00")
    private double preco;
    private int quantidade;
    private String descricao;

    public Produto() {
    }

    // LOG
//    // foi necessário para encontrar conflitos de id's de filmes e analise
//    System.out.println ("produto adicionada: " + produto);// Verifique a análise adicionada
//    System.out.println ("Análises no filme após adição: " + produtos);  // Verifique as análises no filme
}
