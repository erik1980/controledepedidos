/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.unicv.controledepedidos.services;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author erik
 */
public class ModelStateDictionary implements IValidationDictionary {
    
    private final Map<String, String> modelState = new HashMap<>();

    @Override
    public void addError(String key, String errorMessage) {
        modelState.put(key, errorMessage);        
    }

    @Override
    public boolean isValid() {
        return modelState.isEmpty();
    }
    
    
}
