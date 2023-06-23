/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.unicv.controledepedidos.controllers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author erik
 */
public class PrincipalViewController implements Initializable {

    @FXML
    private Pane containerPane;

    @FXML
    private ToolBar menu;

    public void handleFornecedoresButtonAction() {
        try {
            AnchorPane childAnchorPane = FXMLLoader.load(getClass().getResource("../views/GerirFornecedoresView.fxml"));
            containerPane.getChildren().clear();
            containerPane.getChildren().add(childAnchorPane);
            containerPane.setPrefHeight(childAnchorPane.getPrefHeight());
            containerPane.setPrefWidth(childAnchorPane.getPrefWidth());
            menu.setPrefHeight(childAnchorPane.getPrefHeight() + 50);
            Stage stage = (Stage) containerPane.getScene().getWindow();
            stage.setHeight(childAnchorPane.getPrefHeight() + 50);            
            stage.setWidth(containerPane.getPrefWidth() + menu.getPrefWidth());
        } catch (IOException ex) {
            Logger.getLogger(PrincipalViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handleProdutosButtonAction() {
        try {
            AnchorPane childAnchorPane = FXMLLoader.load(getClass().getResource("../views/GerirProdutosView.fxml"));
            containerPane.getChildren().clear();
            containerPane.getChildren().add(childAnchorPane);
            containerPane.setPrefHeight(childAnchorPane.getPrefHeight());
            containerPane.setPrefWidth(childAnchorPane.getPrefWidth());
            menu.setPrefHeight(childAnchorPane.getPrefHeight() + 50);
            Stage stage = (Stage) containerPane.getScene().getWindow();
            stage.setHeight(childAnchorPane.getPrefHeight() + 50);
            stage.setWidth(containerPane.getPrefWidth() + menu.getPrefWidth());
        } catch (IOException ex) {
            Logger.getLogger(PrincipalViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handlePedidosButtonAction() {
        try {
            AnchorPane childAnchorPane = FXMLLoader.load(getClass().getResource("../views/GerirPedidosView.fxml"));
            containerPane.getChildren().clear();
            containerPane.getChildren().add(childAnchorPane);
            containerPane.setPrefHeight(childAnchorPane.getPrefHeight());
            containerPane.setPrefWidth(childAnchorPane.getPrefWidth());
            menu.setPrefHeight(childAnchorPane.getPrefHeight() + 50);
            Stage stage = (Stage) containerPane.getScene().getWindow();
            stage.setHeight(childAnchorPane.getPrefHeight() + 50);
            stage.setWidth(containerPane.getPrefWidth() + menu.getPrefWidth());

        } catch (IOException ex) {
            Logger.getLogger(PrincipalViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

}
