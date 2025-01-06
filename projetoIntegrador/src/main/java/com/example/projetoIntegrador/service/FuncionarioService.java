package com.example.projetoIntegrador.service;

import com.example.projetoIntegrador.model.Funcionario;
import com.example.projetoIntegrador.model.FuncionarioRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FuncionarioService {

    @Autowired
    FuncionarioRepository funcionarioRepository;

    public Funcionario criarFuncionario(Funcionario funcionario) {
        funcionario.setId(null);
        funcionarioRepository.save(funcionario);

        return funcionario;

    }

    public Funcionario getFuncionarioId(Integer funcionarioId) {

        return funcionarioRepository.findById(funcionarioId).orElse(null);

    }

    public Funcionario atualizarFuncionario(Integer funcionarioId, Funcionario funcionarioRequest) {

        Funcionario funcionario = getFuncionarioId(funcionarioId);

        funcionario.setNome(funcionarioRequest.getNome());
        funcionario.setCargo(funcionarioRequest.getCargo());
        funcionario.setDataEntrada(funcionarioRequest.getDataEntrada());
        funcionario.setSalario(funcionarioRequest.getSalario());

        funcionarioRepository.save(funcionario);

        return funcionario;

    }

    public List<Funcionario> listarTodosFuncionarios() {
        return funcionarioRepository.findAll();

    }

    public void deletarFuncionario(Integer funcionarioId) {
        Funcionario funcionario = getFuncionarioId(funcionarioId);
        funcionarioRepository.deleteById(funcionario.getId());

    }

}
