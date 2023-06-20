/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.unicv.controledepedidos.services;

import com.unicv.controledepedidos.exceptions.ServiceException;
import com.unicv.controledepedidos.model.Fornecedor;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author erik
 */
public interface IFornecedorService extends IService<Fornecedor>{    

    public List<Fornecedor> findByNome(String nome) throws ServiceException ;
    
    public List<Fornecedor> findByPais(String pais) throws ServiceException ;

    public Optional<Fornecedor> findByCodigo(int codigo) throws ServiceException ;
    
}
