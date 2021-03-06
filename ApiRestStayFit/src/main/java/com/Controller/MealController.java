/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Controller;

import com.Model.Dish;
import com.Model.Meal;
import com.Model.MealLinkDish;
import com.service.DishService;
import com.service.MealLinkDishService;
import com.service.MealService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
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
 * @author louis
 */

@RestController
public class MealController {
    
    @Autowired
    private MealService mealService;
    
   @Autowired
    private DishService dishService;
   
     @Autowired
    private MealLinkDishService mldService;
     
    @CrossOrigin(origins = "*" )
    @RequestMapping(value = "/meal", method = RequestMethod.GET)
    Iterable<Meal> selectAll() throws Exception {
       return mealService.findAll();
    }
    
    @CrossOrigin(origins = "*")
    @RequestMapping(method = RequestMethod.POST, value = "/meal/create")
    @ResponseBody
    public Object createMeal(@RequestParam(value = "description") String description, @RequestParam(value = "idDietitian") int idDietitian, @RequestParam(value = "isRecommended") int isRecommended, @RequestParam(value = "idGoal") Long idGoal, @RequestParam(value = "idUser") Long idUser, @RequestParam(value = "mealName") String mealName ) {
        Meal m = new Meal( description, idDietitian, isRecommended, idGoal, idUser, mealName);
        try {
            mealService.save(m);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return HttpStatus.NOT_ACCEPTABLE;
        }
        return m.getIdDiner();
    }
    
    
    
    @CrossOrigin(origins = "*" )
    @RequestMapping(method = RequestMethod.POST, value = "/meal/addDish")
    @ResponseBody
    public HttpStatus addDish(@RequestParam(value = "quantity") int quantity, @RequestParam(value = "idDiner")Long idDiner,@RequestParam(value = "idDish") Long idDish) {
        MealLinkDish ple = new MealLinkDish(quantity, idDiner, idDish);
        try {
              mldService.save(ple);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return HttpStatus.NOT_ACCEPTABLE;        
        }
        return HttpStatus.ACCEPTED;
    }
    
    
    
    @CrossOrigin(origins = "*" )
    @RequestMapping(method = RequestMethod.GET, value = "/meal/findmeals/{id}")
     public List<Meal> findMealByGoal(@PathVariable Long id) {
         List<Meal> listMeal = new ArrayList<>();
         List<Long> listId = new ArrayList<>();
         Dish e = new Dish();
         List<Dish> listDish;
         MealLinkDish mld = new MealLinkDish();
       try {
              listMeal.addAll((Collection<? extends Meal>) mealService.findMealByGoal(id));
              for (Meal m : listMeal) {
                  listDish = new ArrayList<>();
                  listId = mldService.findDishByMeal(m.getIdDiner());
                  for (Long i : listId) {
                      e = dishService.findOne(i);
                      mld = mldService.findDataByPMealDish(m.getIdDiner(), i);
                      System.err.println("Calorie :"+e.getCalorie());
                      e.setCalorie(e.getCalorie()*mld.getQuantity()/100);
                      e.setGlucide(e.getGlucide()*mld.getQuantity()/100);
                      e.setLipide(e.getLipide()*mld.getQuantity()/100);
                      e.setProteine(e.getProteine()*mld.getQuantity()/100);
                      e.setMld(mld);
                      listDish.add(e);
                  }
                  m.setListDish(listDish);
              }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return null;
        }
       return listMeal;
     }
     
     
    
}
