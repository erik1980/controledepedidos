/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.unicv.controledepedidos;

import com.unicv.controledepedidos.data.FornecedorDAOJdbc;
import com.unicv.controledepedidos.data.ItemProdutoDAOJdbc;
import com.unicv.controledepedidos.data.PedidoDAO;
import com.unicv.controledepedidos.data.PedidoDAOJdbc;
import com.unicv.controledepedidos.data.ProdutoDAOJdbc;
import com.unicv.controledepedidos.exceptions.DaoException;
import com.unicv.controledepedidos.model.Fornecedor;
import com.unicv.controledepedidos.model.ItemProduto;
import com.unicv.controledepedidos.model.Pedido;
import com.unicv.controledepedidos.model.Produto;
import java.sql.*;
import com.unicv.controledepedidos.utils.JDBCUtil;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author erik
 */
public class Controledepedidos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        try {
//            FornecedorDAOJdbc fornecedorDAO = new FornecedorDAOJdbc();
//            Fornecedor forn1 = new Fornecedor(82, "Bosch", "Alemnaha");
//            Fornecedor forn2 = new Fornecedor(23, "Makita", "Japão");
//            Fornecedor forn3 = new Fornecedor(43, "Dewalt", "USA");
//            Fornecedor forn4 = new Fornecedor(86, "AEG", "Alemanha");
//            Fornecedor forn5 = new Fornecedor(26, "Black & Decker", "USA");
//            Fornecedor forn6 = new Fornecedor(56, "Vito", "Portugal");
//            Fornecedor forn7 = new Fornecedor(75, "Stanley", "USA");
//            Fornecedor forn8 = new Fornecedor(19, "Milwaukee", "USA");
//            Fornecedor forn9 = new Fornecedor(64, "Dremel", "Alemanho");
//            Fornecedor forn10 = new Fornecedor(15, "Itools", "Portulal");
//
//            fornecedorDAO.add(forn1);
//            fornecedorDAO.add(forn2);
//            fornecedorDAO.add(forn3);
//            fornecedorDAO.add(forn4);
//            fornecedorDAO.add(forn5);
//            fornecedorDAO.add(forn6);
//            fornecedorDAO.add(forn7);
//            fornecedorDAO.add(forn8);
//            fornecedorDAO.add(forn9);
//            fornecedorDAO.add(forn10);
//      

//            fornecedorDAO.remove(82);
//            fornecedorDAO.remove(56);
//            fornecedorDAO.remove(15);
//            fornecedorDAO.update(new Fornecedor(86, "Itools", "Portulal"));
//            System.out.println("-------------------------------------------------------------");
//            Optional<Fornecedor> opt = fornecedorDAO.findByCodigo(43);
//            if (opt.isPresent()) {
//                Fornecedor forn = opt.get();
//                System.out.printf("Codigo: %d\nNome: %s\nPaia:%s\n", forn.getCodigo(), forn.getNome(), forn.getPais());
//            }
//            System.out.println("-------------------------------------------------------------");
//            List<Fornecedor> listFornecedores = fornecedorDAO.findAll();
//            listFornecedores.forEach((it) -> {
//                System.out.printf("Codigo: %d\nNome: %s\nPaia:%s\n", it.getCodigo(), it.getNome(), it.getPais());
//                System.out.println();
//            });
//            System.out.println("-------------------------------------------------------------");
//            List<Fornecedor> listFornecedoresByNome = fornecedorDAO.findByNome("M");
//            listFornecedoresByNome.forEach((it) -> {
//                System.out.printf("Codigo: %d\nNome: %s\nPaia:%s\n", it.getCodigo(), it.getNome(), it.getPais());
//                System.out.println();
//            });
//            System.out.println("-------------------------------------------------------------");
//            ItemProdutoDAOJdbc itemProdutoDao = new ItemProdutoDAOJdbc();
//            List<ItemProduto> listItens = itemProdutoDao.findByIdPedido(12);
//            listItens.forEach((it)
//                    -> System.out.printf("%s\t%s\t\n", it.getPedido().getFornecedor().getNome(), it.getProduto().getDescricao())
//            );
//            Fornecedor forn = new Fornecedor(75, "Stanley", "USA");
//            forn.setId(22);
//            Pedido pedido = new Pedido(241, LocalDate.now(), forn);
//            //pedido.setId(28);
//            Produto produto1 = new Produto(843, "Agrafadores", 8000);
//            Produto produto2 = new Produto(633, "Lixadeira", 8000);
//            produto1.setId(4);
//            produto2.setId(3);
//            ItemProduto item1 = new ItemProduto(pedido, produto1, 498);
//          //  item1.setId(40);
//            ItemProduto item2 = new ItemProduto(pedido, produto2, 333);
//           // item2.setId(46);
//            pedido.addItem(item1);
//            pedido.addItem(item2);
//            PedidoDAO pedidoDAO = new PedidoDAOJdbc();
//            pedidoDAO.add(pedido);           
//           // pedidoDAO.update(pedido);
//         DAO.   Pedido ped = pedidoDAO.findById(28).get();
//            pedidoDAO.findAll();
//            pedidoDAO.findByFornecedor(18);
//
//        } catch (DaoException ex) {
//            Logger.getLogger(Controledepedidos.class.getName()).log(Level.SEVERE, null, ex);
//        }
ProdutoDAOJdbc p = new ProdutoDAOJdbc();
        try {
            p.remove(0);
        } catch (DaoException ex) {
            Logger.getLogger(Controledepedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
