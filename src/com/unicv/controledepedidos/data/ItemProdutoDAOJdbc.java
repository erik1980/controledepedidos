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
import java.util.List;
import java.util.Optional;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author erik
 */
public class ItemProdutoDAOJdbc implements ItemProdutoDAO {

    @Override
    public void add(ItemProduto item) throws DaoException {
        String sql = "insert into itens_produtos(pedido_id, produto_id, quantidade)"
                + "values(?, ?, ?)";
        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, item.getPedido().getId());
            pstmt.setInt(2, item.getProduto().getId());
            pstmt.setInt(3, item.getQuantidade());
            pstmt.executeUpdate();
            conn.commit();
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }

    }

    @Override
    public void remove(int id) throws DaoException {
        String sql = "delete from itens_produtos where id_item = ?";
        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            conn.commit();
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }

    }

    @Override
    public void removeAllByPedido(Pedido pedido) throws DaoException {
        String sql = "delete from itens_produtos where pedido_id = ?";
        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, pedido.getId());
            pstmt.executeUpdate();
            conn.commit();
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public void update(ItemProduto item) throws DaoException {
        String sql = "update itens_produtos set pedido_id = ?, produto_id = ? , quantidade = ? "
                + "where id_item = ?";
        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, item.getPedido().getId());
            pstmt.setInt(2, item.getProduto().getId());
            pstmt.setInt(3, item.getQuantidade());
            pstmt.setInt(4, item.getId());
            pstmt.executeUpdate();
            conn.commit();
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public Optional<ItemProduto> findById(int id) throws DaoException {

        return null;
    }

    @Override
    public Optional<ItemProduto> findByIdPedidoAndIdProduto(int idPedido, int idProduto) throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<ItemProduto> findByIdPedido(int idPedido) throws DaoException {
        String sql = "select * from itens_produtos "
                + "join pedidos on itens_produtos.pedido_id = pedidos.id_pedido "
                + "join produtos on itens_produtos.produto_id = produtos.id_produto "
                + "join fornecedores on fornecedores.id_fornecedor = pedidos.fornecedor_id "
                + "where itens_produtos.pedido_id  = ?";
        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, idPedido);
            try (ResultSet rs = pstmt.executeQuery();) {
                List<ItemProduto> listaItens = new ArrayList<>();
                Map<Integer, Pedido> studentMap = new HashMap<>();
                while (rs.next()) {
                    //Extrair os dados do fornecedor                    
                    Pedido pedido = studentMap.get(idPedido);
                    if (studentMap.isEmpty()) {
                        Fornecedor fornecedor = new Fornecedor();
                        fornecedor.setId(rs.getInt("id_fornecedor"));
                        fornecedor.setCodigo(rs.getInt("codigo_fornecedor"));
                        fornecedor.setNome(rs.getString("nome_fornecedor"));
                        fornecedor.setPais(rs.getString("pais"));
                        //Extrair os dados do pedido 
                        pedido = new Pedido();
                        pedido.setId(rs.getInt("id_pedido"));
                        pedido.setCodigo(rs.getInt("codigo_pedido"));
                        pedido.setData(rs.getDate("data_pedido").toLocalDate());
                        pedido.setFornecedor(fornecedor);
                        studentMap.put(idPedido, pedido);
                    }
                    //Extrair os dados do produto
                    Produto produto = new Produto();
                    produto.setId(rs.getInt("id_produto"));
                    produto.setCodigo(rs.getInt("codigo_produto"));
                    produto.setDescricao(rs.getString("descricao"));
                    produto.setPrecoUnitario(rs.getFloat("preco_unitario"));
                    //Extrair os dados do Item
                    ItemProduto item = new ItemProduto();
                    item.setId(rs.getInt("id_item"));
                    item.setQuantidade(rs.getInt("quantidade"));
                    item.setProduto(produto);
                    pedido.addItem(item);
                    //item.setPedido(pedido);
                    listaItens.add(item);
                }
                return listaItens;
            }
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public List<ItemProduto> findAll() throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void removeAll(List<ItemProduto> removeList) throws DaoException {
        for (ItemProduto itemProduto : removeList) {
            remove(itemProduto.getId());
        }
    }

}
