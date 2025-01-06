package com.example.projetoIntegrador.service;

import com.example.projetoIntegrador.model.Produto;
import com.example.projetoIntegrador.model.ProdutoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;

    public Produto criarProduto(Produto produto) {
        produto.setId(null);
        produtoRepository.save(produto);

        return produto;

    }

    public List<Produto> listarProdutosPorNome(String nome) {
        return produtoRepository.findByNomeContainingIgnoreCase(nome);
    }

    public Produto getProdutoId(Integer produtoId) {

        return produtoRepository.findById(produtoId).orElse(null);

    }

    public Produto atualizarProduto(Integer produtoId, Produto produtoRequest) {

        Produto produto = getProdutoId(produtoId);

        produto.setNome(produtoRequest.getNome());
        produto.setPreco(produtoRequest.getPreco());
        produto.setQuantidade(produtoRequest.getQuantidade());
        produto.setDescricao(produtoRequest.getDescricao());

        produtoRepository.save(produto);

        return produto;

    }

    public List<Produto> listarTodosProduto() {
        return produtoRepository.findAll();

    }

    public void deletarProduto(Integer produtoId) {
        Produto produto = getProdutoId(produtoId);
        produtoRepository.deleteById(produto.getId());

    }

}
