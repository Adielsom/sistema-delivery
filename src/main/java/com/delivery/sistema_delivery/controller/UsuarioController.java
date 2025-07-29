package com.delivery.sistema_delivery.controller;

import com.delivery.sistema_delivery.model.Usuario;
import com.delivery.sistema_delivery.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate; // Importar LocalDate
import java.util.Optional;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/perfil/{id}")
    public String exibirPerfilUsuario(@PathVariable Long id, Model model) {
        Optional<Usuario> usuarioOptional = usuarioService.buscarPorId(id);

        if (usuarioOptional.isPresent()) {
            model.addAttribute("usuario", usuarioOptional.get());
            return "usuario/perfil";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/criar-usuario-teste")
    public String criarUsuarioTeste() {
        // Verifica se já existe um usuário de teste para não duplicar
        Optional<Usuario> existingUser = usuarioService.buscarPorId(1L);

        if (!existingUser.isPresent()) {
            Usuario usuarioTeste = new Usuario(
                    "João da Silva",
                    "joao.silva@example.com",
                    LocalDate.of(1990, 5, 15),
                    "/images/perfil_usuario.png"
            );
            usuarioService.salvar(usuarioTeste);
            System.out.println("Usuário de teste 'João da Silva' criado com sucesso!");
        } else {
            System.out.println("Usuário de teste já existe.");
        }
        return "redirect:/perfil/1";
    }
}