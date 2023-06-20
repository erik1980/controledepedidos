/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.unicv.controledepedidos.controllers;

import com.unicv.controledepedidos.data.ProdutoDAOJdbc;
import com.unicv.controledepedidos.exceptions.ServiceException;
import com.unicv.controledepedidos.model.Fornecedor;
import com.unicv.controledepedidos.model.ItemProduto;
import com.unicv.controledepedidos.model.Pedido;
import com.unicv.controledepedidos.model.Produto;
import com.unicv.controledepedidos.services.IPedidoService;
import com.unicv.controledepedidos.services.IProdutoService;
import com.unicv.controledepedidos.services.ProdutoService;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;

/**
 * FXML Controller class
 *
 * @author erik
 */
public class DetalhesPedidoViewController implements Initializable {

    @FXML
    private TableView<ItemProduto> tblItemProduto;

    @FXML
    private TableColumn<ItemProduto, Produto> tcolProduto;

    @FXML
    private TableColumn<ItemProduto, Produto> tcolPrecoUnitario;

    @FXML
    private TableColumn<ItemProduto, Integer> tcolQuantidade;

    @FXML
    private TableColumn<ItemProduto, Float> tcolSubTotal;

    @FXML
    private TextField txtQuantidadeProduto;

    @FXML
    private ComboBox<Produto> cmbProduto;

    @FXML
    private Button btnAdicionar;

    private final List<ItemProduto> oldListaItensProdutos = new ArrayList<>();

    private final List<ItemProduto> toRemoveListaItensProdutos = new ArrayList<>();

    private IProdutoService produtoService;

    private Pedido pedido;

    /**
     * tcolCodigo
     *
     * @param pedido the pedido to set
     */
    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
        tblItemProduto.setItems(pedido.getItensProdutos());
        oldListaItensProdutos.addAll(pedido.getItensProdutos());
    }

    public void handleExcluirButtonAction() {
        if (tblItemProduto.getSelectionModel().isEmpty()) {
            showAlertMessage(Alert.AlertType.ERROR, "Error",
                    "Error excluindo o item do pedido", "Deve selecionar um item");
        } else {
            toRemoveListaItensProdutos.add(tblItemProduto.getSelectionModel().getSelectedItem());
            pedido.removeItens(toRemoveListaItensProdutos);
        }

    }

    public void handleConfirmarButtonAction(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void handleAdicionarButtonAction() {
        ItemProduto novoItemProduto = new ItemProduto();
        pedido.addItem(novoItemProduto);
        tblItemProduto.getSelectionModel().select(novoItemProduto);
        tblItemProduto.scrollTo(novoItemProduto);
        tblItemProduto.setDisable(true);
        btnAdicionar.setDisable(true);
        tblItemProduto.setOpacity(1);
    }

    public void handleAtualizarButtonAction() {
        if (tblItemProduto.getSelectionModel().isEmpty()) {
            showAlertMessage(Alert.AlertType.ERROR, "Error",
                    "Error atualizando o item do pedido", "Deve selecionar um produto");
        } else {
            ItemProduto selectedItem = tblItemProduto.getSelectionModel().getSelectedItem();
            selectedItem.setQuantidade(Integer.parseInt(txtQuantidadeProduto.getText()));
            selectedItem.setProduto(cmbProduto.getValue());
        }

        oldListaItensProdutos.clear();
        oldListaItensProdutos.addAll(pedido.getItensProdutos());
        tblItemProduto.setDisable(false);
        btnAdicionar.setDisable(false);
    }

    public void handleCancelarButtonAction() {
        pedido.getItensProdutos().clear();
        pedido.getItensProdutos().setAll(oldListaItensProdutos);
        tblItemProduto.setDisable(false);
        btnAdicionar.setDisable(false);
        toRemoveListaItensProdutos.clear();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        produtoService = new ProdutoService(new ProdutoDAOJdbc());
        ObservableList<Produto> listaProdutos = FXCollections.observableArrayList();
        try {
            listaProdutos.setAll(produtoService.findAll());
        } catch (ServiceException ex) {
            showAlertMessage(Alert.AlertType.ERROR, "Erro",
                    "Erro carregando os forncedores", ex.getMessage());
        }

        ObservableList<ItemProduto> emptyListaProduto = FXCollections.emptyObservableList();
        tblItemProduto.setItems(emptyListaProduto);
        tblItemProduto.getSelectionModel().selectedItemProperty().addListener((ov, oldValue, newValue) -> {
            if (oldValue != null) {
                txtQuantidadeProduto.setText(null);
                cmbProduto.setValue(null);
            }
            if (newValue != null) {
                txtQuantidadeProduto.setText(Integer.toString(newValue.getQuantidade()));                             
                Optional<Produto> optionalProduto = listaProdutos.stream()
                        .filter(produto -> newValue.getProduto() != null && produto.getId() == newValue.getProduto().getId())
                        .findFirst();
                if (optionalProduto.isPresent()) {
                    cmbProduto.setValue(optionalProduto.get());
                }

            }

        });
        tcolProduto.setCellValueFactory(new PropertyValueFactory<>("produto"));
        tcolPrecoUnitario.setCellValueFactory(new PropertyValueFactory<>("produto"));
        tcolQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        tcolSubTotal.setCellValueFactory(new PropertyValueFactory<>("subTotal"));
        tcolQuantidade.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        tcolSubTotal.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        StringConverter<Produto> produtoStringConverter = new StringConverter<>() {
            @Override
            public String toString(Produto p) {
                String valueToDisplay = null;
                if (p != null) {
                    valueToDisplay = p.getDescricao();
                }
                return valueToDisplay;
            }

            @Override
            public Produto fromString(String string) {
                return null;
            }
        };
        StringConverter<Produto> precoUnitarioStringConverter = new StringConverter<>() {
            @Override
            public String toString(Produto p) {
                String valueToDisplay = null;
                if (p != null) {
                    valueToDisplay = Float.toString(p.getPrecoUnitario());
                }
                return valueToDisplay;
            }

            @Override
            public Produto fromString(String string) {
                return null;
            }
        };
        tcolProduto.setCellFactory(TextFieldTableCell.forTableColumn(produtoStringConverter));
        tcolPrecoUnitario.setCellFactory(TextFieldTableCell.forTableColumn(precoUnitarioStringConverter));
        cmbProduto.setItems(listaProdutos);
        cmbProduto.setConverter(produtoStringConverter);

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
