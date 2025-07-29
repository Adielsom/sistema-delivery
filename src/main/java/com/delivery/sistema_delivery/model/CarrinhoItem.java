package com.delivery.sistema_delivery.model;

import java.io.Serializable;

public class CarrinhoItem implements Serializable {

    private Long produtoId;
    private String nomeProduto;
    private Double precoUnitario;
    private int quantidade;
    private Long restauranteId;
    private String nomeRestaurante;

    public CarrinhoItem() {
    }

    public CarrinhoItem(Long produtoId, String nomeProduto, Double precoUnitario, int quantidade, Long restauranteId, String nomeRestaurante) {
        this.produtoId = produtoId;
        this.nomeProduto = nomeProduto;
        this.precoUnitario = precoUnitario;
        this.quantidade = quantidade;
        this.restauranteId = restauranteId;
        this.nomeRestaurante = nomeRestaurante;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public Double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(Double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Long getRestauranteId() {
        return restauranteId;
    }

    public void setRestauranteId(Long restauranteId) {
        this.restauranteId = restauranteId;
    }

    public String getNomeRestaurante() {
        return nomeRestaurante;
    }

    public void setNomeRestaurante(String nomeRestaurante) {
        this.nomeRestaurante = nomeRestaurante;
    }

    //método para calcular o subtotal do item
    public Double getSubtotal() {
        return this.precoUnitario * this.quantidade;
    }

    @Override
    public String toString() {
        return "CarrinhoItem{" +
                "produtoId=" + produtoId +
                ", nomeProduto='" + nomeProduto + '\'' +
                ", precoUnitario=" + precoUnitario +
                ", quantidade=" + quantidade +
                ", restauranteId=" + restauranteId +
                ", nomeRestaurante='" + nomeRestaurante + '\'' +
                '}';
    }
}