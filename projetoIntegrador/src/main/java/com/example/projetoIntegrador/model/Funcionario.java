package com.example.projetoIntegrador.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

@Data
@Entity
@Table(name = "Funcionario")
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String nome;
    private String cargo;
    private String cpf;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataEntrada;
    @NumberFormat(pattern = "#,##0.00")
    private double salario;

    public Funcionario() {
    }

}
