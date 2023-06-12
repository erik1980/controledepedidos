/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.unicv.controledepedidos.data;

import com.unicv.controledepedidos.exceptions.DaoException;
import com.unicv.controledepedidos.model.Fornecedor;
import com.unicv.controledepedidos.utils.JDBCUtil;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * @author erik
 */
public class FornecedorDAOJdbc implements FornecedorDAO {

    @Override
    public void add(Fornecedor fornecedor) throws DaoException {
        String sql = "insert into fornecedores(codigo_fornecedor, nome_fornecedor, pais)"
                + "values(?, ?, ?)";
        try (Connection conn = JDBCUtil.getConnection(); 
                PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, fornecedor.getCodigo());
            pstmt.setString(2, fornecedor.getNome());
            pstmt.setString(3, fornecedor.getPais());
            pstmt.executeUpdate();
            conn.commit();
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public void remove(int id) throws DaoException {
        String sql = "delete from fornecedores "
                + "where id_fornecedor = ?";
        try (Connection conn = JDBCUtil.getConnection(); 
                PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            conn.commit();
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public void update(Fornecedor fornecedor) throws DaoException {
        String sql = "update fornecedores set codigo_fornecedor = ?, nome_fornecedor = ?, pais = ? "
                + "where id_fornecedor = ?";
        try (Connection conn = JDBCUtil.getConnection(); 
                PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, fornecedor.getCodigo());
            pstmt.setString(2, fornecedor.getNome());
            pstmt.setString(3, fornecedor.getPais());
            pstmt.setInt(4, fornecedor.getId());            
            pstmt.executeUpdate();
            conn.commit();
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public Optional<Fornecedor> findByCodigo(int codigo) throws DaoException {
        String sql = "select * from fornecedores "
                + "where codigo_fornecedor = ?";
        Optional<Fornecedor> optionalFornecedor = Optional.empty();
        try (Connection conn = JDBCUtil.getConnection(); 
                PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, codigo);
            try (ResultSet rs = pstmt.executeQuery();) {              
                if (rs.next()) {
                    Fornecedor fornecedor = new Fornecedor();
                    fornecedor.setCodigo(rs.getInt("codigo_fornecedor"));
                    fornecedor.setNome(rs.getString("nome_fornecedor"));
                    fornecedor.setPais(rs.getString("pais"));
                    optionalFornecedor = Optional.of(fornecedor);
                }              
                return optionalFornecedor;
            }
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public List<Fornecedor> findByNome(String nome) throws DaoException {
        String sql = "select * from fornecedores "
                + "where nome_fornecedor like ?";
        try (Connection conn = JDBCUtil.getConnection(); 
                PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1, nome + "%");
            try (ResultSet rs = pstmt.executeQuery();) {
                List<Fornecedor> listaFornecedores = new ArrayList<>();
                while (rs.next()) {
                    Fornecedor fornecedor = new Fornecedor();
                    fornecedor.setCodigo(rs.getInt("codigo_fornecedor"));
                    fornecedor.setNome(rs.getString("nome_fornecedor"));
                    fornecedor.setPais(rs.getString("pais"));
                    listaFornecedores.add(fornecedor);
                }
                return listaFornecedores;
            }
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }

    }

    @Override
    public List<Fornecedor> findAll() throws DaoException {
        String sql = "select * from fornecedores";
        try (Connection conn = JDBCUtil.getConnection(); 
                PreparedStatement pstmt = conn.prepareStatement(sql);) {
            try (ResultSet rs = pstmt.executeQuery();) {
                List<Fornecedor> listaFornecedores = new ArrayList<>();
                while (rs.next()) {
                    Fornecedor fornecedor = new Fornecedor();
                    fornecedor.setCodigo(rs.getInt("codigo_fornecedor"));
                    fornecedor.setNome(rs.getString("nome_fornecedor"));
                    fornecedor.setPais(rs.getString("pais"));
                    listaFornecedores.add(fornecedor);
                }
                return listaFornecedores;
            }
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

}
