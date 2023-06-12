/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.unicv.controledepedidos.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javafx.beans.Observable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyFloatWrapper;
import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.util.Callback;

/**
 *
 * @author erik
 */
public class Pedido {

    private final IntegerProperty id = new SimpleIntegerProperty(this, "id", 0);

    private final IntegerProperty codigo = new SimpleIntegerProperty(this, "codigo", 0);

    private final ObjectProperty<LocalDate> data = new SimpleObjectProperty<>(this, "data");

    private final ObjectProperty<Fornecedor> fornecedor = new SimpleObjectProperty<>(this, "fornecedot");

    private final ObservableList<ItemProduto> itensProdutos;

    private final ReadOnlyFloatWrapper total = new ReadOnlyFloatWrapper(this, "total", 0);

    public Pedido() {
        Callback<ItemProduto, Observable[]> extractor
                = (ItemProduto it) -> new Observable[]{
                    it.subTotalProperty()
                };
        itensProdutos = FXCollections.observableArrayList(extractor);
        itensProdutos.addListener((ListChangeListener.Change<? extends ItemProduto> change) -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    int start = change.getFrom();
                    int end = change.getTo();
                    change.getList().subList(start, end).forEach((it) -> {
                        total.set(total.get() + it.getSubTotal());
                    });
                } else if (change.wasUpdated()) {
                    total.set(0);
                    itensProdutos.forEach((it) -> {
                        total.set(total.get() + it.getSubTotal());
                    });
                }

            }
        });

    }

    public Pedido(int codigo, LocalDate data, Fornecedor fornecedor) {
        this();
        this.codigo.set(codigo);
        this.data.set(data);
        this.fornecedor.set(fornecedor);
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

    public int getCodigo() {
        return codigo.get();
    }

    public void setCodigo(int codigo) {
        this.codigo.set(codigo);
    }

    public IntegerProperty codigoProperty() {
        return codigo;
    }

    public LocalDate getData() {
        return data.get();
    }

    public void setData(LocalDate data) {
        this.data.set(data);
    }

    public ObjectProperty<LocalDate> dataProperty() {
        return data;
    }

    public Fornecedor getFornecedor() {
        return fornecedor.get();
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor.set(fornecedor);
    }

    public ObjectProperty<Fornecedor> fornecedorProperty() {
        return fornecedor;
    }

    public float getTotal() {
        return total.get();
    }

    public ReadOnlyProperty totalProperty() {
        return total.getReadOnlyProperty();
    }

    public ObservableList<ItemProduto> getItensProdutos() {
        return itensProdutos;
    }

    public void addItem(Produto produto, int quantidade) {
        ItemProduto item = new ItemProduto(this, produto, quantidade);
        item.setPedido(this);
        itensProdutos.add(item);
    }

    public void addItem(ItemProduto item) {
        item.setPedido(this);
        itensProdutos.add(item);
    }

    public void addItens(List<ItemProduto> itens) {
        itens.forEach((it) -> addItem(it));
    }

    //Recomendado
    public void removeItem(int idProdroduto) {
        Optional<ItemProduto> opt = findItemByProduto(idProdroduto);
        opt.ifPresent((it) -> {
            this.itensProdutos.remove(it);
        });
    }

    public void removeItem(ItemProduto item) {
        this.itensProdutos.remove(item);
    }

    public void removeItem(ObservableList<ItemProduto> itens) {
        this.itensProdutos.removeAll(itens);
    }

    public void updateItem(int idProd, int quantidade) {
        Optional<ItemProduto> opt = findItemByProduto(idProd);
        opt.ifPresent((it) -> {
            it.setQuantidade(quantidade);
        });
    }

    public Optional<ItemProduto> findItemByProduto(int codigo) {
        Optional<ItemProduto> opt = itensProdutos.stream().filter((it) -> {
            return it.getProduto().getCodigo() == codigo;
        }).findFirst();
        return opt;
    }

}
