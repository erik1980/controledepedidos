/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.unicv.controledepedidos.data;

import com.unicv.controledepedidos.exceptions.DaoException;
import com.unicv.controledepedidos.model.Fornecedor;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author erik
 */
public interface FornecedorDAO {

    public void add(Fornecedor fornecedor) throws DaoException;

    public void remove(int id) throws DaoException;

    public void update(Fornecedor fornecedor) throws DaoException;
    
    public Optional<Fornecedor>  findById(int id) throws DaoException;

    public Optional<Fornecedor>  findByCodigo(int codigo) throws DaoException;
    
    public List<Fornecedor> findByNome(String nome) throws DaoException;

    public List<Fornecedor> findAll() throws DaoException;        

}
