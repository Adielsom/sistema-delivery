package com.delivery.sistema_delivery.services;
import com.delivery.sistema_delivery.model.Usuario;
import com.delivery.sistema_delivery.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    //método para buscar um usuário pelo ID
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    //método para salvar ou atualizar um usuário
    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

}