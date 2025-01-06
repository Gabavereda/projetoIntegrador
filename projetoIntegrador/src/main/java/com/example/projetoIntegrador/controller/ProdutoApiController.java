/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.projetoIntegrador.controller;

import com.example.projetoIntegrador.model.Produto;
import com.example.projetoIntegrador.service.ProdutoService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/produto")
@RestController
public class ProdutoApiController {

    @Autowired
    ProdutoService produtoService;

    @GetMapping("/listar")
    public ResponseEntity<List> getAllProdutos() {

        List<Produto> produtos = produtoService.listarTodosProduto();

        return new ResponseEntity<>(produtos, HttpStatus.OK);

    }

    //  pesquisar produtos por nome
    @GetMapping("/pesquisar")
    public List<Produto> pesquisarProdutos(@RequestParam(value = "nome", required = false) String nome) {
        if (nome != null && !nome.isEmpty()) {
            return produtoService.listarProdutosPorNome(nome);
        }
        return produtoService.listarTodosProduto();
    }

    @GetMapping("/pesquisar/{id}")
    public ResponseEntity<Produto> getProdutoById(@PathVariable Integer id) {

        Produto produto = produtoService.getProdutoId(id);

        return new ResponseEntity<>(produto, HttpStatus.OK);

    }

    @PostMapping("/adicionar")
    public ResponseEntity<Produto> addProduto(@RequestBody Produto produto) {

        var novoProduto = produtoService.criarProduto(produto);

        return new ResponseEntity<>(novoProduto, HttpStatus.CREATED);

    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Produto> atualizarProduto(@PathVariable Integer id, @RequestBody Produto produto) {

        var produtoAtualizado = produtoService.atualizarProduto(id, produto);

        return new ResponseEntity<>(produtoAtualizado, HttpStatus.OK);

    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity deletarProduto(@PathVariable Integer id) {

        produtoService.deletarProduto(id);

        return new ResponseEntity<>(HttpStatus.OK);

    }

}
