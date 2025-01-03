package com.example.projetoIntegrador.model;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

public class Funcionario {

    private int id;
    private String nome;
    private String cargo;
    private String cpf;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataEntrada;
    @NumberFormat(pattern = "#,##0.00")
    private double salario;

    public Funcionario() {
    }

    public Funcionario(int id, String nome, String cargo, String cpf, Date dataEntrada, double salario) {
        this.id = id;
        this.nome = nome;
        this.cargo = cargo;
        this.cpf = cpf;
        this.dataEntrada = dataEntrada;
        this.salario = salario;
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

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(Date dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

}
