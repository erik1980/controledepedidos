package com.unicv.controledepedidos.services;

import com.unicv.controledepedidos.data.FornecedorDAOJdbc;
import com.unicv.controledepedidos.data.PedidoDAOJdbc;
import com.unicv.controledepedidos.data.ProdutoDAOJdbc;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author erik
 */
public class ServiceManager {

    private static ServiceManager manager;
    private IProdutoService produtoService;
    private IPedidoService pedidoService;
    private IFornecedorService fornecedorService;

    private ServiceManager() {
        produtoService = new ProdutoService(new ProdutoDAOJdbc());
        pedidoService = new PedidoService(new PedidoDAOJdbc());
        fornecedorService = new FornecedorService(new FornecedorDAOJdbc());
    }

    public static ServiceManager getServiceManager() {
        if (manager == null) {
            manager = new ServiceManager();
        }
        return manager;

    } 
 
    public IProdutoService getProdutoService() {
        return produtoService;
    }

    public IPedidoService getPedidoService() {
        return pedidoService;
    }

    public IFornecedorService getFornecedorService() {
        return fornecedorService;
    }
    
   

}
