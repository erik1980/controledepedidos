/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.unicv.controledepedidos.controllers;

import com.unicv.controledepedidos.data.FornecedorDAOJdbc;
import com.unicv.controledepedidos.exceptions.ServiceException;
import com.unicv.controledepedidos.model.Fornecedor;
import com.unicv.controledepedidos.services.IFornecedorService;
import com.unicv.controledepedidos.services.FornecedorService;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author erik
 */
public class GerirFornecedoresViewController implements Initializable {

    @FXML
    private TableView<Fornecedor> tblFornecedores;

    @FXML
    private TableColumn<Fornecedor, Integer> tcolCodigo;

    @FXML
    private TableColumn<Fornecedor, String> tcolNome;

    @FXML
    private TableColumn<Fornecedor, String> tcolPais;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtPais;

    @FXML
    private Button btnAdicionar;

    @FXML
    private RadioButton rbCodigo;

    @FXML
    private RadioButton rbNome;

    @FXML
    private RadioButton rbPais;

    @FXML
    private RadioButton rbTodos;

    @FXML
    private TextField txtBuscar;

    private ObservableList<Fornecedor> listaFornecedores;

    private final List<Fornecedor> oldListaFornecedores = new ArrayList<>();

    private final List<Fornecedor> toRemoveListaFornecedores = new ArrayList<>();

    private IFornecedorService fornecedorService;

    public void handleRadioButtonAction() {
        txtBuscar.clear();
    }

    public void handleBuscarFornecedorButtonAction() {
        try {
            listaFornecedores.clear();
            if (rbTodos.isSelected()) {
                txtBuscar.clear();
                oldListaFornecedores.clear();
                listaFornecedores.setAll(fornecedorService.findAll());
                oldListaFornecedores.addAll(listaFornecedores);
            } else if (!txtBuscar.getText().isBlank()) {
                if (rbNome.isSelected()) {
                    listaFornecedores.setAll(fornecedorService.findByNome(txtBuscar.getText()));
                } else if (rbPais.isSelected()) {
                    listaFornecedores.setAll(fornecedorService.findByPais(txtBuscar.getText()));
                } else {
                    int codigo = Integer.parseInt(txtBuscar.getText());
                    Optional<Fornecedor> optionalFornecedor = fornecedorService.findByCodigo(codigo);
                    optionalFornecedor.ifPresent((fornecedor) -> {
                        listaFornecedores.add(fornecedor);
                    });
                }
            }
        } catch (NumberFormatException ex) {
            String mssg = "O valor inserido não tem o formato correto";
            showAlertMessage(Alert.AlertType.ERROR, "Error",
                    "Erro buscando um fornecedor", mssg);
        } catch (ServiceException ex) {
            showAlertMessage(Alert.AlertType.ERROR, "Error",
                    "Erro buscando um fornecedor", ex.getMessage());
        }
    }

    public void handleAdicionarButtonAction() {
        Fornecedor novoFornecedor = new Fornecedor();
        listaFornecedores.add(novoFornecedor);
        tblFornecedores.getSelectionModel().select(novoFornecedor);
        tblFornecedores.scrollTo(novoFornecedor);
        tblFornecedores.setDisable(true);
        btnAdicionar.setDisable(true);
        tblFornecedores.setOpacity(1);
    }

    public void handleExcluirButtonAction() {
        if (tblFornecedores.getSelectionModel().isEmpty()) {
            showAlertMessage(Alert.AlertType.ERROR, "Error",
                    "Error excluindo o fornecedor", "Deve selecionar um fornecedor");
        } else {
            toRemoveListaFornecedores.add(tblFornecedores.getSelectionModel().getSelectedItem());
            listaFornecedores.remove(tblFornecedores.getSelectionModel().getSelectedItem());
        }

    }

    public void handleAtualizarButtonAction() {
        if (tblFornecedores.getSelectionModel().isEmpty()) {
            showAlertMessage(Alert.AlertType.ERROR, "Error",
                    "Error atualizando o fornecedor", "Deve selecionar um fornecedor");
        } else {
            try {
                Fornecedor selectedFornecedor = tblFornecedores.getSelectionModel().getSelectedItem();
                selectedFornecedor.setCodigo(Integer.parseInt(txtCodigo.getText()));
                selectedFornecedor.setNome(txtNome.getText());
                selectedFornecedor.setPais(txtPais.getText());
                if (selectedFornecedor.getId() == 0) {
                    fornecedorService.add(selectedFornecedor);
                } else {
                    fornecedorService.update(selectedFornecedor);
                }
                for (Fornecedor fornecedor : toRemoveListaFornecedores) {
                    fornecedorService.remove(fornecedor.getId());
                }
                oldListaFornecedores.clear();
                oldListaFornecedores.addAll(listaFornecedores);
                tblFornecedores.setDisable(false);
                btnAdicionar.setDisable(false);
            } catch (NumberFormatException ex) {
                String mssg = "O valor inserido não tem o formato correto";
                showAlertMessage(Alert.AlertType.ERROR, "Error",
                        "Erro atualozando um fornecedor", mssg);
            } catch (ServiceException ex) {
                showAlertMessage(Alert.AlertType.ERROR, "Error",
                        "Error atualizando o fornecedor", ex.getMessage());
            }

        }
    }

    public void handleCancelarButtonAction() {
        listaFornecedores.clear();
        listaFornecedores.setAll(oldListaFornecedores);
        tblFornecedores.setDisable(false);
        btnAdicionar.setDisable(false);
        toRemoveListaFornecedores.clear();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fornecedorService = new FornecedorService(new FornecedorDAOJdbc());
        listaFornecedores = FXCollections.emptyObservableList();
        try {
            listaFornecedores = FXCollections.observableList(fornecedorService.findAll());
            oldListaFornecedores.addAll(listaFornecedores);
        } catch (ServiceException ex) {
            showAlertMessage(Alert.AlertType.ERROR, "Erro",
                    "Erro carregando os fornecedores", ex.getMessage());
        }
        tblFornecedores.setItems(listaFornecedores);
        tcolCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        tcolNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tcolPais.setCellValueFactory(new PropertyValueFactory<>("pais"));
        tblFornecedores.getSelectionModel().selectedItemProperty().addListener((ov, oldValue, newValue) -> {
            if (oldValue != null) {
                txtCodigo.clear();
                txtNome.clear();
                txtPais.clear();
            }
            if (newValue != null) {
                txtCodigo.setText(Integer.toString(newValue.getCodigo()));
                txtNome.setText(newValue.getNome());
                txtPais.setText(newValue.getPais());
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
