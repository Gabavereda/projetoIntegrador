package com.example.projetoIntegrador.controller;

import com.example.projetoIntegrador.model.Fornecedor;
import com.example.projetoIntegrador.service.FornecedorService;
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
@RequestMapping("/fornecedores")
public class FornecedorController {

    @Autowired
    FornecedorService fornecedorService;

    private List<Fornecedor> fornecedores = new ArrayList<>();

//    // Bloco estático com exemplos
//    {
//        fornecedores.add(new Fornecedor(contadorId++, "Açougue azevedo", "0900-15-000-00", "rua lirio dos santos", "0500-00", "1111-5555", "azevedo@hotmail"));
//        fornecedores.add(new Fornecedor(contadorId++, "distribuidor  Gomes", "88888-15-000-00", "Pça do carmo", "04598-00", "9999-2121", "gomes@hotmail"));
//        fornecedores.add(new Fornecedor(contadorId++, "Padaria Anjos", "0900-15-111-00", "rua pedro", "5555-00", "33333-5555", "pedro@hotmail"));
//    }
    // página de cadastro fornecedores
    @GetMapping("/cadastrar-fornecedor")
    public String exibirPaginaCadastro(Model model) {

        Fornecedor fornecedor = new Fornecedor();
        // para ja iniciar com a lista de fornecedores na tela 
        model.addAttribute("listarTodosFornecedores", fornecedorService.listarTodosFornecedores());
        model.addAttribute("fornecedor", fornecedor);

        return "cadastrar-fornecedor";
    }

    // cadastrando fornecedor
    @PostMapping("/cadastro")
    public String processarCadastro(@ModelAttribute("fornecedor") Fornecedor fornecedor) {

        if (fornecedor.getId() == null) {
            fornecedorService.criarFornecedor(fornecedor);
        } else {
            fornecedorService.atualizarFornecedor(fornecedor.getId(), fornecedor);
        }

        return "redirect:/fornecedores/cadastrar-fornecedor";
    }

    // editar fornecedor
    @GetMapping("/atualizarFornecedorForm/{id}")
    public String atualizarFornecedorForm(@PathVariable(value = "id") Integer id, Model model) {
        Fornecedor fornecedor = fornecedorService.getFornecedorId(id);

        model.addAttribute("fornecedor", fornecedor);

        return "redirect:/fornecedores/cadastrar-fornecedor";
    }

    //excluir 
    @PostMapping("/deletarFornecedor/{id}")
    public String deletarFornecedor(@PathVariable(value = "id") Integer id) {
        fornecedorService.deletarFornecedor(id);

        return "redirect:/fornecedores/cadastrar-fornecedor";

    }
}
