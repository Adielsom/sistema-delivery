
package com.delivery.sistema_delivery.services;

import com.delivery.sistema_delivery.model.Produto;
import com.delivery.sistema_delivery.model.Restaurante;
import com.delivery.sistema_delivery.repository.ProdutoRepository;
import com.delivery.sistema_delivery.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Restaurante> buscarTodos() {
        return restauranteRepository.findAll();
    }

    public Optional<Restaurante> buscarPorId(Long id) {
        return restauranteRepository.findById(id);
    }

    @Transactional //garante que todas as operações dentro do método sejam uma única transação
    public Restaurante salvar(Restaurante restaurante) {
        if (restaurante.getId() == null) {
            //se for um novo restaurante, apenas salva.
            //garante que novos produtos adicionados com o restaurante sejam marcados como ativos.
            if (restaurante.getProdutos() != null) {
                restaurante.getProdutos().forEach(p -> {
                    p.setAtivo(true);
                    p.setRestaurante(restaurante); //garante a associação bidirecional
                });
            }
            return restauranteRepository.save(restaurante);
        } else {
            //se for uma atualização de restaurante existente
            Restaurante restauranteExistente = restauranteRepository.findById(restaurante.getId())
                    .orElseThrow(() -> new RuntimeException("Restaurante não encontrado para atualização!"));

            //atualiza os campos básicos do restaurante existente
            restauranteExistente.setNome(restaurante.getNome());
            restauranteExistente.setEndereco(restaurante.getEndereco());
            restauranteExistente.setImageUrl(restaurante.getImageUrl());
            restauranteExistente.setTelefone(restaurante.getTelefone());

            //lógica para Soft Delete de Produtos Removidos
            //coleta os IDs dos produtos que foram enviados no formulário
            Set<Long> idsProdutosRecebidos = restaurante.getProdutos().stream()
                    .filter(p -> p.getId() != null)
                    .map(Produto::getId)
                    .collect(Collectors.toSet());

            //busca todos os produtos atualmente associados ao restaurante no banco
            List<Produto> produtosAtuaisDoBanco = produtoRepository.findByRestauranteId(restaurante.getId());

            for (Produto produtoExistente : produtosAtuaisDoBanco) {
                //se um produto que existe no banco NÃO está na lista recebida E está ativo, desativa-o
                if (!idsProdutosRecebidos.contains(produtoExistente.getId()) && produtoExistente.isAtivo()) {
                    produtoExistente.setAtivo(false);
                    produtoRepository.save(produtoExistente); //salva a mudança de status do produto
                }
            }

            //lógica para Adicionar Novos Produtos e Atualizar Existentes
            //limpa a coleção de produtos atual do restaurante existente.
            //garantindo que o Hibernate gerencie a coleção corretamente sem orphanRemoval.
            restauranteExistente.getProdutos().clear();

            for (Produto produtoRecebido : restaurante.getProdutos()) {
                if (produtoRecebido.getId() == null) {
                    // associa ao restaurante existente e marca como ativo
                    produtoRecebido.setRestaurante(restauranteExistente);
                    produtoRecebido.setAtivo(true);
                    restauranteExistente.addProduto(produtoRecebido); //adiciona à coleção do restaurante
                } else {
                    //produto existente: busca a versão gerenciada e atualiza seus dados
                    Optional<Produto> produtoOpt = produtoRepository.findById(produtoRecebido.getId());
                    if (produtoOpt.isPresent()) {
                        Produto produtoDoBanco = produtoOpt.get();
                        produtoDoBanco.setNome(produtoRecebido.getNome());
                        produtoDoBanco.setDescricao(produtoRecebido.getDescricao());
                        produtoDoBanco.setPreco(produtoRecebido.getPreco());
                        produtoDoBanco.setImageUrl(produtoRecebido.getImageUrl());
                        produtoDoBanco.setAtivo(true); //garante que o produto editado/reativado seja ativo

                        restauranteExistente.addProduto(produtoDoBanco); //adiciona à coleção do restaurante
                    }

                }
            }
            //salva o restaurante com sua coleção de produtos atualizada.
            return restauranteRepository.save(restauranteExistente);
        }
    }

    public void deletar(Long id) {
        restauranteRepository.deleteById(id);
    }

    public List<Restaurante> buscarPorNome(String nome) {
        return restauranteRepository.findByNomeContainingIgnoreCase(nome);
    }
}