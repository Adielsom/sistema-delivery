package com.delivery.sistema_delivery.services;

import com.delivery.sistema_delivery.model.CarrinhoItem;
import com.delivery.sistema_delivery.model.Endereco;
import com.delivery.sistema_delivery.model.Pedido;
import com.delivery.sistema_delivery.model.PedidoItem;
import com.delivery.sistema_delivery.model.Produto;
import com.delivery.sistema_delivery.model.Usuario;
import com.delivery.sistema_delivery.repository.EnderecoRepository;
import com.delivery.sistema_delivery.repository.PedidoItemRepository;
import com.delivery.sistema_delivery.repository.PedidoRepository;
import com.delivery.sistema_delivery.repository.ProdutoRepository;
import com.delivery.sistema_delivery.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private PedidoItemRepository pedidoItemRepository; //embora Pedido cascade, é bom ter para operações separadas
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CarrinhoService carrinhoService;

    @Transactional //garante que toda a operação seja atômica
    public Pedido finalizarPedido(HttpSession session, String metodoPagamento) {
        List<CarrinhoItem> carrinhoItens = carrinhoService.getCarrinho(session);

        if (carrinhoItens.isEmpty()) {
            System.out.println("Não é possível finalizar um pedido com o carrinho vazio.");
            return null;
        }

        //obter o Usuário e Endereço
        //vamos usar o usuário de teste "joana@example.com" e seu primeiro endereço.
        Usuario usuario = usuarioRepository.findByEmail("oliveiraadielson@gmail")
                .orElseThrow(() -> new RuntimeException("Usuário de teste 'oliveiraadielson@gmail' não encontrado."));

        List<Endereco> enderecosUsuario = enderecoRepository.findByUsuario(usuario);
        Endereco enderecoEntrega = null;
        if (!enderecosUsuario.isEmpty()) {
            enderecoEntrega = enderecosUsuario.get(0);
        } else {
            throw new RuntimeException("Nenhum endereço encontrado para o usuário 'oliveiraadielson@gmail'. Crie um endereço de teste.");
        }


        //criar o Pedido
        Pedido pedido = new Pedido(usuario, enderecoEntrega, metodoPagamento);
        pedido.setDataHoraPedido(LocalDateTime.now()); // Garante que a data/hora seja a atual
        //o valor total será calculado ao adicionar os itens ao pedido


        //adicionar Itens ao Pedido
        for (CarrinhoItem itemCarrinho : carrinhoItens) {
            Optional<Produto> produtoOpt = produtoRepository.findById(itemCarrinho.getProdutoId());
            if (produtoOpt.isPresent()) {
                Produto produto = produtoOpt.get();
                //o preço unitário no PedidoItem deve ser o preço do produto no momento da compra,
                //para evitar problemas se o preço do produto mudar no futuro.
                PedidoItem pedidoItem = new PedidoItem(pedido, produto, itemCarrinho.getQuantidade(), produto.getPreco());
                pedido.addPedidoItem(pedidoItem);
            } else {
                System.err.println("Produto com ID " + itemCarrinho.getProdutoId() + " não encontrado. Ignorando item do carrinho.");

            }
        }

        if (pedido.getItens().isEmpty()) {
            //se nenhum item válido foi adicionado ao pedido, não salve.
            System.out.println("Nenhum item válido para criar o pedido.");
            return null;
        }

        //Salvar o Pedido
        pedidoRepository.save(pedido);
        System.out.println("Pedido " + pedido.getId() + " finalizado com sucesso!");
        System.out.println("Método de Pagamento: " + metodoPagamento);
        System.out.println("Valor Total: R$ " + String.format("%.2f", pedido.getValorTotal()));
        System.out.println("Endereço de Entrega: " + pedido.getEnderecoEntrega().getRua() + ", " + pedido.getEnderecoEntrega().getNumero());

        //Limpar o carrinho da sessão
        carrinhoService.limparCarrinho(session);

        return pedido; //retorna o pedido salvo
    }
}