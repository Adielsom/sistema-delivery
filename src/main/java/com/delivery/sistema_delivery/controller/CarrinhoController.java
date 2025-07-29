package com.delivery.sistema_delivery.controller;

import com.delivery.sistema_delivery.model.CarrinhoItem;
import com.delivery.sistema_delivery.services.CarrinhoService;
import com.delivery.sistema_delivery.services.PedidoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;

    @Autowired
    private PedidoService pedidoService; // Injete PedidoService aqui

    // Endpoint para adicionar um produto ao carrinho via AJAX
    @PostMapping("/carrinho/adicionar")
    @ResponseBody // Retorna JSON
    public Map<String, Object> adicionarAoCarrinho(@RequestParam Long produtoId,
                                                   @RequestParam(defaultValue = "1") int quantidade,
                                                   HttpSession session) {
        carrinhoService.adicionarAoCarrinho(session, produtoId, quantidade);
        int totalItens = carrinhoService.getNumeroItensNoCarrinho(session);
        return Map.of("success", true, "totalItens", totalItens);
    }

    // Endpoint para remover um produto do carrinho
    @PostMapping("/carrinho/remover")
    public String removerDoCarrinho(@RequestParam Long produtoId, HttpSession session, RedirectAttributes attributes) {
        carrinhoService.removerDoCarrinho(session, produtoId);
        attributes.addFlashAttribute("mensagem", "Produto removido do carrinho!");
        return "redirect:/carrinho";
    }

    // Endpoint para limpar o carrinho
    @PostMapping("/carrinho/limpar")
    public String limparCarrinho(HttpSession session, RedirectAttributes attributes) {
        carrinhoService.limparCarrinho(session);
        attributes.addFlashAttribute("mensagem", "Carrinho limpo!");
        return "redirect:/carrinho";
    }

    // Endpoint para exibir a página do carrinho
    @GetMapping("/carrinho")
    public String verCarrinho(Model model, HttpSession session) {
        List<CarrinhoItem> carrinhoItens = carrinhoService.getCarrinho(session);
        model.addAttribute("carrinhoItens", carrinhoItens);
        model.addAttribute("valorTotal", carrinhoService.getValorTotalCarrinho(session));
        model.addAttribute("totalItensCarrinho", carrinhoService.getNumeroItensNoCarrinho(session)); // Para o contador
        return "carrinho/carrinho"; // Aponta para o novo template HTML
    }

    // Endpoint para finalizar o pedido
    @PostMapping("/carrinho/finalizar") // Novo endpoint POST para finalização
    public String finalizarPedido(@RequestParam String metodoPagamento, HttpSession session, RedirectAttributes attributes) {
        try {
            pedidoService.finalizarPedido(session, metodoPagamento);
            attributes.addFlashAttribute("mensagem", "Seu pedido foi finalizado com sucesso!");
            return "redirect:/confirmacao-pedido"; // Redireciona para uma página de confirmação
        } catch (RuntimeException e) {
            attributes.addFlashAttribute("erro", "Erro ao finalizar pedido: " + e.getMessage());
            return "redirect:/carrinho"; // Em caso de erro, volta para o carrinho
        }
    }

    // Endpoint para obter o número de itens no carrinho (para atualização do contador no frontend)
    @GetMapping("/carrinho/totalItens")
    @ResponseBody
    public Map<String, Integer> getTotalItensCarrinho(HttpSession session) {
        return Map.of("totalItens", carrinhoService.getNumeroItensNoCarrinho(session));
    }
}