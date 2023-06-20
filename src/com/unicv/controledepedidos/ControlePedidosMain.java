/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package com.unicv.controledepedidos;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author erik
 */
public class ControlePedidosMain extends Application {

    @Override
    public void start(Stage primaryStage) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("views/PrincipalView.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("Sistema de Controle de Pedidos");
            primaryStage.setScene(scene);
            primaryStage.setResizable(true);            
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(ControlePedidosMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
