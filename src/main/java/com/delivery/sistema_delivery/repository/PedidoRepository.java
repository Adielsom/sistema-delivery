package com.delivery.sistema_delivery.repository;

import com.delivery.sistema_delivery.model.Pedido;
import com.delivery.sistema_delivery.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByUsuario(Usuario usuario);
}