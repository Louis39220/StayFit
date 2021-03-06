/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service;

import com.Model.Dish;

/**
 *
 * @author Florian
 */
public interface DishService {
    
        public  Iterable<Dish> findAll();
        
        public Dish findOne(Long id);
        
        public boolean exists(Long id);
        
        public void delete(Long id);
        
        public void delete(Dish d);
        
        public void save(Dish d);

}