package com.cursoJavaYt.curso.dao;

import com.cursoJavaYt.curso.models.User;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository //hace refe a la conex con bd, para acceder al repositorio
@Transactional //nos permite armar consultas sql a bd , como las va a hacer y asi
public class UserDaoImp implements UserDao{

    @PersistenceContext
    EntityManager entityManager;


    @Override
    @Transactional
    public List<User> read() {
        String query = "FROM User";
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public void delete(Long id) {
        User user = entityManager.find(User.class ,id);
        entityManager.remove(user);
    }

    @Override
    public void create(User user) {
        entityManager.merge(user); //guardando user en db
    }

    @Override
    public User getUserByCredentials(User user) {
        String query = "FROM User WHERE email = :email";
        List<User> userList = entityManager.createQuery(query)
                .setParameter("email", user.getEmail())
                .getResultList();

        if (userList.isEmpty()) {
            return null;
        }

        String passwordHashed = userList.get(0).getPassword();

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

        if (argon2.verify(passwordHashed, user.getPassword())) {
            return userList.get(0);

        } else {
            return null;
        }
    }
}
