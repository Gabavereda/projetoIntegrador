package com.example.projetoIntegrador.controller;

import com.example.projetoIntegrador.model.ItemCarrinho;
import com.example.projetoIntegrador.model.Produto;
import com.example.projetoIntegrador.service.ItemCarrinhoService;
import com.example.projetoIntegrador.service.ProdutoService;
import jakarta.servlet.http.HttpSession;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LojaController {

    @Autowired
    ItemCarrinhoService itemCarrinhoservice;
    @Autowired
    ProdutoService produtoService;

    // tela principal
    @GetMapping("/home-loja")
    public String exibirLoja(Model model) {

        ItemCarrinho itemCarrinho = new ItemCarrinho();

        model.addAttribute("listarTodosProduto", produtoService.listarTodosProduto());
        model.addAttribute("listarTodosItemCarrinho", itemCarrinhoservice.listarTodosItemCarrinho());
        model.addAttribute("itemCarrinho", itemCarrinho);

        return "home-loja";
    }

    // montar carrinho em session
    @GetMapping("/loja-carrinho")
    public String mostrarCarrinho(HttpSession session, Model model) {
        List<ItemCarrinho> carrinho = (List<ItemCarrinho>) session.getAttribute("loja-carrinho");

        if (carrinho != null) {
            carrinho = carrinho.stream()
                    .filter(item -> !item.isCompraFinalizada())
                    .collect(Collectors.toList());
        }

        // modelo
        List<Produto> produtos = produtoService.listarTodosProduto();
        model.addAttribute("listarTodosProduto", produtos);

        //  valor total do carrinho
        double total = 0;
        for (ItemCarrinho item : carrinho) {
            double valorItem = item.getProduto().getPreco() * item.getQuantidade();
            total += valorItem;
            /// logzinho
            for (ItemCarrinho itemLog : carrinho) {
                System.out.println("Carrinho Item ID: " + itemLog.getId());
                System.out.println("Produto ID: " + (itemLog.getProduto() != null ? itemLog.getProduto().getId() : "Produto Nulo"));
            }

            // log achando id
            System.out.println("Item: " + item.getProduto().getNome()
                    + ", Quantidade: " + item.getQuantidade()
                    + ", Subtotal: " + valorItem);
        }

        //   para Pix
        double descontoPix = 0.10; // 10% de desconto
        double totalComDescontoPix = total * (1 - descontoPix);
        //  carrinho no console
        System.out.println("Valor total do carrinho: " + total);

        DecimalFormat df = new DecimalFormat("#,##0.00");

        model.addAttribute("carrinho", carrinho);
        model.addAttribute("total", df.format(total));
        model.addAttribute("totalComDescontoPix", df.format(totalComDescontoPix));
        return "loja-carrinho";
    }

    @PostMapping("/adicionar-carrinho")
    public String adicionarCarrinho(
            @RequestParam("idProduto") int idProduto,
            @RequestParam("quantidade") int quantidade,
            HttpSession session,
            Model model) {

        //  produtos disponíveis
        List<Produto> produtos = produtoService.listarTodosProduto();
        Produto produtoSelecionado = produtos.stream()
                .filter(produto -> produto.getId() == idProduto)
                .findFirst()
                .orElse(null);

        if (produtoSelecionado != null) {
            //  na sessão
            List<ItemCarrinho> carrinho = (List<ItemCarrinho>) session.getAttribute("loja-carrinho");
            if (carrinho == null) {
                carrinho = new ArrayList<>();
            }

            //  se o produto já está no carrinho
            ItemCarrinho itemExistente = carrinho.stream()
                    .filter(item -> item.getProduto().getId() == idProduto)
                    .findFirst()
                    .orElse(null);

            if (itemExistente != null) {
                //  quantidade do item existente
                itemExistente.setQuantidade(itemExistente.getQuantidade() + quantidade);
            } else {
                // Lombok
                ItemCarrinho novoItem = new ItemCarrinho();
                novoItem.setProduto(produtoSelecionado);
                novoItem.setQuantidade(quantidade);
                carrinho.add(novoItem);

                //  novo item no banco de dados (se necessário)
                itemCarrinhoservice.criarItemCarrinho(novoItem);
            }

            // atualizadno o carrinho na sessão
            session.setAttribute("loja-carrinho", carrinho);
            model.addAttribute("mensagem", "Você tem produtos no carrinho!");
        } else {
            model.addAttribute("mensagem", "Produto não encontrado.");
        }

        // volta inicio com modelo
        model.addAttribute("produtos", produtos);
        model.addAttribute("listarTodosProduto", produtoService.listarTodosProduto());
        return "home-loja";
    }

    /// SESSAO DA TELA DE VENDAFINALIZACAO
    @PostMapping("/finalizar-venda")
    public ResponseEntity<Void> finalizarVenda(HttpSession session) {
        List<ItemCarrinho> carrinho = (List<ItemCarrinho>) session.getAttribute("loja-carrinho");

        if (carrinho == null || carrinho.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        for (ItemCarrinho item : carrinho) {
            item.setCompraFinalizada(true); //  compra como finalizada
            itemCarrinhoservice.criarItemCarrinho(item);
        }

        session.setAttribute("loja-carrinho", new ArrayList<>()); // impa o carrinho 
        return ResponseEntity.ok().build();
    }

    @PostMapping("/deletarItemCarrinho/{id}")
    public String deletarItemCarrinho(@PathVariable(value = "id") Integer id, HttpSession session) {

        itemCarrinhoservice.deletarItemCarrinho(id);

        session.setAttribute("loja-carrinho", new ArrayList<>());

        return "redirect:/home-loja"; //  após a exclusão
    }

    /// para relatorios
    @GetMapping("/montarTabelaRelatorio")
    public String montarTabelaRelatorio(Model model) {
        List<ItemCarrinho> finalizados = itemCarrinhoservice.listarTodosItemCarrinho()
                .stream()
                .filter(ItemCarrinho::isCompraFinalizada)
                .collect(Collectors.toList());

        List<ItemCarrinho> naoFinalizados = itemCarrinhoservice.listarTodosItemCarrinho()
                .stream()
                .filter(item -> !item.isCompraFinalizada())
                .collect(Collectors.toList());

        model.addAttribute("finalizados", finalizados);
        model.addAttribute("naoFinalizados", naoFinalizados);

        return "relatorio-vendas";
    }

}
