package com.delivery.sistema_delivery.services;

import com.delivery.sistema_delivery.model.CarrinhoItem;
import com.delivery.sistema_delivery.model.Produto;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarrinhoService {

    private static final String CARRINHO_SESSION_KEY = "carrinho";

    @Autowired
    private ProdutoService produtoService; //para buscar detalhes do produto
    @Autowired
    private RestauranteService restauranteService; //para buscar detalhes do restaurante

    //método para obter o carrinho da sessão
    @SuppressWarnings("unchecked")
    public List<CarrinhoItem> getCarrinho(HttpSession session) {
        List<CarrinhoItem> carrinho = (List<CarrinhoItem>) session.getAttribute(CARRINHO_SESSION_KEY);
        if (carrinho == null) {
            carrinho = new ArrayList<>();
            session.setAttribute(CARRINHO_SESSION_KEY, carrinho);
        }
        return carrinho;
    }

    //método para adicionar um produto ao carrinho
    public void adicionarAoCarrinho(HttpSession session, Long produtoId, int quantidade) {
        List<CarrinhoItem> carrinho = getCarrinho(session);
        Optional<Produto> produtoOptional = produtoService.buscarPorId(produtoId);

        if (produtoOptional.isPresent()) {
            Produto produto = produtoOptional.get();
            //verifica se o produto já está no carrinho
            Optional<CarrinhoItem> itemExistente = carrinho.stream()
                    .filter(item -> item.getProdutoId().equals(produtoId))
                    .findFirst();

            if (itemExistente.isPresent()) {
                //se já existe, atualiza a quantidade
                itemExistente.get().setQuantidade(itemExistente.get().getQuantidade() + quantidade);
            } else {
                //se não existe, adiciona um novo item
                String nomeRestaurante = restauranteService.buscarPorId(produto.getRestaurante().getId())
                        .map(r -> r.getNome())
                        .orElse("Desconhecido");

                CarrinhoItem novoItem = new CarrinhoItem(
                        produto.getId(),
                        produto.getNome(),
                        produto.getPreco(),
                        quantidade,
                        produto.getRestaurante().getId(),
                        nomeRestaurante
                );
                carrinho.add(novoItem);
            }
            session.setAttribute(CARRINHO_SESSION_KEY, carrinho);
        }
    }

    //método para remover um item do carrinho
    public void removerDoCarrinho(HttpSession session, Long produtoId) {
        List<CarrinhoItem> carrinho = getCarrinho(session);
        carrinho.removeIf(item -> item.getProdutoId().equals(produtoId));
        session.setAttribute(CARRINHO_SESSION_KEY, carrinho);
    }

    //método para limpar o carrinho
    public void limparCarrinho(HttpSession session) {
        session.removeAttribute(CARRINHO_SESSION_KEY);
    }

    //método para obter o número total de itens no carrinho
    public int getNumeroItensNoCarrinho(HttpSession session) {
        List<CarrinhoItem> carrinho = getCarrinho(session);
        return carrinho.stream().mapToInt(CarrinhoItem::getQuantidade).sum();
    }

    //método para calcular o valor total do carrinho
    public Double getValorTotalCarrinho(HttpSession session) {
        List<CarrinhoItem> carrinho = getCarrinho(session);
        return carrinho.stream().mapToDouble(CarrinhoItem::getSubtotal).sum();
    }
}