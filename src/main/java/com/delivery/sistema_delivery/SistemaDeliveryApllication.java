package com.delivery.sistema_delivery;

import com.delivery.sistema_delivery.model.Usuario;
import com.delivery.sistema_delivery.model.Restaurante;
import com.delivery.sistema_delivery.model.Produto;
import com.delivery.sistema_delivery.model.Endereco;
import com.delivery.sistema_delivery.repository.UsuarioRepository;
import com.delivery.sistema_delivery.repository.RestauranteRepository;
import com.delivery.sistema_delivery.repository.ProdutoRepository;
import com.delivery.sistema_delivery.repository.EnderecoRepository;


import com.delivery.sistema_delivery.factory_method.Notification;
import com.delivery.sistema_delivery.factory_method.NotificationFactory;
import com.delivery.sistema_delivery.factory_method.EmailNotificationFactory;
import com.delivery.sistema_delivery.factory_method.SMSNotificationFactory;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootApplication
public class SistemaDeliveryApllication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaDeliveryApllication.class, args);
	}

	//bean para adicionar usuário de teste
	@Bean
	public CommandLineRunner demoUsers(UsuarioRepository usuarioRepository) {
		return args -> {
			//verifica se Dyel já existe; se não, a cria.
			Optional<Usuario> joanaOpt = usuarioRepository.findByEmail("oliveiraadielson@gmail");
			if (joanaOpt.isEmpty()) {
				usuarioRepository.save(new Usuario("Dyel Caxtro", "oliveiraadielson@gmail", LocalDate.of(2002, 2, 16), ""));
				System.out.println("Usuário 'oliveiraadielson@gmail' criado!");
			} else {
				System.out.println("Usuário 'oliveiraadielson@gmail' já existe.");
			}

			// Verifica se Carlos já existe; se não, o cria.
			Optional<Usuario> carlosOpt = usuarioRepository.findByEmail("carlos@example.com");
			if (carlosOpt.isEmpty()) {
				usuarioRepository.save(new Usuario("Carlos Pereira", "carlos@example.com", LocalDate.of(1985, 11, 22), ""));
				System.out.println("Usuário 'carlos@example.com' criado!");
			} else {
				System.out.println("Usuário 'carlos@example.com' já existe.");
			}

			// Se nenhum usuário foi criado nesta execução, informa que usuários já existiam.
			if (joanaOpt.isPresent() && carlosOpt.isPresent()) {
				System.out.println("Todos os usuários de teste já existiam.");
			}
		};
	}

	// Bean para adicionar restaurantes de teste e seus produtos
	@Bean
	public CommandLineRunner demoRestaurantesAndProdutos(
			RestauranteRepository restauranteRepository,
			ProdutoRepository produtoRepository) {
		return args -> {
			if (restauranteRepository.count() == 0) {
				// Restaurante 1: Pizza da Vila
				Restaurante pizzaDaVila = new Restaurante(
						"Pizza da Vila",
						"Rua das Pizzas, 123 - Centro",
						"(11) 98765-4321", // Telefone
						"https://via.placeholder.com/150/FF5733/FFFFFF?text=PizzaVila"
				);
				restauranteRepository.save(pizzaDaVila);

				produtoRepository.save(new Produto("Pizza Calabresa", "Molho, mussarela, calabresa fatiada, cebola", 34.90, "https://via.placeholder.com/100/FF5733/FFFFFF?text=Calabresa"));
				produtoRepository.save(new Produto("Pizza Portuguesa", "Molho, mussarela, presunto, ovos, cebola, azeitona", 38.00, "https://via.placeholder.com/100/FF5733/FFFFFF?text=Portuguesa"));
				produtoRepository.save(new Produto("Refrigerante Coca-Cola", "Lata 350ml", 7.00, "https://via.placeholder.com/100/FF5733/FFFFFF?text=Coca"));
				// Busca os produtos sem restaurante e os associa à Pizza da Vila
				produtoRepository.findAll().forEach(p -> {
					if (p.getRestaurante() == null) {
						p.setRestaurante(pizzaDaVila);
						produtoRepository.save(p);
					}
				});


				// Restaurante 2: Sabor Caseiro
				Restaurante saborCaseiro = new Restaurante(
						"Sabor Caseiro",
						"Av. Principal, 456 - Bairro Novo",
						"(21) 12345-6789", // Telefone
						"https://via.placeholder.com/150/33FF57/FFFFFF?text=SaborCaseiro"
				);
				restauranteRepository.save(saborCaseiro);

				Produto feijoada = new Produto("Feijoada", "Feijoada completa com arroz, couve e farofa", 29.90, "https://via.placeholder.com/100/33FF57/FFFFFF?text=Feijoada");
				feijoada.setRestaurante(saborCaseiro);
				produtoRepository.save(feijoada);

				Produto macarronada = new Produto("Macarronada", "Massa com molho bolonhesa tradicional", 24.50, "https://via.placeholder.com/100/33FF57/FFFFFF?text=Macarronada");
				macarronada.setRestaurante(saborCaseiro);
				produtoRepository.save(macarronada);

				Produto saladaFruta = new Produto("Salada de Fruta", "Mix de frutas frescas da estação", 15.00, "https://via.placeholder.com/100/33FF57/FFFFFF?text=SaladaFruta");
				saladaFruta.setRestaurante(saborCaseiro);
				produtoRepository.save(saladaFruta);

				System.out.println("Restaurantes e Produtos de teste criados e associados!");
			} else {
				System.out.println("Restaurantes e Produtos já existem.");
			}
		};
	}

	// Bean para adicionar endereços de teste (após usuários serem criados)
	@Bean
	public CommandLineRunner demoEnderecos(
			UsuarioRepository usuarioRepository,
			EnderecoRepository enderecoRepository) {
		return args -> {
			Usuario joana = usuarioRepository.findByEmail("oliveiraadielson@gmail").orElse(null);
			if (joana != null) {
				// Verifica se já existem endereços para Joana. Se não tiver nenhum, cria os dois.
				if (enderecoRepository.findByUsuario(joana).isEmpty()) {
					enderecoRepository.save(new Endereco("Rua das Flores", "100", "Ap. 101", "Jardim", "São Paulo", "SP", "01000-000", joana));
					enderecoRepository.save(new Endereco("Av. Central", "500", "Casa C", "Centro", "São Paulo", "SP", "02000-000", joana));
					System.out.println("Endereços de teste para Joana criados!");
				} else {
					System.out.println("Endereços para 'joana@example.com' já existem.");
				}
			} else {
				System.out.println("Usuário 'joana@example.com' não encontrado. Não foi possível criar/verificar endereços de teste.");
			}
		};
	}

	//Demonstração do Padrão Factory Method
	@Bean
	public CommandLineRunner runFactoryMethodDemo() {
		return args -> {
			System.out.println("\nPadrão Factory Method");

			// Usando a fábrica de E-mail
			NotificationFactory emailFactory = new EmailNotificationFactory();
			Notification emailNotification = emailFactory.createNotification();
			emailNotification.send("Seu pedido #FM001 foi recebido via Factory Method!");

			System.out.println("----------------------------------------------");

			// Usando a fábrica de SMS
			NotificationFactory smsFactory = new SMSNotificationFactory();
			Notification smsNotification = smsFactory.createNotification();
			smsNotification.send("Seu pedido #FM001 está a caminho via Factory Method!");

			System.out.println("----------------------------------------------");
			System.out.println("Demonstração do Factory Method finalizada.");
		};
	}
}