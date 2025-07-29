package com.delivery.sistema_delivery.repository;

import com.delivery.sistema_delivery.model.PedidoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PedidoItemRepository extends JpaRepository<PedidoItem, Long> {
    // Ex: List<PedidoItem> findByPedidoId(Long pedidoId);
}