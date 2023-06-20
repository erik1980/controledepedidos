/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.unicv.controledepedidos.controllers;

import com.unicv.controledepedidos.data.ProdutoDAOJdbc;
import com.unicv.controledepedidos.exceptions.ServiceException;
import com.unicv.controledepedidos.model.Produto;
import com.unicv.controledepedidos.services.IProdutoService;
import com.unicv.controledepedidos.services.ProdutoService;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author erik
 */
public class GerirProdutosViewController implements Initializable {

    @FXML
    private TableView<Produto> tblProdutos;

    @FXML
    private TableColumn<Produto, Integer> tcolCodigo;

    @FXML
    private TableColumn<Produto, String> tcolDescricao;

    @FXML
    private TableColumn<Produto, Float> tcolPrecoUnitario;

    @FXML
    private TextField txtDescricao;

    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtPrecoUn;

    @FXML
    private Button btnAdicionar;

    @FXML
    private RadioButton rbCodigo;

    @FXML
    private RadioButton rbDescricao;

    @FXML
    private RadioButton rbTodos;

    @FXML
    private TextField txtBuscar;

    private ObservableList<Produto> listaProdutos;

    private final List<Produto> oldlistaProdutos = new ArrayList<>();

    private final List<Produto> toRemoveListaProdutos = new ArrayList<>();

    private IProdutoService produtoService;

    public void handleRadioButtonAction() {
        txtBuscar.clear();
    }

    public void handleBuscarProdutoButtonAction() {
        try {
            listaProdutos.clear();
            if (rbTodos.isSelected()) { 
                txtBuscar.clear();
                listaProdutos.setAll(produtoService.findAll());
            } else if (!txtBuscar.getText().isBlank()) {
                if (rbDescricao.isSelected()) {                   
                    listaProdutos.setAll(produtoService.findByDescricao(txtBuscar.getText()));

                } else {
                    int codigo = Integer.parseInt(txtBuscar.getText());                   
                    Optional<Produto> optionalProduto = produtoService.findByCodigo(codigo);
                    optionalProduto.ifPresent((produto) -> {
                        listaProdutos.add(produto);
                    });
                }
            }
        } catch (NumberFormatException ex) {
            String mssg = "O valor inserido não tem o formato correto";
            showAlertMessage(Alert.AlertType.ERROR, "Error",
                    "Erro buscando um produto", mssg);
        } catch (ServiceException ex) {
            showAlertMessage(Alert.AlertType.ERROR, "Error",
                    "Erro buscando um produto", ex.getMessage());
        }
    }

    public void handleAdicionarButtonAction() {
        Produto novoProduto = new Produto();
        listaProdutos.add(novoProduto);
        tblProdutos.getSelectionModel().select(novoProduto);
        tblProdutos.scrollTo(novoProduto);
        tblProdutos.setDisable(true);
        btnAdicionar.setDisable(true);
        tblProdutos.setOpacity(1);
    }

    public void handleExcluirButtonAction() {
        if (tblProdutos.getSelectionModel().isEmpty()) {
            showAlertMessage(Alert.AlertType.ERROR, "Error",
                    "Error excluindo o produto", "Deve selecionar um produto");
        } else {
            toRemoveListaProdutos.add(tblProdutos.getSelectionModel().getSelectedItem());
            listaProdutos.remove(tblProdutos.getSelectionModel().getSelectedItem());
        }

    }

    public void handleAtualizarButtonAction() {
        if (tblProdutos.getSelectionModel().isEmpty()) {
            showAlertMessage(Alert.AlertType.ERROR, "Error",
                    "Error atualizando o produto", "Deve selecionar um produto");
        } else {
            try {
                Produto selectedProduto = tblProdutos.getSelectionModel().getSelectedItem();
                if (selectedProduto.getId() == 0) {
                    selectedProduto.setCodigo(Integer.parseInt(txtCodigo.getText()));
                    selectedProduto.setDescricao(txtDescricao.getText());
                    selectedProduto.setPrecoUnitario(Float.parseFloat(txtPrecoUn.getText()));
                    produtoService.add(selectedProduto);
                } else {
                    selectedProduto.setCodigo(Integer.parseInt(txtCodigo.getText()));
                    selectedProduto.setDescricao(txtDescricao.getText());
                    selectedProduto.setPrecoUnitario(Float.parseFloat(txtPrecoUn.getText()));
                    produtoService.update(selectedProduto);
                }
                for (Produto produto : toRemoveListaProdutos) {
                    produtoService.remove(produto.getId());
                }
                oldlistaProdutos.clear();
                oldlistaProdutos.addAll(listaProdutos);
                tblProdutos.setDisable(false);
                btnAdicionar.setDisable(false);
            } catch (ServiceException ex) {
                showAlertMessage(Alert.AlertType.ERROR, "Error",
                        "Error atualizando o produto", ex.getMessage());
            }

        }
    }

    public void handleCancelarButtonAction() {
        listaProdutos.clear();
        listaProdutos.setAll(oldlistaProdutos);
        tblProdutos.setDisable(false);
        btnAdicionar.setDisable(false);
        toRemoveListaProdutos.clear();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        produtoService = new ProdutoService(new ProdutoDAOJdbc());
        listaProdutos = FXCollections.emptyObservableList();
        try {
            listaProdutos = FXCollections.observableList(produtoService.findAll());
            oldlistaProdutos.addAll(listaProdutos);
        } catch (ServiceException ex) {
            showAlertMessage(Alert.AlertType.ERROR, "Erro",
                    "Erro carregando os produtos", ex.getMessage());
        }
        tblProdutos.setItems(listaProdutos);
        tcolCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        tcolDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        tcolPrecoUnitario.setCellValueFactory(new PropertyValueFactory<>("precoUnitario"));
        tblProdutos.getSelectionModel().selectedItemProperty().addListener((ov, oldValue, newValue) -> {
             if (oldValue != null) {
                txtCodigo.clear();
                txtDescricao.clear();
                txtPrecoUn.clear();
            }
            if (newValue != null) {
                txtCodigo.setText(Integer.toString(newValue.getCodigo()));
                txtDescricao.setText(newValue.getDescricao());
                txtPrecoUn.setText(Float.toString(newValue.getPrecoUnitario()));
            }
        });
    }    
    private Optional<ButtonType> showAlertMessage(Alert.AlertType type, String title,
            String headerText, String mssg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(mssg);
        return alert.showAndWait();
    }
}
