/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.unicv.controledepedidos.data;

import com.unicv.controledepedidos.exceptions.DaoException;
import com.unicv.controledepedidos.model.Produto;
import com.unicv.controledepedidos.utils.JDBCUtil;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * @author erik
 */
public class ProdutoDAOJdbc implements ProdutoDAO {

    @Override
    public void add(Produto produto) throws DaoException {
        String sql = "insert into produtos(codigo_produto, descricao, preco_unitario)"
                + "values(?, ?, ?)";
        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            pstmt.setInt(1, produto.getCodigo());
            pstmt.setString(2, produto.getDescricao());
            pstmt.setFloat(3, produto.getPrecoUnitario());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                produto.setId(id);
            }
            conn.commit();
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public void remove(int id) throws DaoException {
        String sql = "delete from produtos"
                + " where id_produto = ?";
        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            conn.commit();
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public void update(Produto produto) throws DaoException {
        String sql = "update produtos set codigo_produto = ?, descricao = ?, preco_unitario = ? "
                + "where id_produto = ?";
        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, produto.getCodigo());
            pstmt.setString(2, produto.getDescricao());
            pstmt.setFloat(3, produto.getPrecoUnitario());
            pstmt.setInt(4, produto.getId());
            pstmt.executeUpdate();
            conn.commit();
        } catch (SQLException ex){
            throw new DaoException(ex);
        }
    }

    @Override
    public Optional<Produto> findById(int id) throws DaoException {
        String sql = "select * from produtos "
                + "where id_produto = ?";
        Optional<Produto> obtionalProduto = Optional.empty();
        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery();) {
                if (rs.first()) {
                    Produto produto = new Produto();
                    produto.setId(rs.getInt("id_produto"));
                    produto.setCodigo(rs.getInt("codigo_produto"));
                    produto.setDescricao(rs.getString("descricao"));
                    produto.setPrecoUnitario(rs.getFloat("preco_unitario"));
                    obtionalProduto = Optional.of(produto);
                }
                return obtionalProduto;
            }
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public List<Produto> findByDescricao(String descricao) throws DaoException {
        String sql = "select * from produtos "
                + "where descricao like ? and descricao <> ''";
        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1, descricao + "%");
            try (ResultSet rs = pstmt.executeQuery();) {
                List<Produto> listaProdutos = new ArrayList<>();
                while (rs.next()) {
                    Produto produto = new Produto();
                    produto.setCodigo(rs.getInt("id_produto"));
                    produto.setCodigo(rs.getInt("codigo_produto"));
                    produto.setDescricao(rs.getString("descricao"));
                    produto.setPrecoUnitario(rs.getFloat("preco_unitario"));
                    listaProdutos.add(produto);
                }
                return listaProdutos;
            }
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public Optional<Produto> findByCodigo(int codigo) throws DaoException {
        String sql = "select * from produtos "
                + "where codigo_produto = ?";
        Optional<Produto> obtionalProduto = Optional.empty();
        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, codigo);
            try (ResultSet rs = pstmt.executeQuery();) {
                if (rs.next()) {
                    Produto produto = new Produto();
                    produto.setId(rs.getInt("id_produto"));
                    produto.setCodigo(rs.getInt("codigo_produto"));
                    produto.setDescricao(rs.getString("descricao"));
                    produto.setPrecoUnitario(rs.getFloat("preco_unitario"));
                    obtionalProduto = Optional.of(produto);

                }
                return obtionalProduto;
            }
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public List<Produto> findAll() throws DaoException {
        String sql = "select * from produtos";
        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
            try (ResultSet rs = pstmt.executeQuery();) {
                List<Produto> listaProdutos = new ArrayList<>();
                while (rs.next()) {
                    Produto produto = new Produto();
                    produto.setId(rs.getInt("id_produto"));
                    produto.setCodigo(rs.getInt("codigo_produto"));
                    produto.setDescricao(rs.getString("descricao"));
                    produto.setPrecoUnitario(rs.getFloat("preco_unitario"));
                    listaProdutos.add(produto);
                }
                return listaProdutos;
            }
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }

    }

}
