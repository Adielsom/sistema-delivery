package com.delivery.sistema_delivery.controller;

import com.delivery.sistema_delivery.model.Restaurante;
import com.delivery.sistema_delivery.model.Produto;
import com.delivery.sistema_delivery.services.RestauranteService;
import com.delivery.sistema_delivery.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private ProdutoService produtoService;

    //Endpoint para listar TODOS os restaurantes
    @GetMapping({"", "/"})
    public String listarRestaurantes(Model model) {
        List<Restaurante> restaurantes = restauranteService.buscarTodos();
        model.addAttribute("restaurantes", restaurantes);
        if (model.containsAttribute("mensagem")) {
            model.addAttribute("mensagem", model.getAttribute("mensagem"));
        }
        if (model.containsAttribute("erro")) {
            model.addAttribute("erro", model.getAttribute("erro"));
        }
        return "restaurantes/lista-restaurantes";
    }

    //Endpoint para exibir o formulário de criação de novo restaurante
    @GetMapping("/novo")
    public String exibirFormularioCriacao(Model model) {
        model.addAttribute("restaurante", new Restaurante());
        return "restaurantes/formulario";
    }

    //Endpoint para SALVAR ou ATUALIZAR um restaurante
    // Este método agora usará a lógica aprimorada no RestauranteService.salvar()
    @PostMapping("/salvar")
    public String salvarRestaurante(Restaurante restaurante, RedirectAttributes attributes) {
        restauranteService.salvar(restaurante); //
        attributes.addFlashAttribute("mensagem", "Restaurante salvo com sucesso!");
        return "redirect:/restaurantes";
    }

    //Endpoint para exibir o formulário de EDIÇÃO de um restaurante existente
    @GetMapping("/editar/{id}")
    public String exibirFormularioEdicao(@PathVariable Long id, Model model, RedirectAttributes attributes) {
        Optional<Restaurante> restauranteOptional = restauranteService.buscarPorId(id);
        if (restauranteOptional.isPresent()) {
            model.addAttribute("restaurante", restauranteOptional.get());
            return "restaurantes/formulario";
        } else {
            attributes.addFlashAttribute("erro", "Restaurante não encontrado para edição.");
            return "redirect:/restaurantes";
        }
    }

    //Endpoint para DELETAR um restaurante (usando POST para segurança)
    @PostMapping("/deletar/{id}")
    public String deletarRestaurante(@PathVariable Long id, RedirectAttributes attributes) {
        Optional<Restaurante> restauranteOptional = restauranteService.buscarPorId(id);
        if (restauranteOptional.isPresent()) {
            restauranteService.deletar(id);
            attributes.addFlashAttribute("mensagem", "Restaurante excluído com sucesso!");
        } else {
            attributes.addFlashAttribute("erro", "Restaurante não encontrado para exclusão.");
        }
        return "redirect:/restaurantes";
    }

    //Endpoint para exibir o cardápio de um restaurante específico e seus produtos
    @GetMapping("/{restauranteId}/produtos")
    public String exibirCardapioRestaurante(@PathVariable("restauranteId") Long restauranteId, Model model, RedirectAttributes attributes) {
        Optional<Restaurante> restauranteOptional = restauranteService.buscarPorId(restauranteId);

        if (restauranteOptional.isPresent()) {
            Restaurante restaurante = restauranteOptional.get();
            model.addAttribute("restaurante", restaurante);

            // ATUALIZADO: Busca APENAS os produtos ATIVOS associados a este restaurante
            List<Produto> produtosDoRestaurante = produtoService.buscarProdutosPorRestauranteAtivos(restaurante.getId());
            model.addAttribute("produtos", produtosDoRestaurante);

            if (model.containsAttribute("mensagem")) {
                model.addAttribute("mensagem", model.getAttribute("mensagem"));
            }
            if (model.containsAttribute("erro")) {
                model.addAttribute("erro", model.getAttribute("erro"));
            }

            return "restaurantes/cardapio-restaurante";
        } else {
            attributes.addFlashAttribute("erro", "Restaurante não encontrado.");
            return "redirect:/restaurantes";
        }
    }
}