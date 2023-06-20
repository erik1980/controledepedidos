/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.unicv.controledepedidos.model;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author erik
 */
public class Produto {

    private final IntegerProperty id = new SimpleIntegerProperty(this, "id", 0);

    private final IntegerProperty codigo = new SimpleIntegerProperty(this, "codigo", 0);

    private final StringProperty descricao = new SimpleStringProperty(this, "descricao","");

    private final FloatProperty precoUnitario = new SimpleFloatProperty(this, "precoUnitario", 0);

    public Produto() {
    }

    public Produto(int codigo, String descricao, float precoUnitario) {       
        this.codigo.set(codigo);
        this.descricao.set(descricao);
        this.precoUnitario.set(precoUnitario);
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

    public final IntegerProperty codigoProperty() {
        return codigo;
    }

    public String getDescricao() {
        return descricao.get();
    }

    public void setDescricao(String descricao) {
        this.descricao.set(descricao);
    }

    public final StringProperty descricaoProperty() {
        return descricao;
    }

    public float getPrecoUnitario() {
        return precoUnitario.get();
    }

    public void setPrecoUnitario(float precoUnitario) {
        this.precoUnitario.set(precoUnitario);
    }

    public final FloatProperty precoUnitarioProperty() {
        return precoUnitario;
    }

}
