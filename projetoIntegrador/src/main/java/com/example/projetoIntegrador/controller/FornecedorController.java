package com.example.projetoIntegrador.controller;

import com.example.projetoIntegrador.model.Fornecedor;
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
@RequestMapping("/fornecedores")
public class FornecedorController {

    private List<Fornecedor> fornecedores = new ArrayList<>();
    private int contadorId = 1;

    // Bloco estático com exemplos
    {
        fornecedores.add(new Fornecedor(contadorId++, "Açougue azevedo", "0900-15-000-00", "rua lirio dos santos", "0500-00", "1111-5555", "azevedo@hotmail"));
        fornecedores.add(new Fornecedor(contadorId++, "distribuidor  Gomes", "88888-15-000-00", "Pça do carmo", "04598-00", "9999-2121", "gomes@hotmail"));
        fornecedores.add(new Fornecedor(contadorId++, "Padaria Anjos", "0900-15-111-00", "rua pedro", "5555-00", "33333-5555", "pedro@hotmail"));
    }

    // página de cadastro fornecedores
    @GetMapping("/cadastrar-fornecedor")
    public String exibirPaginaCadastro(Model model) {

        model.addAttribute("fornecedor", new Fornecedor());
        model.addAttribute("fornecedores", fornecedores);

        return "cadastrar-fornecedor";
    }

    // formulario cadastro
    @PostMapping("/cadastro")
    public String processarCadastro(@ModelAttribute Fornecedor fornecedor) {
        fornecedor.setId(contadorId++);
        fornecedores.add(fornecedor);
        return "redirect:/fornecedores/cadastrar-fornecedor";
    }

    //excluir 
    @PostMapping("/delete/{id}")
    public String deletarFornecedor(@PathVariable int id) {
        fornecedores.removeIf(fornecedor -> fornecedor.getId() == id);

        return "redirect:/fornecedores/cadastrar-fornecedor";

    }

    // editar fornecedor
    @GetMapping("/edit/{id}")
    public String editarFornecedor(@PathVariable int id, Model model) {
        Fornecedor fornecedor = fornecedores.stream()
                .filter(f -> f.getId() == id)
                .findFirst()
                .orElse(null);
        if (fornecedor == null) {
            return "redirect:/fornecedores/cadastrar-fornecedor";

        }
        model.addAttribute("fornecedor", fornecedor);
        model.addAttribute("fornecedores", fornecedores);
        return "/cadastrar-fornecedor";
    }

    @PostMapping("/edit/{id}")
    public String salvarEdicao(@PathVariable int id, @ModelAttribute Fornecedor fornecedorAtualizado) {
        Fornecedor fornecedorExistente = fornecedores.stream()
                .filter(f -> f.getId() == id)
                .findFirst()
                .orElse(null);
        if (fornecedorExistente != null) {
            fornecedorExistente.setNome(fornecedorAtualizado.getNome());
            fornecedorExistente.setCnpj(fornecedorAtualizado.getCnpj());
            fornecedorExistente.setEndereco(fornecedorAtualizado.getEndereco());
            fornecedorExistente.setCep(fornecedorAtualizado.getCep());
            fornecedorExistente.setTelefone(fornecedorAtualizado.getTelefone());
            fornecedorExistente.setEmail(fornecedorAtualizado.getEmail());
        } else {
            // Se o fornecedor não existir, cria um novo (caso de criação)
            fornecedorAtualizado.setId(contadorId);
            fornecedores.add(fornecedorAtualizado);

        }
        return "redirect:/fornecedores/cadastrar-fornecedor";

    }
}
