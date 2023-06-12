/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.unicv.controledepedidos.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyFloatProperty;
import javafx.beans.property.ReadOnlyFloatWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 * @author erik
 */
public class ItemProduto {

    private final IntegerProperty id = new SimpleIntegerProperty(this, "id", 0);

   private final ObjectProperty<Pedido> pedido = new SimpleObjectProperty<>(this, "pedido");

    private final ObjectProperty<Produto> produto = new SimpleObjectProperty<>(this, "produto");

    private final IntegerProperty quantidade = new SimpleIntegerProperty(this, "quantidade", 0);

    private final ReadOnlyFloatWrapper subTotal = new ReadOnlyFloatWrapper(this, "subTotal", 0);

    public ItemProduto() {

    }

    public ItemProduto(Pedido pedido, Produto produto, int quantidade) {
        this.pedido.set(pedido);
        this.produto.set(produto);
        this.quantidade.set(quantidade);
        this.subTotal.bind(this.quantidade.multiply(produto.precoUnitarioProperty()));
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public final IntegerProperty idProperty() {
        return id;
    }

    public Pedido getPedido() {
        return pedido.get();
    }

    public void setPedido(Pedido pedido) {
        this.pedido.set(pedido);
    }

    public final ObjectProperty<Pedido> idPedidoProperty() {
        return pedido;
    }

    public Produto getProduto() {
        return produto.get();
    }

    public void setProduto(Produto produto) {
        this.produto.set(produto);
        this.subTotal.bind(this.quantidade.multiply(produto.precoUnitarioProperty()));
    }

    public ObjectProperty<Produto> produtoProperty() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade.get();
    }

    public void setQuantidade(int quantidade) {
        this.quantidade.set(quantidade);
    }

    public IntegerProperty quantidadeProperty() {
        return this.quantidade;
    }

    public float getSubTotal() {
        return subTotal.get();
    }

    public ReadOnlyFloatProperty subTotalProperty() {
        return subTotal.getReadOnlyProperty();
    }    
    
}
