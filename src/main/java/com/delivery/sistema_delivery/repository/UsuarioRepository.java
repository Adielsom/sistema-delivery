package com.delivery.sistema_delivery.repository;
import com.delivery.sistema_delivery.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional; // Importar Optional

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    //buscar um usuário pelo email
    Optional<Usuario> findByEmail(String email);
}