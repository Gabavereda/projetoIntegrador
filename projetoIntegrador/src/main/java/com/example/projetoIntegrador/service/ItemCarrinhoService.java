package com.example.projetoIntegrador.service;

import com.example.projetoIntegrador.model.ItemCarrinho;
import com.example.projetoIntegrador.model.ItemCarrinhoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemCarrinhoService {

    @Autowired
    ItemCarrinhoRepository itemCarrinhoRepository;

    public ItemCarrinho criarItemCarrinho(ItemCarrinho itemCarrinho) {
        itemCarrinho.setId(null);
        itemCarrinhoRepository.save(itemCarrinho);

        return itemCarrinho;

    }

    public ItemCarrinho getItemCarrinhoId(Integer itemCarrinhoId) {

        return itemCarrinhoRepository.findById(itemCarrinhoId).orElse(null);

    }

    public ItemCarrinho atualizarItemCarrinho(Integer itemCarrinhoId, ItemCarrinho itemCarrinhoRequest) {

        ItemCarrinho itemCarrinho = getItemCarrinhoId(itemCarrinhoId);

        itemCarrinho.setProduto(itemCarrinhoRequest.getProduto());
        itemCarrinho.setQuantidade(itemCarrinhoRequest.getQuantidade());

        itemCarrinhoRepository.save(itemCarrinho);

        return itemCarrinho;

    }

    public List<ItemCarrinho> listarTodosItemCarrinho() {
        return itemCarrinhoRepository.findAll();

    }

    public void deletarItemCarrinho(Integer itemCarrinhoId) {
        ItemCarrinho itemCarrinho = getItemCarrinhoId(itemCarrinhoId);
        itemCarrinhoRepository.deleteById(itemCarrinho.getId());

    }

}
