package com.example.projetoIntegrador.controller;

import com.example.projetoIntegrador.model.Fornecedor;
import com.example.projetoIntegrador.service.FornecedorService;
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
@RequestMapping("/fornecedor")
public class FornecedorApiController {

    @Autowired
    FornecedorService fornecedorService;

    @GetMapping("/listar")
    public ResponseEntity<List> getAllFornecedores() {

        List<Fornecedor> fornecedores = fornecedorService.listarTodosFornecedores();

        return new ResponseEntity<>(fornecedores, HttpStatus.OK);

    }

    @GetMapping("/pesquisar/{id}")
    public ResponseEntity<Fornecedor> getFornecedorById(@PathVariable Integer id) {

        Fornecedor fornecedor = fornecedorService.getFornecedorId(id);

        return new ResponseEntity<>(fornecedor, HttpStatus.OK);

    }

    @PostMapping("/adicionar")
    public ResponseEntity<Fornecedor> addFornecedor(@RequestBody Fornecedor fornecedor) {

        var novoFornecedor = fornecedorService.criarFornecedor(fornecedor);

        return new ResponseEntity<>(novoFornecedor, HttpStatus.CREATED);

    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Fornecedor> atualizarFornecedor(@PathVariable Integer id, @RequestBody Fornecedor fornecedor) {

        var fornecedorAtualizado = fornecedorService.atualizarFornecedor(id, fornecedor);

        return new ResponseEntity<>(fornecedorAtualizado, HttpStatus.OK);

    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity deletarFornecedor(@PathVariable Integer id) {

        fornecedorService.deletarFornecedor(id);

        return new ResponseEntity<>(HttpStatus.OK);

    }

}
