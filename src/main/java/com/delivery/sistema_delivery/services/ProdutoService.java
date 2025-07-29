
package com.delivery.sistema_delivery.services;

import com.delivery.sistema_delivery.model.Produto;
import com.delivery.sistema_delivery.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Produto> buscarTodos() {
        return produtoRepository.findAll();
    }

    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }

    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }

    public void deletar(Long id) {
        produtoRepository.deleteById(id);
    }

    public List<Produto> buscarProdutosPorRestaurante(Long restauranteId) {
        return produtoRepository.findByRestauranteId(restauranteId);
    }

    //busca apenas produtos ATIVOS de um restaurante
    public List<Produto> buscarProdutosPorRestauranteAtivos(Long restauranteId) {
        return produtoRepository.findByRestauranteIdAndAtivoTrue(restauranteId);
    }
}