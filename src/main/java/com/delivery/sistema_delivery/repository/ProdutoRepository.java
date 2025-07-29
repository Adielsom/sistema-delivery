
package com.delivery.sistema_delivery.repository;
import com.delivery.sistema_delivery.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByRestauranteId(Long restauranteId);
    //método para buscar produtos ativos por ID do restaurante
    List<Produto> findByRestauranteIdAndAtivoTrue(Long restauranteId);
}