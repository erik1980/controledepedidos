/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.unicv.controledepedidos.services;

import com.unicv.controledepedidos.exceptions.ServiceException;
import com.unicv.controledepedidos.model.Produto;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author erik
 */
public interface IProdutoService extends IService<Produto>{

    public List<Produto> findByDescricao(String descricao) throws ServiceException;
    
    Optional<Produto> findByCodigo(int codigo) throws ServiceException;
    

   
    
}
