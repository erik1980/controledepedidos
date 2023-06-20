/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.unicv.controledepedidos.services;

import com.unicv.controledepedidos.exceptions.ServiceException;
import com.unicv.controledepedidos.model.Pedido;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author erik
 */
public interface IPedidoService extends IService<Pedido>{
    
    Optional<Pedido> findByCodigo(int codigo) throws ServiceException;
    
    List<Pedido> findByCodigoFornecedor(int codigo) throws ServiceException;
    
    List<Pedido> findByNomeFornecedor(String nome) throws ServiceException;
    
    List<Pedido> findByData(LocalDate data) throws ServiceException;
    
}
