package com.example.projetoIntegrador.controller;

import com.example.projetoIntegrador.model.Funcionario;
import com.example.projetoIntegrador.service.FuncionarioService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    FuncionarioService funcionarioService;

    private List<Funcionario> funcionarios = new ArrayList<>();

    // Bloco estático com exemplos
    {

        // convert para formato data
        try {
            // parsingdata
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            // parsingdata
            Date data1 = sdf.parse("15/05/2020");
            Date data2 = sdf.parse("10/12/2021");
//
//            funcionarios.add(new Funcionario(contadorId++, "Glauber Araujo", "Repositor", "400.157.157-15", data1, 2.050));
//            funcionarios.add(new Funcionario(contadorId++, "João Silva", "Gerente", "123.456.789-00", data2, 3.550));
        } catch (ParseException e) {
            System.out.println("erro a converter data" + e);
        }
    }

    // Exibir a página de cadastro com a lista de funcionarios
    @GetMapping("/cadastrar-funcionario")
    public String exibirPaginaCadastro(Model model) {

        Funcionario funcionario = new Funcionario();

        model.addAttribute("listarTodosFuncionarios", funcionarioService.listarTodosFuncionarios());
        model.addAttribute("funcionario", funcionario);

        return "cadastrar-funcionario";
    }

    // Processar o formulário de cadastro
    @PostMapping("/cadastro")
    public String processarCadastro(@ModelAttribute("funcionario") Funcionario funcionario) {

        if (funcionario.getId() == null) {
            funcionarioService.criarFuncionario(funcionario);
        } else {
            funcionarioService.atualizarFuncionario(funcionario.getId(), funcionario);
        }

        return "redirect:/funcionarios/cadastrar-funcionario"; // Redireciona para a mesma página para atualizar a lista
    }

    // Editar um funcionario
    @GetMapping("/atualizarFuncionarioForm/{id}")
    public String atualizarFuncionarioForm(@PathVariable(value = "id") Integer id, Model model) {
        Funcionario funcionario = funcionarioService.getFuncionarioId(id);

        model.addAttribute("funcionario", funcionario);

        return "redirect:/funcionarios/cadastrar-funcionario";
    }

    // Excluir um funcionario
    @PostMapping("/deletarFuncionario/{id}")
    public String deletarFuncionario(@PathVariable int id) {
        funcionarioService.deletarFuncionario(id);

        return "redirect:/funcionarios/cadastrar-funcionario";
    }


}
