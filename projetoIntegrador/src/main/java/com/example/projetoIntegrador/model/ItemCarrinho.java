package com.example.projetoIntegrador.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "itemCarrinho")
public class ItemCarrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Produto produto;
    private int quantidade;
    //PARA RELATORIOS
    @Column(nullable = false)
    private boolean compraFinalizada = false;

    public ItemCarrinho() {
    }

}
