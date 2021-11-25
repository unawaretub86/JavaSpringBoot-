package com.cursoJavaYt.curso.controllers;

import com.cursoJavaYt.curso.dao.UserDao;
import com.cursoJavaYt.curso.models.User;
import com.cursoJavaYt.curso.utils.JwtUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

//    @RequestMapping(value = "api/user/{id}", method = RequestMethod.POST)
//    public User create(){
//        User user = new User();
//        user.setName("lucas");
//        user.setLastName("Moy");
//        user.setTelephone("2124124");
//        user.setEmail("LucasMoy@mail.com");
//        user.setPassword("Lucas123");
//
//        return user;
//    }

//    @RequestMapping(value = "api/user/{id}", method = RequestMethod.GET)
//    public User read(@PathVariable Long id){
//        User user = new User();
//        user.setId(id);
//        user.setName("lucas");
//        user.setLastName("Moy");
//        user.setTelephone("2124124");
//        user.setEmail("LucasMoy@mail.com");
//        user.setPassword("Lucas123");
//
//        return user;
//
//    }

    @Autowired //automaticamente nos hace la clase de implementacion , cree un objeto y lo guarde aca abajo (inyeccion dependencias)
    private UserDao userDao;

    @Autowired
    private JwtUtil jwtUtil;

    @RequestMapping(value = "api/users", method = RequestMethod.GET)
    public List<User> read(@RequestHeader(value = "Authorization") String token){
            if (!validateToken(token)) {return null;}
        return userDao.read(); //esto nos devuelve la lista , hace la consulta
    }

    private boolean validateToken(String token){

        String userId =  jwtUtil.getKey(token);
        return userId != null ;

    }

    @RequestMapping(value = "api/users", method = RequestMethod.POST)
    public void create(@RequestBody  User user){ //primero el tipo luego el nombre de la var , el requestbody es para covertir el json en un user

//      llamamos a la libreria para el encode del pass y se hace asi el encode
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
//      el primer valor (3) es las veces de encriptado, el segundo es la cantidad de memoria que puede usar, el segundo es cuanto consumir para que
//      haga mas rapido el proceso, el ulitmo da
        String hash =  argon2.hash(3, 1024, 3, user.getPassword());
        user.setPassword(hash);

        userDao.create(user); //esto nos devuelve la lista , hace la consulta

    }

    @RequestMapping(value = "api/users/{id}", method = RequestMethod.DELETE)
    public void delete(@RequestHeader(value = "Authorization") String token ,@PathVariable Long id){
        if (!validateToken(token)) {return;}
        userDao.delete(id);
    }

//    @RequestMapping(value = "api/users/{id}", method = RequestMethod.PUT)
//    public User update(){
//        User user = new User();
//        user.setName("lucas");
//        user.setLastName("Moy");
//        user.setTelephone("2124124");
//        user.setEmail("LucasMoy@mail.com");
//        user.setPassword("Lucas123");
//
//        return user;
//    }
}
