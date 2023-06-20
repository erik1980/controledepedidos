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
 * @param <T>
 */
public interface IService<T>{

    void add(T o)throws ServiceException;   

    Optional<T> get(int id)throws ServiceException;

    void update(T o)throws ServiceException;      

    void remove(int id)throws ServiceException; 
    
    List<T> findAll() throws ServiceException;    
   
}
