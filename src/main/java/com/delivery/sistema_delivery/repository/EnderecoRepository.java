package com.delivery.sistema_delivery.repository;

import com.delivery.sistema_delivery.model.Endereco;
import com.delivery.sistema_delivery.model.Usuario; // Importar Usuario
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    //método para buscar endereços de um usuário específico
    List<Endereco> findByUsuario(Usuario usuario);
}