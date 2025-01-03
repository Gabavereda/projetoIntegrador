package com.example.projetoIntegrador.controller;

import com.example.projetoIntegrador.model.Produto;
import java.util.ArrayList;
import java.util.List;
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

    private List<Produto> produtos = new ArrayList<>();
    private int contadorId = 1;

    // Bloco estático com exemplos
    {
        produtos.add(new Produto(contadorId++, "carne", 30.90, 20, "Carne bovina de primeira"));
        produtos.add(new Produto(contadorId++, "leite", 5.50, 50, "Leite integral 1L"));
        produtos.add(new Produto(contadorId++, "acucar", 11.90, 50, "Açucar padrão"));
        produtos.add(new Produto(contadorId++, "arroz", 4.90, 30, "Arroz tipo 1, pacote de 1kg"));
        produtos.add(new Produto(contadorId++, "paes", 9.90, 30, "Pão francês"));
        produtos.add(new Produto(contadorId++, "carne", 30.90, 20, "Carne bovina de primeira"));
        produtos.add(new Produto(contadorId++, "leite", 5.50, 50, "Leite integral 1L"));
        produtos.add(new Produto(contadorId++, "acucar", 11.90, 50, "Açucar padrão"));
        produtos.add(new Produto(contadorId++, "arroz", 4.90, 30, "Arroz tipo 1, pacote de 1kg"));
        produtos.add(new Produto(contadorId++, "paes", 9.90, 30, "Pão embalagem"));
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    // Exibir a página de cadastro com a lista de produtos
    @GetMapping("/cadastro")
    public String exibirPaginaCadastro(Model model) {
        model.addAttribute("produto", new Produto());
        model.addAttribute("produtos", produtos); // Adiciona a lista de produtos ao modelo
        return "cadastro";
    }

    // Processar o formulário de cadastro
    @PostMapping("/cadastro")
    public String processarCadastro(@ModelAttribute Produto produto) {
        produto.setId(contadorId++);
        produtos.add(produto);
        return "redirect:/produtos/cadastro"; // Redireciona para a mesma página para atualizar a lista
    }

    // Excluir um produto
    @PostMapping("/delete/{id}")
    public String deletarProduto(@PathVariable int id) {
        produtos.removeIf(produto -> produto.getId() == id);
        return "redirect:/produtos/cadastro";
    }

    // Editar um produto
    @GetMapping("/edit/{id}")
    public String editarProduto(@PathVariable int id, Model model) {
        Produto produto = produtos.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
        if (produto == null) {
            return "redirect:/produtos/cadastro";
        }
        model.addAttribute("produto", produto);
        model.addAttribute("produtos", produtos); // Inclui a lista para manter na mesma página
        return "cadastro";
    }

    @PostMapping("/edit/{id}")
    public String salvarEdicao(@PathVariable int id, @ModelAttribute Produto produtoAtualizado) {
        Produto produtoExistente = produtos.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
        if (produtoExistente != null) {

            produtoExistente.setNome(produtoAtualizado.getNome());
            produtoExistente.setPreco(produtoAtualizado.getPreco());
            produtoExistente.setQuantidade(produtoAtualizado.getQuantidade());
            produtoExistente.setDescricao(produtoAtualizado.getDescricao());
        } else {
            // Se o produto não existir, cria um novo (caso de criação)
            produtoAtualizado.setId(contadorId);
            produtos.add(produtoAtualizado);

        }
        return "redirect:/produtos/cadastro";
    }

    // TO DO
//      @GetMapping("/pesquisar/{id}")
//    public ResponseEntity<Produto> getProdutoById(@PathVariable Integer id) {
//        Produto filme = produtoService.getProdutoId(id);
//        return new ResponseEntity<>(produto, HttpStatus.OK);
//
//    }
}
