/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Controller;

import com.Model.Dish;
import com.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Florian
 */
@RestController
public class DishController {
    
    @Autowired
    private DishService dishService;
    
     @CrossOrigin(origins = "*" )
     @RequestMapping(value = "/dish", method = RequestMethod.GET)
    Iterable<Dish> selectAll() throws Exception{
       return dishService.findAll();
    }
    
    
    @CrossOrigin(origins = "*" )
    @RequestMapping(method = RequestMethod.POST, value = "/dish/create")
    @ResponseBody
        public HttpStatus createDish(@RequestParam(value = "name") String name, @RequestParam(value = "description") String description,@RequestParam(value = "dishRecette") String dishRecette,@RequestParam(value = "caloriePercent") int caloriePercent,@RequestParam(value = "proteinePercent") int proteinePercent, @RequestParam(value = "glucidePercent") int glucidePercent,@RequestParam(value = "lipidePercent") int lipidePercent, @RequestParam(value = "picture") String picture) {
        Dish d = new Dish(name, description, dishRecette, caloriePercent, proteinePercent,glucidePercent,lipidePercent, picture);
        try {
            dishService.save(d);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return HttpStatus.NOT_ACCEPTABLE;
                    }
        return HttpStatus.ACCEPTED;
    }
    
    @CrossOrigin(origins = "*" )
   @RequestMapping(method = RequestMethod.DELETE, value = "/dish/delete/{id}")
    public HttpStatus DeleteDish(@PathVariable Long id) {
        try {
            if (dishService.exists(id)) {
                 dishService.delete(id);
            } else {
                return HttpStatus.NOT_ACCEPTABLE;
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return HttpStatus.NOT_ACCEPTABLE;
        }
        return HttpStatus.ACCEPTED;
                
    }
    
    
    
}
