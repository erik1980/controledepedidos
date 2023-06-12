/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.unicv.controledepedidos.data;

import com.unicv.controledepedidos.exceptions.DaoException;
import com.unicv.controledepedidos.model.ItemProduto;
import com.unicv.controledepedidos.model.Pedido;
import java.util.List;import java.util.Optional;
import java.util.Optional;

/**
 *
 * @author erik
 */
public interface ItemProdutoDAO {

    public void add(ItemProduto item) throws DaoException;

    public void remove(int id) throws DaoException;

    public void removeAllByPedido(Pedido pedido) throws DaoException;

    public void update(ItemProduto item) throws DaoException;

    public Optional<ItemProduto> findById(int id) throws DaoException;
    
    public Optional<ItemProduto> findByIdPedidoAndIdProduto(int idPedido, int idProduto) throws DaoException;
    
    public List<ItemProduto> findByIdPedido(int idPedido) throws DaoException;

    public List<ItemProduto> findAll() throws DaoException;

    public void removeAll(List<ItemProduto> removeList)throws DaoException;

  


}
