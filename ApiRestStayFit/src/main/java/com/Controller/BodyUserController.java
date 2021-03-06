/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Controller;

import com.Model.BodyUser;
import com.service.BodyUserService;
import java.util.Iterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import com.Model.User;
import com.Model.UserConnect;
import com.service.BodyUserService;
import com.service.UserConnectService;
import com.service.UserService;
import java.text.ParseException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
<<<<<<< HEAD
 * @author louis
 */
@RestController
@ControllerAdvice
public class BodyUserController {
    
        @Autowired
    private UserConnectService userConnectService;
    @Autowired
    private BodyUserService bodyUserService;
    
        @CrossOrigin(origins = "*" )
        @RequestMapping(value = "/bodyusers", method = RequestMethod.GET)
    Iterable<BodyUser> selectAll() throws Exception{
       return bodyUserService.findAll();
    }
    
    @CrossOrigin(origins = "*" )
    @RequestMapping(method = RequestMethod.GET, value = "/bodyuser/find/{id}")
     public Iterable<BodyUser> findBodyUserByUser(@PathVariable Long id) {
       try {
            if (bodyUserService.exists(id)) {
                 return bodyUserService.findBodyUserByUser(id);
            } else {
                  return null;
            }
        } catch (Exception e) {
            System.err.println(e.getStackTrace());
            return null;
        }
     }

    @CrossOrigin(origins = "*" )
    @RequestMapping(method = RequestMethod.POST, value = "/bodyUser/create")
    @ResponseBody
    public HttpStatus createUser(@RequestParam(value = "idUser") Long id, @RequestParam(value = "weight") int weight, @RequestParam(value = "gracemass") int gracemass,HttpServletResponse response) throws ParseException {
        BodyUser bodyUser = new BodyUser(weight,gracemass, true, id);
        try {
            bodyUserService.save(bodyUser);
        } catch (Exception e) {
            System.err.println(e.getStackTrace());
            return HttpStatus.NOT_ACCEPTABLE;
        }
        return HttpStatus.ACCEPTED;
    }
    
    @CrossOrigin(origins = "*" )
    @RequestMapping(method = RequestMethod.POST, value = "/bodyUser/insert")
    @ResponseBody
    public HttpStatus insertUser(@RequestParam(value = "idUser") Long id, @RequestParam(value = "weight") int weight, @RequestParam(value = "gracemass") int gracemass,HttpServletResponse response) throws ParseException {
        BodyUser bodyUser = new BodyUser(weight,gracemass, true, id);
        try {
            bodyUserService.saveAndUpdate(bodyUser);
        } catch (Exception e) {
            System.err.println(e.getStackTrace());
            return HttpStatus.NOT_ACCEPTABLE;
        }
        return HttpStatus.ACCEPTED;
    }
    
    @CrossOrigin(origins = "*" )
    @RequestMapping(method = RequestMethod.GET, value = "/bodyUser/find/{id}")
     public Iterable<BodyUser> findBodyUserById(@PathVariable Long id) {
        return bodyUserService.findBodyUserByUser(id);
       /*
       Iterable<BodyUser> bodyUsers = new Iterable<BodyUser>();
       try {
            if (userConnectService.exists(id)) {
                 return bodyUserService.findBodyUserByUser(id);
            } else {
                  return null;
            }
        } catch (Exception e) {
            System.err.println(e.getStackTrace());
            return null;
        }
        return bodyUsers; 
*/
    }
     
    @CrossOrigin(origins = "*" )
    @RequestMapping(method = RequestMethod.GET, value = "/bodyUser/findLast/{id}")
     public BodyUser findByIdUserAndIsLast(@PathVariable Long id) {
        return bodyUserService.findByIdUserAndIsLast(id);
       /*
       Iterable<BodyUser> bodyUsers = new Iterable<BodyUser>();
       try {
            if (userConnectService.exists(id)) {
                 return bodyUserService.findBodyUserByUser(id);
            } else {
                  return null;
            }
        } catch (Exception e) {
            System.err.println(e.getStackTrace());
            return null;
        }
                return bodyUsers; */
     } 
    
         @CrossOrigin(origins = "*" )
    @RequestMapping(method = RequestMethod.POST, value = "/bodyuser/create")
    @ResponseBody
    public HttpStatus createBodyUser(@RequestParam(value = "weight") int weight, @RequestParam(value = "gracemasse") int gracemasse, @RequestParam(value = "isLast") boolean isLast, @RequestParam(value = "idUser") Long idUser) {
        BodyUser u = new BodyUser(weight, gracemasse,isLast, idUser);
        try {
            bodyUserService.save(u);
        } catch (Exception e) {
            System.err.println(e.getStackTrace());
            return HttpStatus.METHOD_FAILURE;
        }
        return HttpStatus.ACCEPTED;
    }
    
    @CrossOrigin(origins = "*" )
   @RequestMapping(method = RequestMethod.DELETE, value = "/bodyUser/delete/{id}")
    public HttpStatus DeleteUser(@PathVariable Long id) {
        try {
              if (bodyUserService.exists(id)) {
                 bodyUserService.delete(id);
             } else {
                 return HttpStatus.NOT_ACCEPTABLE;
            }
        } catch (Exception e) {
            System.err.println(e.getStackTrace());
            return HttpStatus.NOT_ACCEPTABLE;
        }
        return HttpStatus.ACCEPTED;
                
    }
    
}
