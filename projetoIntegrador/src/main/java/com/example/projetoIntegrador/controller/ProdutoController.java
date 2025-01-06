package com.example.projetoIntegrador.controller;

import com.example.projetoIntegrador.model.Produto;
import com.example.projetoIntegrador.service.ProdutoService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    ProdutoService produtoService;

    private List<Produto> produtos = new ArrayList<>();

    // Bloco estático com exemplos
//    {
//        produtos.add(new Produto(contadorId++, "carne", 30.90, 20, "Carne bovina de primeira"));
//        produtos.add(new Produto(contadorId++, "leite", 5.50, 50, "Leite integral 1L"));
//        produtos.add(new Produto(contadorId++, "acucar", 11.90, 50, "Açucar padrão"));
//        produtos.add(new Produto(contadorId++, "arroz", 4.90, 30, "Arroz tipo 1, pacote de 1kg"));
//        produtos.add(new Produto(contadorId++, "paes", 9.90, 30, "Pão francês"));
//        produtos.add(new Produto(contadorId++, "carne", 30.90, 20, "Carne bovina de primeira"));
//        produtos.add(new Produto(contadorId++, "leite", 5.50, 50, "Leite integral 1L"));
//        produtos.add(new Produto(contadorId++, "acucar", 11.90, 50, "Açucar padrão"));
//        produtos.add(new Produto(contadorId++, "arroz", 4.90, 30, "Arroz tipo 1, pacote de 1kg"));
//        produtos.add(new Produto(contadorId++, "paes", 9.90, 30, "Pão embalagem"));
//    }
    public List<Produto> getProdutos() {
        return produtos;
    }

    //  cadastro com a lista de produtos
    @GetMapping("/cadastro")
    public String exibirPaginaCadastro(Model model) {
        Produto produto = new Produto();

        // para ja iniciar com a lista de fornecedores na tela 
        model.addAttribute("listarTodosProduto", produtoService.listarTodosProduto());
        model.addAttribute("produto", produto);
        return "cadastro";
    }

    // Processar o formulário de cadastro
    @PostMapping("/cadastro")
    public String processarCadastro(@ModelAttribute("produto") Produto produto) {

        if (produto.getId() == null) {
            produtoService.criarProduto(produto);

        } else {
            produtoService.atualizarProduto(produto.getId(), produto);
        }

        return "redirect:/produtos/cadastro";
    }

    // edit
    @GetMapping("/atualizarProdutoForm/{id}")
    public String atualizarProdutoForm(@PathVariable(value = "id") Integer id, Model model) {
        Produto produto = produtoService.getProdutoId(id);

        model.addAttribute("produto", produto);

        return "redirect:/produtos/cadastro";
    }

    // delete
    @PostMapping("/deletarProduto/{id}")
    public String deletarProduto(@PathVariable(value = "id") Integer id) {
        produtoService.deletarProduto(id);
        return "redirect:/produtos/cadastro";
    }

}
