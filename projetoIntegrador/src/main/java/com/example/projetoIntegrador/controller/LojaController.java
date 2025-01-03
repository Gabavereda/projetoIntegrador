package com.example.projetoIntegrador.controller;

import com.example.projetoIntegrador.model.ItemCarrinho;
import com.example.projetoIntegrador.model.Produto;
import jakarta.servlet.http.HttpSession;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LojaController {

    @Autowired
    private ProdutoController produtoController;

    @GetMapping("/home-loja")
    public String exibirLoja(Model model) {
        List<Produto> produtos = produtoController.getProdutos(); // Obtém os produtos
        model.addAttribute("produtos", produtos); // Passa os produtos para o template
        return "home-loja";
    }

    @GetMapping("/loja-carrinho")
    public String mostrarCarrinho(HttpSession session, Model model) {
        List<ItemCarrinho> carrinho = (List<ItemCarrinho>) session.getAttribute("loja-carrinho");

        if (carrinho == null) {
            carrinho = new ArrayList<>();
        }
        // Calculando o valor total do carrinho
        double total = 0;
        for (ItemCarrinho item : carrinho) {
            double valorItem = item.getProduto().getPreco() * item.getQuantidade();
            total += valorItem;

            // Exibindo cada item do carrinho no console
            System.out.println("Item: " + item.getProduto().getNome()
                    + ", Quantidade: " + item.getQuantidade()
                    + ", Subtotal: " + valorItem);
        }

        // Calculando desconto para Pix
        double descontoPix = 0.10; // 10% de desconto
        double totalComDescontoPix = total * (1 - descontoPix);
        // Exibindo o total do carrinho no console
        System.out.println("Valor total do carrinho: " + total);

        DecimalFormat df = new DecimalFormat("#,##0.00");

        model.addAttribute("carrinho", carrinho);
        model.addAttribute("total", df.format(total));
        model.addAttribute("totalComDescontoPix", df.format(totalComDescontoPix));
        return "loja-carrinho";
    }

    @PostMapping("/adicionar-carrinho")
    public String adicionarCarrinho(@RequestParam("idProduto") int idProduto,
            @RequestParam("quantidade") int quantidade,
            HttpSession session, Model model) {
        List<Produto> produtos = produtoController.getProdutos();
        Produto produtoSelecionado = produtos.stream()
                .filter(produto -> produto.getId() == idProduto)
                .findFirst()
                .orElse(null);

        if (produtoSelecionado != null) {
            List<ItemCarrinho> carrinho = (List<ItemCarrinho>) session.getAttribute("loja-carrinho");
            if (carrinho == null) {
                carrinho = new ArrayList<>();
            }

            ItemCarrinho itemExistente = carrinho.stream()
                    .filter(item -> item.getProduto().getId() == idProduto)
                    .findFirst()
                    .orElse(null);

            if (itemExistente != null) {
                itemExistente.setQuantidade(itemExistente.getQuantidade() + quantidade);
            } else {
                carrinho.add(new ItemCarrinho(produtoSelecionado, quantidade));
            }

            session.setAttribute("loja-carrinho", carrinho);
            model.addAttribute("mensagem", "Voce tem produtos no carrinho!");
        }

        // Retorna a mesma página
        model.addAttribute("produtos", produtos); // Certifique-se de adicionar os produtos novamente ao modelo
        return "home-loja";
    }

    @PostMapping("/remover-carrinho")
    public String removerDoCarrinho(@RequestParam("idProduto") int idProduto, HttpSession session) {
        // Obtém o carrinho da sessão
        List<ItemCarrinho> carrinho = (List<ItemCarrinho>) session.getAttribute("loja-carrinho");

        if (carrinho != null) {
            // Remove o item do carrinho pelo ID do produto
            carrinho.removeIf(item -> item.getProduto().getId() == idProduto);

            // Atualiza o carrinho na sessão
            session.setAttribute("loja-carrinho", carrinho);
        }

        // Redireciona de volta para a página do carrinho
        return "redirect:/loja-carrinho";
    }

    @PostMapping("/finalizar-venda")
    public ResponseEntity<Void> finalizarVenda(@RequestBody Map<String, Object> dadosPagamento, HttpSession session) {
        // Lógica para processar a venda e salvar as informações (dadosPagamento contém as opções de pagamento)
        List<ItemCarrinho> carrinho = (List<ItemCarrinho>) session.getAttribute("loja-carrinho");

        if (carrinho == null || carrinho.isEmpty()) {
            return ResponseEntity.status(400).build(); // Retorna erro se o carrinho estiver vazio
        }

        // Aqui você pode processar a venda, como salvar no banco de dados, gerar um recibo, etc.
        // Após finalizar, pode limpar o carrinho
        session.setAttribute("loja-carrinho", new ArrayList<>());

        return ResponseEntity.ok().build();
    }

}
