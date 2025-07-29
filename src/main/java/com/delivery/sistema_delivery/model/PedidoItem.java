package com.delivery.sistema_delivery.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "pedido_itens") //nome da tabela no banco de dados
public class PedidoItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne //muitos itens para um pedido
    @JoinColumn(name = "pedido_id", nullable = false) //coluna para a chave estrangeira do pedido
    private Pedido pedido;

    @ManyToOne //muitos itens para um produto
    @JoinColumn(name = "produto_id", nullable = false) //coluna para a chave estrangeira do produto
    private Produto produto;

    private Integer quantidade;
    private Double precoUnitario; //preço do produto no momento da compra

    //construtor padrão (necessário para JPA)
    public PedidoItem() {}

    // Construtor com campos obrigatórios
    public PedidoItem(Pedido pedido, Produto produto, Integer quantidade, Double precoUnitario) {
        this.pedido = pedido;
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(Double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }
}