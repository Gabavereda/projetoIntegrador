package com.example.projetoIntegrador.controller;

import com.example.projetoIntegrador.model.Funcionario;
import com.example.projetoIntegrador.service.FuncionarioService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/funcionario")
public class FuncionarioApiController {

    @Autowired
    FuncionarioService funcionarioService;

    @GetMapping("/listar")
    public ResponseEntity<List> getAllFuncionarios() {

        List<Funcionario> funcionarios = funcionarioService.listarTodosFuncionarios();

        return new ResponseEntity<>(funcionarios, HttpStatus.OK);

    }

    @GetMapping("/pesquisar/{id}")
    public ResponseEntity<Funcionario> getFuncionarioById(@PathVariable Integer id) {

        Funcionario funcionario = funcionarioService.getFuncionarioId(id);

        return new ResponseEntity<>(funcionario, HttpStatus.OK);

    }

    @PostMapping("/adicionar")
    public ResponseEntity<Funcionario> addFuncionario(@RequestBody Funcionario funcionario) {

        var novoFuncionario = funcionarioService.criarFuncionario(funcionario);

        return new ResponseEntity<>(novoFuncionario, HttpStatus.CREATED);

    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Funcionario> atualizarFuncionario(@PathVariable Integer id, @RequestBody Funcionario funcionario) {

        var funcionarioAtualizado = funcionarioService.atualizarFuncionario(id, funcionario);

        return new ResponseEntity<>(funcionarioAtualizado, HttpStatus.OK);

    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity deletarFuncionario(@PathVariable Integer id) {

        funcionarioService.deletarFuncionario(id);

        return new ResponseEntity<>(HttpStatus.OK);

    }

}
