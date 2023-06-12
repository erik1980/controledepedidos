/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.unicv.controledepedidos.data;

import com.unicv.controledepedidos.exceptions.DaoException;
import com.unicv.controledepedidos.model.Fornecedor;
import com.unicv.controledepedidos.model.ItemProduto;
import com.unicv.controledepedidos.model.Pedido;
import com.unicv.controledepedidos.model.Produto;
import com.unicv.controledepedidos.utils.JDBCUtil;
import java.time.LocalDate;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

/**
 *
 * @author erik
 */
public class PedidoDAOJdbc implements PedidoDAO {

    @Override
    public void add(Pedido pedido) throws DaoException {
        String sql = "insert into pedidos(codigo_pedido, data_pedido, fornecedor_id)"
                + "values(?, ?, ?)";
        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            pstmt.setInt(1, pedido.getCodigo());
            pstmt.setDate(2, Date.valueOf(pedido.getData()));
            pstmt.setInt(3, pedido.getFornecedor().getId());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                pedido.setId(id);
            }
            conn.commit();

        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
        ItemProdutoDAO itemProdutoDAO = new ItemProdutoDAOJdbc();
        for (ItemProduto it : pedido.getItensProdutos()) {
            itemProdutoDAO.add(it);
        }

    }

    @Override
    public void remove(int id) throws DaoException {
        String sql = "delete from pedidos where id_pedido = ?";
        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            conn.commit();
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }

    }

    @Override
    public void update(Pedido pedido) throws DaoException {
        String sql = "update pedidos set codigo_pedido = ?, data_pedido = ?, fornecedor_id = ? "
                + "where id_pedido = ?";
        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, pedido.getCodigo());
            pstmt.setDate(2, Date.valueOf(pedido.getData()));
            pstmt.setInt(3, pedido.getFornecedor().getId());
            pstmt.setInt(4, pedido.getId());
            pstmt.executeUpdate();
            conn.commit();
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
        ItemProdutoDAO itemProdutoDAO = new ItemProdutoDAOJdbc();
        List<ItemProduto> oldListaItenProdutos = itemProdutoDAO.findByIdPedido(pedido.getId());
        List<ItemProduto> removeList = new ArrayList<>(oldListaItenProdutos);
        for (ItemProduto newItem : pedido.getItensProdutos()) {
            if (newItem.getId() == 0) {
                itemProdutoDAO.add(newItem);
            }
            for (ItemProduto oldItem : oldListaItenProdutos) {
                if (oldItem.getId() == newItem.getId()) {
                    itemProdutoDAO.update(newItem);
                    removeList.remove(oldItem);
                }
            }
        }
        itemProdutoDAO.removeAll(removeList);

    }

    @Override
    public Optional<Pedido> findById(int id) throws DaoException {
        String sql = "select * from pedidos join fornecedores on "
                + "pedidos.fornecedor_id = fornecedores.id_fornecedor "
                + "where id_pedido = ?";
        Optional<Pedido> obtionalPedido = Optional.empty();
        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Fornecedor fornecedor = new Fornecedor();
                fornecedor.setId(rs.getInt("id_fornecedor"));
                fornecedor.setCodigo(rs.getInt("codigo_fornecedor"));
                fornecedor.setNome(rs.getString("nome_fornecedor"));
                fornecedor.setPais(rs.getString("pais"));
                Pedido pedido = new Pedido();
                pedido.setFornecedor(fornecedor);
                pedido.setId(rs.getInt("id_pedido"));
                pedido.setCodigo(rs.getInt("codigo_pedido"));
                pedido.setData(rs.getDate("data_pedido").toLocalDate());
                ItemProdutoDAO itemProdutoDAO = new ItemProdutoDAOJdbc();
                List<ItemProduto> itensPedidos = itemProdutoDAO.findByIdPedido(id);
                pedido.addItens(itensPedidos);
                obtionalPedido = Optional.of(pedido);
            }

        } catch (SQLException ex) {
            throw new DaoException(ex);
        }

        return obtionalPedido;
    }

    @Override
    public List<Pedido> findAll() throws DaoException {
        String sql = "select * from pedidos join fornecedores on "
                + "pedidos.fornecedor_id = fornecedores.id_fornecedor ";
        List<Pedido> listaPedidos = new ArrayList<>();
        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Fornecedor fornecedor = new Fornecedor();
                fornecedor.setId(rs.getInt("id_fornecedor"));
                fornecedor.setCodigo(rs.getInt("codigo_fornecedor"));
                fornecedor.setNome(rs.getString("nome_fornecedor"));
                fornecedor.setPais(rs.getString("pais"));
                Pedido pedido = new Pedido();
                pedido.setFornecedor(fornecedor);
                pedido.setId(rs.getInt("id_pedido"));
                pedido.setCodigo(rs.getInt("codigo_pedido"));
                pedido.setData(rs.getDate("data_pedido").toLocalDate());
                ItemProdutoDAO itemProdutoDAO = new ItemProdutoDAOJdbc();
                List<ItemProduto> itensPedidos = itemProdutoDAO.findByIdPedido(pedido.getId());
                pedido.addItens(itensPedidos);
                listaPedidos.add(pedido);
            }

        } catch (SQLException ex) {
            throw new DaoException(ex);
        }

        return listaPedidos;
    }

    @Override
    public List<Pedido> findByDate(LocalDate data) throws DaoException {
        String sql = "select * from pedidos join fornecedores on "
                + "pedidos.fornecedor_id = fornecedores.id_fornecedor "
                + "where data_pedido = ?";
        List<Pedido> listaPedidos = new ArrayList<>();
        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setDate(1, Date.valueOf(data));
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Fornecedor fornecedor = new Fornecedor();
                fornecedor.setId(rs.getInt("id_fornecedor"));
                fornecedor.setCodigo(rs.getInt("codigo_fornecedor"));
                fornecedor.setNome(rs.getString("nome_fornecedor"));
                fornecedor.setPais(rs.getString("pais"));
                Pedido pedido = new Pedido();
                pedido.setFornecedor(fornecedor);
                pedido.setId(rs.getInt("id_pedido"));
                pedido.setCodigo(rs.getInt("codigo_pedido"));
                pedido.setData(rs.getDate("data_pedido").toLocalDate());
                ItemProdutoDAO itemProdutoDAO = new ItemProdutoDAOJdbc();
                List<ItemProduto> itensPedidos = itemProdutoDAO.findByIdPedido(pedido.getId());
                pedido.addItens(itensPedidos);
                listaPedidos.add(pedido);
            }

        } catch (SQLException ex) {
            throw new DaoException(ex);
        }

        return listaPedidos;
    }

    @Override
    public List<Pedido> findByFornecedor(int id) throws DaoException {
        String sql = "select * from pedidos join fornecedores on "
                + "pedidos.fornecedor_id = fornecedores.id_fornecedor "
                + "where id_fornecedor = ?";
        List<Pedido> listaPedidos = new ArrayList<>();
        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                //mejorar
                Fornecedor fornecedor = new Fornecedor();
                fornecedor.setId(rs.getInt("id_fornecedor"));
                fornecedor.setCodigo(rs.getInt("codigo_fornecedor"));
                fornecedor.setNome(rs.getString("nome_fornecedor"));
                fornecedor.setPais(rs.getString("pais"));
                Pedido pedido = new Pedido();
                pedido.setFornecedor(fornecedor);
                pedido.setId(rs.getInt("id_pedido"));
                pedido.setCodigo(rs.getInt("codigo_pedido"));
                pedido.setData(rs.getDate("data_pedido").toLocalDate());
                ItemProdutoDAO itemProdutoDAO = new ItemProdutoDAOJdbc();
                List<ItemProduto> itensPedidos = itemProdutoDAO.findByIdPedido(pedido.getId());
                pedido.addItens(itensPedidos);
                listaPedidos.add(pedido);
            }

        } catch (SQLException ex) {
            throw new DaoException(ex);
        }

        return listaPedidos;
    }

}
