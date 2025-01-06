package com.example.projetoIntegrador.controller;

import com.example.projetoIntegrador.model.ItemCarrinho;
import com.example.projetoIntegrador.service.ItemCarrinhoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/carrinho")
@RestController
public class LojaApiController {

    @Autowired
    ItemCarrinhoService itemCarrinhoService;

    @GetMapping("/listar")
    public ResponseEntity<List> getAllLojaCarrinho() {

        List<ItemCarrinho> itemCarrinhos = itemCarrinhoService.listarTodosItemCarrinho();

        return new ResponseEntity<>(itemCarrinhos, HttpStatus.OK);

    }

    @PostMapping("/adicionar")
    public ResponseEntity<ItemCarrinho> addItemCarrinho(@RequestBody ItemCarrinho itemCarrinho) {

        var novoItemCarrinho = itemCarrinhoService.criarItemCarrinho(itemCarrinho);

        return new ResponseEntity<>(novoItemCarrinho, HttpStatus.CREATED);

    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity deletarItemCarrinho(@PathVariable Integer id) {

        itemCarrinhoService.deletarItemCarrinho(id);

        return new ResponseEntity<>(HttpStatus.OK);

    }

}
