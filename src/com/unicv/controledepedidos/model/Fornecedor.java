/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.unicv.controledepedidos.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author erik
 */
public class Fornecedor {

    private final IntegerProperty id = new SimpleIntegerProperty(this, "id", 0);

    private final IntegerProperty codigo = new SimpleIntegerProperty(this, "codigo", 0);

    private final StringProperty nome = new SimpleStringProperty(this, "nome");

    private final StringProperty pais = new SimpleStringProperty(this, "pais");

    public Fornecedor() {
    }

    public Fornecedor( int codigo, String nome, String pais) {        
        this.codigo.set(codigo);
        this.nome.set(nome);
        this.pais.set(pais);
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

    public String getNome() {
        return nome.get();
    }

    public void setNome(String nome) {
        this.nome.set(nome);
    }

    public final StringProperty nomeProperty() {
        return nome;
    }

    public String getPais() {
        return pais.get();
    }

    public void setPais(String pais) {
        this.pais.set(pais);
    }

    public final StringProperty paisProperty() {
        return pais;
    }    
    

}
