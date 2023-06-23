/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.unicv.controledepedidos.controllers;

import com.unicv.controledepedidos.data.FornecedorDAOJdbc;
import com.unicv.controledepedidos.data.PedidoDAOJdbc;
import com.unicv.controledepedidos.exceptions.ServiceException;
import com.unicv.controledepedidos.model.Fornecedor;
import com.unicv.controledepedidos.model.Pedido;
import com.unicv.controledepedidos.services.FornecedorService;
import com.unicv.controledepedidos.services.IFornecedorService;
import com.unicv.controledepedidos.services.IPedidoService;
import com.unicv.controledepedidos.services.PedidoService;
import com.unicv.controledepedidos.services.ServiceManager;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LocalDateStringConverter;

/**
 * FXML Controller class
 *
 * @author erik
 */
public class GerirPedidosViewController implements Initializable {

    @FXML
    private RadioButton rbCodigo;

    @FXML
    private RadioButton rbData;

    @FXML
    private RadioButton rbTodos;

    @FXML
    private TextField txtBuscar;

    @FXML
    private DatePicker dtpBuscar;

    @FXML
    private TableView<Pedido> tblPedidos;

    @FXML
    private TableColumn<Pedido, Integer> tcolCodigo;

    @FXML
    private TableColumn<Pedido, LocalDate> tcolData;

    @FXML
    private TableColumn<Pedido, Fornecedor> tcolNomeFornecedor;

    @FXML
    private TableColumn<Pedido, Float> tcolTotal;

    @FXML
    private TableColumn<Pedido, Void> tcolDetalhes;

    @FXML
    private ComboBox<Fornecedor> cmbFornecedores;

    @FXML
    private DatePicker dtpData;

    @FXML
    private TextField txtCodigo;

    @FXML
    private Button btnAdicionar;

    private ObservableList<Pedido> listaPedidos;

    private ObservableList<Fornecedor> listaFornecedores;

    private final List<Pedido> oldListaPedidos = new ArrayList<>();

    private final List<Pedido> toRemoveListaPedidos = new ArrayList<>();

    private IPedidoService pedidoService;

    private IFornecedorService fornecedorService;

    public void handleRadioButtonAction() {
        txtBuscar.clear();
    }

    public void handleBuscarPedidoButtonAction() {
        try {
            listaPedidos.clear();
            if (rbTodos.isSelected()) {
                txtBuscar.clear();
                oldListaPedidos.clear();
                dtpBuscar.setValue(null);
                listaPedidos.setAll(pedidoService.findAll());
                oldListaPedidos.addAll(listaPedidos);

            } else if (rbData.isSelected()) {
                LocalDate data = dtpBuscar.getValue();
                List<Pedido> newListaPedidos = pedidoService.findByData(data);
                listaPedidos.setAll(newListaPedidos);
            } else if (!txtBuscar.getText().isBlank()) {
                if (rbCodigo.isSelected()) {
                    int codigo = Integer.parseInt(txtBuscar.getText());
                    Optional<Pedido> optionalPedido = pedidoService.findByCodigo(codigo);
                    optionalPedido.ifPresent((pedido) -> {
                        listaPedidos.add(pedido);
                    });
                } else {
                    List<Pedido> newListaPedidos = pedidoService.findByNomeFornecedor(txtBuscar.getText());
                    listaPedidos.setAll(newListaPedidos);
                }
            }
        } catch (NumberFormatException ex) {
            String mssg = "O valor inserido não tem o formato correto";
            showAlertMessage(Alert.AlertType.ERROR, "Error",
                    "Erro buscando um pedido", mssg);
        } catch (ServiceException ex) {
            showAlertMessage(Alert.AlertType.ERROR, "Error",
                    "Erro buscando um pedido", ex.getMessage());
        }
    }

    public void handleAdicionarButtonAction() {
        Pedido novoPedido = new Pedido();
        listaPedidos.add(novoPedido);
        tblPedidos.getSelectionModel().select(novoPedido);
        tblPedidos.scrollTo(novoPedido);
        tblPedidos.setDisable(true);
        tblPedidos.setDisable(true);
        tblPedidos.setOpacity(1);
    }

    public void handleExcluirButtonAction() {
        if (tblPedidos.getSelectionModel().isEmpty()) {
            showAlertMessage(Alert.AlertType.ERROR, "Error",
                    "Error excluindo o produto", "Deve selecionar um produto");
        } else {
            toRemoveListaPedidos.add(tblPedidos.getSelectionModel().getSelectedItem());
            listaPedidos.remove(tblPedidos.getSelectionModel().getSelectedItem());
        }

    }

    public void handleAtualizarButtonAction() {
        if (tblPedidos.getSelectionModel().isEmpty()) {
            showAlertMessage(Alert.AlertType.ERROR, "Error",
                    "Error atualizando o pedido", "Deve selecionar um pedido");
        } else {
            try {
                Pedido selectdoPedido = tblPedidos.getSelectionModel().getSelectedItem();
                selectdoPedido.setCodigo(Integer.parseInt(txtCodigo.getText()));
                selectdoPedido.setData(dtpData.getValue());
                selectdoPedido.setFornecedor(cmbFornecedores.getValue());
                if (selectdoPedido.getId() == 0) {
                    pedidoService.add(selectdoPedido);

                } else {
                    pedidoService.update(selectdoPedido);
                }
                for (Pedido pedido : toRemoveListaPedidos) {
                    pedidoService.remove(pedido.getId());
                }
                oldListaPedidos.clear();
                oldListaPedidos.addAll(listaPedidos);
                tblPedidos.setDisable(false);
                btnAdicionar.setDisable(false);
            } catch (NumberFormatException ex) {
                String mssg = "O valor inserido não tem o formato correto";
                showAlertMessage(Alert.AlertType.ERROR, "Error",
                        "Erro atualizando um pedido", mssg);
            } catch (ServiceException ex) {
                showAlertMessage(Alert.AlertType.ERROR, "Error",
                        "Error atualizando o fornecedor", ex.getMessage());
            }

        }

    }

    public void handleCancelarButtonAction() {
        listaPedidos.clear();
        listaPedidos.setAll(oldListaPedidos);
        tblPedidos.setDisable(false);
        btnAdicionar.setDisable(false);
        toRemoveListaPedidos.clear();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pedidoService = ServiceManager.getServiceManager().getPedidoService();
        fornecedorService = ServiceManager.getServiceManager().getFornecedorService();
        listaPedidos = FXCollections.emptyObservableList();
        listaFornecedores = FXCollections.emptyObservableList();
        try {
            listaFornecedores = FXCollections.observableArrayList();
            listaFornecedores.setAll(fornecedorService.findAll());
        } catch (ServiceException ex) {
            showAlertMessage(Alert.AlertType.ERROR, "Erro",
                    "Erro carregando os forncedores", ex.getMessage());
        }
        try {
            listaPedidos = FXCollections.observableArrayList();
            listaPedidos.setAll(pedidoService.findAll());
            oldListaPedidos.addAll(listaPedidos);
        } catch (ServiceException ex) {
            showAlertMessage(Alert.AlertType.ERROR, "Erro",
                    "Erro carregando os pedidos", ex.getMessage());
        }
        tblPedidos.setItems(listaPedidos);
        tcolCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        tcolData.setCellValueFactory(new PropertyValueFactory<>("data"));
        tcolNomeFornecedor.setCellValueFactory(new PropertyValueFactory<>("fornecedor"));
        tcolTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        tcolCodigo.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        tcolTotal.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        tcolData.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
        StringConverter<Fornecedor> fornecedorStringConverter = new StringConverter<>() {
            @Override
            public String toString(Fornecedor t) {
                String valueToDisplay = null;
                if (t != null) {
                    valueToDisplay = t.getNome();
                }
                return valueToDisplay;
            }

            @Override
            public Fornecedor fromString(String string) {
                return null;
            }
        };
        tcolNomeFornecedor.setCellFactory(TextFieldTableCell.forTableColumn(fornecedorStringConverter));
        //Criar cell com Button para ver os itens de cada pedido na TableView      
        tcolDetalhes.setCellFactory((TableColumn<Pedido, Void> param) -> {
            final TableCell<Pedido, Void> cell = new TableCell<Pedido, Void>() {
                private final Button btn = new Button("Dethales");

                {
                    btn.setOnAction((ActionEvent event) -> {
                        try {
                            Pedido selectedPedido = getTableView().getItems().get(getIndex());
                            tblPedidos.getSelectionModel().select(selectedPedido);
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/DetalhesPedidoView.fxml"));
                            Parent root = loader.load();
                            DetalhesPedidoViewController controller = loader.getController();
                            controller.setPedido(selectedPedido);
                            Scene scene = new Scene(root);
                            Stage stage = new Stage();
                            stage.setScene(scene);
                            stage.show();
                        } catch (IOException ex) {
                            showAlertMessage(Alert.AlertType.ERROR, "Erro",
                                    "Erro carregando os itens", ex.getMessage());
                        }

                    });
                }

                @Override
                public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(btn);
                    }
                }
            };
            return cell;
        });

        tblPedidos.getSelectionModel().selectedItemProperty().addListener((ov, oldValue, newValue) -> {
            if (oldValue != null) {
                txtCodigo.clear();
                dtpBuscar.setValue(null);
                cmbFornecedores.setValue(null);
            }
            if (newValue != null) {
                txtCodigo.setText(Integer.toString(newValue.getCodigo()));
                dtpData.setValue(newValue.getData());
                Optional<Fornecedor> optionalFornecedor = listaFornecedores.stream()
                        .filter(fornecedor -> newValue.getFornecedor() != null && fornecedor.getId() == newValue.getFornecedor().getId())
                        .findFirst();

                if (optionalFornecedor.isPresent()) {
                    cmbFornecedores.setValue(optionalFornecedor.get());
                }

                //nós não podemos fazer isso
             //   cmbFornecedores.setValue(newValue.getFornecedor());
            }
        });
        cmbFornecedores.getItems().setAll(listaFornecedores);
        cmbFornecedores.setConverter(fornecedorStringConverter);
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
