package com.example.projetoIntegrador.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "itemCarrinho")
public class ItemCarrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne //relação 
    private Produto produto;

    private int quantidade;

    @Column(nullable = false)
    private boolean compraFinalizada = false; // Indica se a compra foi finalizada ou não

    public ItemCarrinho() {
    }

}
