/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.unicv.controledepedidos.data;

import com.unicv.controledepedidos.exceptions.DaoException;
import com.unicv.controledepedidos.model.Produto;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author erik
 */
public interface ProdutoDAO {

    public void add(Produto produto) throws DaoException;
    
    public void remove(int id)  throws DaoException;
    
    public void update(Produto produto)  throws DaoException;
    
    public Optional<Produto> findById(int id) throws DaoException;
    
    public Optional<Produto> findByCodigo(int codigo) throws DaoException;
    
    public List<Produto> findByDescricao(String descricao) throws DaoException;
    
    public List<Produto> findAll() throws DaoException;

}
