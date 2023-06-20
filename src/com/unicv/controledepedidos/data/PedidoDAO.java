/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.unicv.controledepedidos.data;

import com.unicv.controledepedidos.exceptions.DaoException;
import com.unicv.controledepedidos.model.Pedido;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author erik
 */
public interface PedidoDAO {

    public void add(Pedido ped) throws DaoException;

    public void remove(int id) throws DaoException;

    public void update(Pedido ped) throws DaoException;

    public Optional<Pedido> findById(int id) throws DaoException;
    
    public Optional<Pedido> findByCodigo(int codigo) throws DaoException;

    public List<Pedido> findAll() throws DaoException;

    public List<Pedido> findByDate(LocalDate date) throws DaoException;

    public List<Pedido> findByIdFornecedor(int id) throws DaoException;

    public List<Pedido> findByCodigoFornecedor(int codigo) throws DaoException;

    public List<Pedido> findByNomeFornecedor(String nome) throws DaoException;

}
