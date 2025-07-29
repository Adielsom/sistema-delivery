package com.delivery.sistema_delivery.controller;

import com.delivery.sistema_delivery.model.Produto;
import com.delivery.sistema_delivery.model.Restaurante;
import com.delivery.sistema_delivery.services.ProdutoService;
import com.delivery.sistema_delivery.services.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private RestauranteService restauranteService;

    @GetMapping("/produtos")
    public String listarTodosProdutos(Model model) {
        List<Produto> produtos = produtoService.buscarTodos();
        model.addAttribute("produtos", produtos);
        return "produtos/lista_produtos_geral";
    }

    @GetMapping("/restaurantes/{restauranteId}/produtos/novo")
    public String exibirFormularioCriacaoProduto(@PathVariable Long restauranteId, Model model, RedirectAttributes attributes) {
        Optional<Restaurante> restauranteOptional = restauranteService.buscarPorId(restauranteId);
        if (restauranteOptional.isPresent()) {
            Produto produto = new Produto();
            produto.setRestaurante(restauranteOptional.get()); //associa o produto ao restaurante
            model.addAttribute("produto", produto);
            model.addAttribute("restaurante", restauranteOptional.get()); // Adiciona o restaurante para exibir o nome
            return "produtos/form-produto"; // Nome do seu arquivo HTML de formulário de produto
        } else {
            attributes.addFlashAttribute("erro", "Restaurante não encontrado para adicionar produto.");
            return "redirect:/restaurantes";
        }
    }

    //processa o envio do formulário para salvar um produto
    @PostMapping("/produtos/salvar")
    public String salvarProduto(Produto produto, RedirectAttributes attributes) {
        //verifica se o produto tem um restaurante associado e se o ID do restaurante é válido
        if (produto.getRestaurante() != null && produto.getRestaurante().getId() != null) {
            Optional<Restaurante> restauranteOptional = restauranteService.buscarPorId(produto.getRestaurante().getId());
            if (restauranteOptional.isPresent()) {
                produto.setRestaurante(restauranteOptional.get()); //garante que o objeto Restaurante esteja gerenciado
                produtoService.salvar(produto);
                attributes.addFlashAttribute("mensagem", "Produto salvo com sucesso!");
                //redireciona de volta para o cardápio do restaurante específico
                return "redirect:/restaurantes/" + produto.getRestaurante().getId() + "/produtos";
            } else {
                attributes.addFlashAttribute("erro", "Restaurante não encontrado para associar o produto.");
                return "redirect:/restaurantes";
            }
        } else {
            attributes.addFlashAttribute("erro", "Restaurante não especificado para o produto.");
            return "redirect:/restaurantes";
        }
    }

    //exibe o formulário para editar um produto existente
    @GetMapping("/produtos/editar/{id}")
    public String exibirFormularioEdicaoProduto(@PathVariable Long id, Model model, RedirectAttributes attributes) {
        Optional<Produto> produtoOptional = produtoService.buscarPorId(id);
        if (produtoOptional.isPresent()) {
            Produto produto = produtoOptional.get();
            model.addAttribute("produto", produto);
            model.addAttribute("restaurante", produto.getRestaurante()); //adiciona o restaurante associado
            return "produtos/form-produto"; //reutiliza o formulário de produto
        } else {
            attributes.addFlashAttribute("erro", "Produto não encontrado para edição.");
            return "redirect:/restaurantes";
        }
    }

    //deleta um produto
    @PostMapping("/produtos/deletar/{id}") // Use @PostMapping para exclusão
    public String deletarProduto(@PathVariable Long id, RedirectAttributes attributes) {
        Optional<Produto> produtoOptional = produtoService.buscarPorId(id);
        if (produtoOptional.isPresent()) {
            Long restauranteId = produtoOptional.get().getRestaurante().getId(); //pega o ID do restaurante antes de deletar
            produtoService.deletar(id);
            attributes.addFlashAttribute("mensagem", "Produto excluído com sucesso!");
            return "redirect:/restaurantes/" + restauranteId + "/produtos"; //redireciona para o cardápio do restaurante
        } else {
            attributes.addFlashAttribute("erro", "Produto não encontrado para exclusão.");
            return "redirect:/restaurantes";
        }
    }
}