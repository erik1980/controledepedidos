/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.unicv.controledepedidos.services;

import com.unicv.controledepedidos.data.FornecedorDAO;
import com.unicv.controledepedidos.exceptions.DaoException;
import com.unicv.controledepedidos.exceptions.ServiceException;
import com.unicv.controledepedidos.model.Fornecedor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author erik
 */
public class FornecedorService implements IFornecedorService {

    private FornecedorDAO fornecedorDAO;

    public FornecedorService(FornecedorDAO fornecedorDAO) {
        this.fornecedorDAO = fornecedorDAO;
    }

    @Override
    public void add(Fornecedor o) throws ServiceException {
        if (o.getNome().isBlank()) {
            throw new ServiceException("O nome do fornecedor é invalido");
        }
        try {
            fornecedorDAO.add(o);
        } catch (DaoException ex) {
            throw new ServiceException("Erro ao adicionar o fornecedor. "
                    + "Revice a coneção à base de dados", ex);
        }
    }

    @Override
    public Optional<Fornecedor> get(int id) throws ServiceException {
        Optional<Fornecedor> optionalFornecedor = Optional.empty();
        try {
            optionalFornecedor = fornecedorDAO.findById(id);
        } catch (DaoException ex) {
            throw new ServiceException("Erro ao ler o fornecedor. "
                    + "Revice a coneção à base de dados", ex);
        }
        return optionalFornecedor;
    }

    @Override
    public void update(Fornecedor o) throws ServiceException {
        if (o.getNome().isBlank()) {
            throw new ServiceException("O nome do fornecedor é invalido");
        }
        try {
            fornecedorDAO.update(o);
        } catch (DaoException ex) {
            throw new ServiceException("Erro ao atualizar o fornecedor. "
                    + "Revice a coneção à base de dados", ex);
        }
    }

    @Override
    public void remove(int id) throws ServiceException {
        try {
            fornecedorDAO.remove(id);
        } catch (DaoException ex) {
            throw new ServiceException("Erro ao excluir o fornecedor. "
                    + "Revice a coneção à base de dados", ex);
        }
    }

    @Override
    public List<Fornecedor> findAll() throws ServiceException {
        List<Fornecedor> listaFornecedores = new ArrayList<>();
        try {
            listaFornecedores.addAll(fornecedorDAO.findAll());

        } catch (DaoException ex) {
            throw new ServiceException("Erro ao ler os fornecedores. "
                    + "Revice a coneção à base de dados", ex);
        }
        return listaFornecedores;
    }

    @Override
    public List<Fornecedor> findByNome(String nome) throws ServiceException {
        List<Fornecedor> listaFornecedores = new ArrayList<>();
        try {
            listaFornecedores.addAll(fornecedorDAO.findByNome(nome));
        } catch (DaoException ex) {
            throw new ServiceException("Erro ao ler os fornecedores por nome. "
                    + "Revice a conexão à base de dados", ex);
        }
        return listaFornecedores;
    }

    @Override
    public Optional<Fornecedor> findByCodigo(int codigo) throws ServiceException {
        Optional<Fornecedor> optionalProduto = Optional.empty();
        try {
            optionalProduto = fornecedorDAO.findByCodigo(codigo);
        } catch (DaoException ex) {
            throw new ServiceException("Erro ao ler os fornecedores por código. "
                    + "Revice a conexão à base de dados", ex);
        }
        return optionalProduto;
    }

    @Override
    public List<Fornecedor> findByPais(String pais) throws ServiceException {
        List<Fornecedor> listaFornecedores = new ArrayList<>();
        try {
            listaFornecedores.addAll(fornecedorDAO.findByNome(pais));
        } catch (DaoException ex) {
            throw new ServiceException("Erro ao ler os fornecedores por pais. "
                    + "Revice a conexão à base de dados", ex);
        }
        return listaFornecedores;
    }

}
