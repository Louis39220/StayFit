/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Controller;

import com.Model.BodyUser;
import com.Model.User;
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
 * @author louis
 */
@RestController
@ControllerAdvice
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserConnectService userConnectService;
    
    @Autowired
    private BodyUserService bodyUserService;
   
    @CrossOrigin(origins = "*" )
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    Iterable<User> selectAll() throws Exception{
       return userService.findAll();
    }

    @CrossOrigin(origins = "*" )
    @RequestMapping(method = RequestMethod.POST, value = "/user/create")
    @ResponseBody
    public HttpStatus createUser(@RequestParam(value = "id") Long id, @RequestParam(value = "firstname") String firstname, @RequestParam(value = "lastname") String lastname,@RequestParam(value = "birthday") String birthday,  @RequestParam(value = "sexe") String sexe, @RequestParam(value = "size") int size, @RequestParam(value = "weight") int weight,@RequestParam(value = "picture",required = false) String picture) throws ParseException {
        String[] birthdayArray = birthday.split("/");
        String birthdaySimpleFormat = birthdayArray[2]+"-"+birthdayArray[1]+"-"+birthdayArray[0];
        User u = new User(id,firstname, lastname, birthdaySimpleFormat, sexe, size, picture);
        try {
            userService.save(u);
            if (weight != 0) {
                BodyUser body = new BodyUser(weight, true, id);
                bodyUserService.save(body);
            }
        } catch (Exception e) {
            System.err.println(e.getStackTrace());
            return HttpStatus.NOT_ACCEPTABLE;
        }
        return HttpStatus.ACCEPTED;
    }
    
    @CrossOrigin(origins = "*" )
    @RequestMapping(method = RequestMethod.POST, value = "/user/updategoal")
    @ResponseBody
    public HttpStatus updateIdGoalUser(@RequestParam(value = "id") Long id, @RequestParam(value = "idgoal") Long idgoal) throws ParseException {
        User u = userService.findOne(id);
        u.setIdGoal(idgoal);
        try {
            userService.save(u);
        } catch (Exception e) {
            System.err.println(e.getStackTrace());
            return HttpStatus.NOT_ACCEPTABLE;
        }
        return HttpStatus.ACCEPTED;
    }
    
    @CrossOrigin(origins = "*" )
    @RequestMapping(method = RequestMethod.GET, value = "/user/find/{id}")
     public User findUserById(@PathVariable Long id) {
         User user = new User();
       try {
            if (userConnectService.exists(id)) {
                 user = userService.findOne(id);
            } else {
                  return null;
            }
        } catch (Exception e) {
            System.err.println(e.getStackTrace());
            return null;
        }
        return user; 
     }
     
    @CrossOrigin(origins = "*")
    @RequestMapping(method = RequestMethod.POST, value = "/user/setgoal")
    @ResponseBody
    public HttpStatus setGoal(@RequestParam(value = "id") Long id, @RequestParam(value = "idGoal") long idgoal, HttpServletResponse response) throws ParseException {
        User u = userService.findOne(id);
        try {
            u.setIdGoal(idgoal);
            userService.save(u);
        } catch (Exception e) {
            System.err.println(e.getStackTrace());
            return HttpStatus.NOT_ACCEPTABLE;
        }
        return HttpStatus.ACCEPTED;
    }
    
     @CrossOrigin(origins = "*")
    @RequestMapping(method = RequestMethod.POST, value = "/user/setpicture")
    @ResponseBody
    public HttpStatus setPicture(@RequestParam(value = "id") Long id, @RequestParam(value = "picture") String picture) throws ParseException {
        User u = userService.findOne(id);
        try {
            u.setPicture(picture);
            userService.save(u);
        } catch (Exception e) {
            System.err.println(e.getStackTrace());
            return HttpStatus.NOT_ACCEPTABLE;
        }
        return HttpStatus.ACCEPTED;
    }
    
        
    @CrossOrigin(origins = "*")
    @RequestMapping(method = RequestMethod.POST, value = "/user/updateInfos")
    @ResponseBody
    public HttpStatus updateInfos(@RequestParam(value = "id") Long id,@RequestParam(value = "firstname", required = false) String firstname, @RequestParam(value = "lastname", required = false) String lastname,@RequestParam(value = "birthday", required = false) String birthday,  @RequestParam(value = "sexe", required = false) String sexe, @RequestParam(value = "picture", required = false) String picture, @RequestParam(value = "size", required = false) int size)  throws ParseException {
        User u = userService.findOne(id);

        try {
            if (firstname != null) {
                 u.setFirstName(firstname);
            }else if (lastname != null) {
                 u.setLastname(lastname);
            }else if (birthday != null) {
                 String[] birthdayArray = birthday.split("/");
                 String birthdaySimpleFormat = birthdayArray[2]+"-"+birthdayArray[1]+"-"+birthdayArray[0];
                u.setBirthday(birthdaySimpleFormat);
            }else if (sexe != null) {
                u.setSexe(sexe);
            }else if (picture!= null) {
                u.setPicture(picture);
            }else if (size != 0) {
                u.setSize(size);
            }
            userService.save(u);
        } catch (Exception e) {
            System.err.println(e.getStackTrace());
            return HttpStatus.NOT_ACCEPTABLE;
        }
        return HttpStatus.ACCEPTED;
    }
    
    @CrossOrigin(origins = "*" )
   @RequestMapping(method = RequestMethod.DELETE, value = "/user/delete/{id}")
    public HttpStatus DeleteUser(@PathVariable Long id) {
        try {
              if (userService.exists(id)) {
                 userService.delete(id);
             } else {
                 return HttpStatus.METHOD_FAILURE;
            }
        } catch (Exception e) {
            System.err.println(e.getStackTrace());
            return HttpStatus.METHOD_FAILURE;
        }
        return HttpStatus.ACCEPTED;
                
    }
    
    @CrossOrigin(origins = "*" )
    @RequestMapping("/index")
    public String index() {
        System.out.println("Bienvenue chez les users");
        return "";
    }
}
