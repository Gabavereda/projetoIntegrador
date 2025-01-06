package com.example.projetoIntegrador.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Fornecedor")
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String nome;
    private String cnpj;
    private String endereco;
    private String cep;
    private String telefone;
    private String email;

    public Fornecedor() {
    }

}
