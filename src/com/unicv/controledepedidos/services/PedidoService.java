/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.unicv.controledepedidos.services;

import com.unicv.controledepedidos.data.PedidoDAO;
import com.unicv.controledepedidos.exceptions.DaoException;
import com.unicv.controledepedidos.exceptions.ServiceException;
import com.unicv.controledepedidos.model.Pedido;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author erik
 */
public class PedidoService implements IPedidoService {

    private PedidoDAO pedidoDAO;

    public PedidoService(PedidoDAO pedidoDAO) {
        this.pedidoDAO = pedidoDAO;

    }

    @Override
    public void add(Pedido o) throws ServiceException {
        if (o.getFornecedor() == null) {
            throw new ServiceException("O pedido não tem fornecedor");
        }
        try {
            pedidoDAO.add(o);
        } catch (DaoException ex) {
            throw new ServiceException("Erro ao adicionar o pedido. "
                    + "Revice a coneção à base de dados", ex);
        }
    }

    @Override
    public Optional<Pedido> get(int id) throws ServiceException {
        Optional<Pedido> optionalPedido = Optional.empty();
        try {
            optionalPedido = pedidoDAO.findById(id);
        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
        return optionalPedido;
    }

    @Override
    public void update(Pedido o) throws ServiceException {
        if (o.getFornecedor() == null) {
            throw new ServiceException("O pedido não tem fornecedor");
        }
        try {
            pedidoDAO.update(o);
        } catch (DaoException ex) {

            throw new ServiceException(ex);
        }
    }

    @Override
    public void remove(int id) throws ServiceException {
        try {
            pedidoDAO.remove(id);
        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    public List<Pedido> findAll() throws ServiceException {
        List<Pedido> listaPedidos = new ArrayList<>();
        try {
            listaPedidos.addAll(pedidoDAO.findAll());

        } catch (DaoException ex) {
            throw new ServiceException("Erro ao excluir o pedido. "
                    + "Revice a coneção à base de dados", ex);
        }
        return listaPedidos;
    }

    @Override
    public Optional<Pedido> findByCodigo(int codigo) throws ServiceException {
        Optional<Pedido> optionalPedido = Optional.empty();
        try {
            optionalPedido = pedidoDAO.findByCodigo(codigo);
        } catch (DaoException ex) {
            throw new ServiceException("Erro ao ler os pedidos por código. "
                    + "Revice a conexão à base de dados", ex);
        }
        return optionalPedido;
    }

    @Override
    public List<Pedido> findByCodigoFornecedor(int codigo) throws ServiceException {
        List<Pedido> listaPedidos = new ArrayList<>();
        try {
            listaPedidos.addAll(pedidoDAO.findByCodigoFornecedor(codigo));
        } catch (DaoException ex) {
            throw new ServiceException("Erro ao ler os pedidos por codigo do "
                    + "fornecedor. Revice a conexão à base de dados", ex);
        }
        return listaPedidos;
    }

    @Override
    public List<Pedido> findByNomeFornecedor(String nome) throws ServiceException {
        List<Pedido> listaPedidos = new ArrayList<>();
        try {
            listaPedidos.addAll(pedidoDAO.findByNomeFornecedor(nome));

        } catch (DaoException ex) {
            throw new ServiceException("Erro ao ler os pedidos por nome do "
                    + "fornecedor. Revice a conexão à base de dados", ex);
        }
        return listaPedidos;
    }

    @Override
    public List<Pedido> findByData(LocalDate data) throws ServiceException {
        List<Pedido> listaPedidos = new ArrayList<>();
        try {
            listaPedidos.addAll(pedidoDAO.findByDate(data));
        } catch (DaoException ex) {
            throw new ServiceException("Erro ao ler os pedidos por data. "
                    + "Revice a conexão à base de dados", ex);
        }
        return listaPedidos;

    }

}
