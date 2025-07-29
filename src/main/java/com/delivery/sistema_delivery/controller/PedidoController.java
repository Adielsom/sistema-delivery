package com.delivery.sistema_delivery.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.delivery.sistema_delivery.model.CarrinhoItem;
import com.delivery.sistema_delivery.services.CarrinhoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class PedidoController {

    private static final Logger logger = LoggerFactory.getLogger(PedidoController.class);

    @Autowired
    private CarrinhoService carrinhoService;

    @PostMapping("/finalizar-pedido")
    public String finalizarPedido(HttpSession session, RedirectAttributes redirectAttributes) {
        List<CarrinhoItem> carrinhoItens = carrinhoService.getCarrinho(session);

        if (carrinhoItens.isEmpty()) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Seu carrinho está vazio. Adicione itens antes de finalizar o pedido.");
            return "redirect:/carrinho";
        }

        carrinhoService.limparCarrinho(session);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Seu pedido foi realizado com sucesso!");
        return "redirect:/confirmacao-pedido";
    }

    @GetMapping("/confirmacao-pedido")
    public String mostrarConfirmacaoPedido(Model model, HttpSession session) {
        // INÍCIO DO CÓDIGO A SER ADICIONADO PARA DEBBUGING
        if (model.containsAttribute("mensagemSucesso")) {
            logger.info("Mensagem de sucesso ENCONTRADA na model: " + model.getAttribute("mensagemSucesso"));
        } else {
            logger.warn("Mensagem de sucesso NÃO ENCONTRADA na model para confirmacao-pedido.");
        }
        // FIM DO CÓDIGO A SER ADICIONADO PARA DEBUGGING
        return "confirmacao-pedido";
    }
}