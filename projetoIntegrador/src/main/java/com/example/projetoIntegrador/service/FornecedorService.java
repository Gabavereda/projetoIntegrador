package com.example.projetoIntegrador.service;

import com.example.projetoIntegrador.model.Fornecedor;
import com.example.projetoIntegrador.model.FornecedorRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FornecedorService {

    @Autowired
    FornecedorRepository fornecedorRepository;

    public Fornecedor criarFornecedor(Fornecedor fornecedor) {
        fornecedor.setId(null);
        fornecedorRepository.save(fornecedor);

        return fornecedor;

    }

    public Fornecedor getFornecedorId(Integer fornecedorId) {

        return fornecedorRepository.findById(fornecedorId).orElse(null);

    }

    public Fornecedor atualizarFornecedor(Integer fornecedorId, Fornecedor fornecedorRequest) {

        Fornecedor fornecedor = getFornecedorId(fornecedorId);

        fornecedor.setNome(fornecedorRequest.getNome());
        fornecedor.setCnpj(fornecedorRequest.getCnpj());
        fornecedor.setEndereco(fornecedorRequest.getEndereco());
        fornecedor.setCep(fornecedorRequest.getCep());
        fornecedor.setTelefone(fornecedorRequest.getTelefone());
        fornecedor.setEmail(fornecedorRequest.getEmail());

        fornecedorRepository.save(fornecedor);

        return fornecedor;

    }

    public List<Fornecedor> listarTodosFornecedores() {
        return fornecedorRepository.findAll();

    }

    public void deletarFornecedor(Integer fornecedorId) {
        Fornecedor fornecedor = getFornecedorId(fornecedorId);
        fornecedorRepository.deleteById(fornecedor.getId());

    }

}
