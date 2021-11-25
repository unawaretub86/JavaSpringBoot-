package com.cursoJavaYt.curso.controllers;

import com.cursoJavaYt.curso.dao.UserDao;
import com.cursoJavaYt.curso.models.User;
import com.cursoJavaYt.curso.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private UserDao userDao;

    @Autowired //Nos permite usarlo en las demas partes del proyecto y ponerle las anotaciones de value etc
    private JwtUtil jwtutil;

    @RequestMapping(value = "api/login", method = RequestMethod.POST)
    public String login(@RequestBody User user){ //primero el tipo luego el nombre de la var , el requestbody es para covertir el json en un user

        User userLogged = userDao.getUserByCredentials(user);

        if (userLogged != null ){

            return jwtutil.create(String.valueOf(userLogged.getId()), userLogged.getEmail());
        }
        return "fail";
    }

}
