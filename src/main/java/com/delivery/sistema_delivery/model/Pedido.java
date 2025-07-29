package com.delivery.sistema_delivery.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
public class Pedido implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne //muitos pedidos para um usuário
    @JoinColumn(name = "usuario_id", nullable = false) //coluna para a chave estrangeira do usuário
    private Usuario usuario;

    @ManyToOne //muitos pedidos podem ser entregues no mesmo endereço
    @JoinColumn(name = "endereco_id", nullable = false) //coluna para a chave estrangeira do endereço
    private Endereco enderecoEntrega; //endereço selecionado para este pedido

    private LocalDateTime dataHoraPedido;

    private Double valorTotal;

    @Enumerated(EnumType.STRING) //armazena o enum como String no BD
    private StatusPedido status;

    private String metodoPagamento;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PedidoItem> itens = new ArrayList<>();

    //construtor padrão (necessário para JPA)
    public Pedido() {
        this.dataHoraPedido = LocalDateTime.now(); //define a data e hora do pedido na criação
        this.status = StatusPedido.PENDENTE;
    }

    //construtor com campos obrigatórios
    public Pedido(Usuario usuario, Endereco enderecoEntrega, String metodoPagamento) {
        this(); //chama o construtor padrão para inicializar dataHoraPedido e status
        this.usuario = usuario;
        this.enderecoEntrega = enderecoEntrega;
        this.metodoPagamento = metodoPagamento;
        this.valorTotal = 0.0; //será calculado ao adicionar itens
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Endereco getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public void setEnderecoEntrega(Endereco enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }

    public LocalDateTime getDataHoraPedido() {
        return dataHoraPedido;
    }

    public void setDataHoraPedido(LocalDateTime dataHoraPedido) {
        this.dataHoraPedido = dataHoraPedido;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public List<PedidoItem> getItens() {
        return itens;
    }

    public void setItens(List<PedidoItem> itens) {
        this.itens = itens;
    }

    //método de conveniência para adicionar um item e atualizar o total
    public void addPedidoItem(PedidoItem item) {
        this.itens.add(item);
        item.setPedido(this);
        if (this.valorTotal == null) {
            this.valorTotal = 0.0;
        }
        this.valorTotal += item.getPrecoUnitario() * item.getQuantidade();
    }
}