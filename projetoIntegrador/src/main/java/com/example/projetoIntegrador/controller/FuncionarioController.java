package com.example.projetoIntegrador.controller;

import com.example.projetoIntegrador.model.Funcionario;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/funcionarios")
public class FuncionarioController {

    private List<Funcionario> funcionarios = new ArrayList<>();
    private int contadorId = 1;

    // Bloco estático com exemplos
    {
        try {
            // parsingdata
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            // parsingdata
            Date data1 = sdf.parse("15/05/2020");
            Date data2 = sdf.parse("10/12/2021");

            funcionarios.add(new Funcionario(contadorId++, "Glauber Araujo", "Repositor", "400.157.157-15", data1, 2.050));
            funcionarios.add(new Funcionario(contadorId++, "João Silva", "Gerente", "123.456.789-00", data2, 3.550));
        } catch (ParseException e) {
            System.out.println("erro a converter data" + e);
        }
    }

    // Exibir a página de cadastro com a lista de funcionarios
    @GetMapping("/cadastrar-funcionario")
    public String exibirPaginaCadastro(Model model) {
        model.addAttribute("funcionario", new Funcionario());
        model.addAttribute("funcionarios", funcionarios);
        return "cadastrar-funcionario";
    }

    // Processar o formulário de cadastro
    @PostMapping("/cadastro")
    public String processarCadastro(@ModelAttribute Funcionario funcionario) {
        funcionario.setId(contadorId++);
        funcionarios.add(funcionario);
        return "redirect:/funcionarios/cadastrar-funcionario"; // Redireciona para a mesma página para atualizar a lista
    }

    // Excluir um funcionario
    @PostMapping("/delete/{id}")
    public String deletarFuncionario(@PathVariable int id) {
        funcionarios.removeIf(funcionario -> funcionario.getId() == id);
        return "redirect:/funcionarios/cadastrar-funcionario";
    }

    // Editar um funcionario
    @GetMapping("/edit/{id}")
    public String editarFuncionario(@PathVariable int id, Model model) {
        Funcionario funcionario = funcionarios.stream()
                .filter(f -> f.getId() == id)
                .findFirst()
                .orElse(null);
        if (funcionario == null) {
            return "redirect:/funcionarios/cadastrar-funcionario";
        }
        model.addAttribute("funcionario", funcionario);
        model.addAttribute("funcionarios", funcionarios); // Inclui a lista para manter na mesma página
        return "/cadastrar-funcionario";
    }

    @PostMapping("/edit/{id}")
    public String salvarEdicao(@PathVariable int id, @ModelAttribute Funcionario funcionarioAtualizado) {
        Funcionario funcionarioExistente = funcionarios.stream()
                .filter(f -> f.getId() == id)
                .findFirst()
                .orElse(null);
        if (funcionarioExistente != null) {

            funcionarioExistente.setNome(funcionarioAtualizado.getNome());
            funcionarioExistente.setCargo(funcionarioAtualizado.getCargo());
            funcionarioExistente.setCpf(funcionarioAtualizado.getCpf());
            funcionarioExistente.setDataEntrada(funcionarioAtualizado.getDataEntrada());
            funcionarioExistente.setSalario(funcionarioAtualizado.getSalario());
        } else {
            // Se o produto não existir, cria um novo (caso de criação)
            funcionarioAtualizado.setId(contadorId);
            funcionarios.add(funcionarioAtualizado);

        }
        return "redirect:/funcionarios/cadastrar-funcionario";
    }

    // TO DO
//      @GetMapping("/pesquisar/{id}")
//    public ResponseEntity<Produto> getProdutoById(@PathVariable Integer id) {
//        Produto filme = produtoService.getProdutoId(id);
//        return new ResponseEntity<>(produto, HttpStatus.OK);
//
//    }
}
