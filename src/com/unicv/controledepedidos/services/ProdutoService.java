/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.unicv.controledepedidos.services;

import com.unicv.controledepedidos.data.ProdutoDAO;
import com.unicv.controledepedidos.exceptions.DaoException;
import com.unicv.controledepedidos.exceptions.ServiceException;
import com.unicv.controledepedidos.model.Produto;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author erik
 */
public class ProdutoService implements IProdutoService {

    private ProdutoDAO produtoDAO;

    public ProdutoService(ProdutoDAO produtoDAO) {
        this.produtoDAO = produtoDAO;
    }

    @Override
    public void add(Produto o) throws ServiceException {
        if (o.getDescricao().isBlank()) {
            throw new ServiceException("O valor da descrição é invalida");
        }
        try {
            produtoDAO.add(o);
        } catch (DaoException ex) {
            throw new ServiceException("Erro ao adicionar o produto. "
                    + "Revice a coneção à base de dados", ex);
        }
    }

    @Override
    public Optional<Produto> get(int id) throws ServiceException {
        Optional<Produto> optionalProduto = Optional.empty();
        try {
            optionalProduto = produtoDAO.findById(id);
        } catch (DaoException ex) {
            throw new ServiceException("Erro ao ler o produto. "
                    + "Revice a coneção à base de dados", ex);
        }
        return optionalProduto;
    }

    @Override
    public void update(Produto o) throws ServiceException {
        if (o.getDescricao().isBlank()) {
            throw new ServiceException("O valor da descrição é invalida");
        }
        try {
            produtoDAO.update(o);
        } catch (DaoException ex) {
            throw new ServiceException("Erro ao atualizar o produto. "
                    + "Revice a coneção à base de dados", ex);
        }
    }

    @Override
    public void remove(int id) throws ServiceException {
        try {
            produtoDAO.remove(id);
        } catch (DaoException ex) {
            throw new ServiceException("Erro ao excluir o produto. "
                    + "Revice a coneção à base de dados", ex);
        }
    }

    @Override
    public List<Produto> findAll() throws ServiceException {
        List<Produto> listaProdutos = new ArrayList<>();
        try {
            listaProdutos.addAll(produtoDAO.findAll());

        } catch (DaoException ex) {
            throw new ServiceException("Erro ao ler os produtos. "
                    + "Revice a coneção à base de dados", ex);
        }
        return listaProdutos;
    }

    @Override
    public Optional<Produto> findByCodigo(int codigo) throws ServiceException {
        Optional<Produto> optionalProduto = Optional.empty();
        try {
            optionalProduto = produtoDAO.findByCodigo(codigo);
        } catch (DaoException ex) {
            throw new ServiceException("Erro ao ler os produtos por código. "
                    + "Revice a conexão à base de dados", ex);
        }
        return optionalProduto;
    }

    @Override
    public List<Produto> findByDescricao(String descricao) throws ServiceException {
        List<Produto> listaProdutos = new ArrayList<>();
        try {
            listaProdutos.addAll(produtoDAO.findByDescricao(descricao));

        } catch (DaoException ex) {
            throw new ServiceException("Erro ao ler os produtos por descrição. "
                    + "Revice a conexão à base de dados", ex);
        }
        return listaProdutos;
    }

}
