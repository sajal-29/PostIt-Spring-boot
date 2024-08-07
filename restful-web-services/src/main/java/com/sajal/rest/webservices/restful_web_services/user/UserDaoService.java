package com.sajal.rest.webservices.restful_web_services.user;

import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CompositeType;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserDaoService {
    private static List<User> users = new ArrayList<>();

    static{
        users.add(new User(1, "Sajal", LocalDate.now().minusYears(21)));
        users.add(new User(2, "Ram", LocalDate.now().minusYears(20)));
        users.add(new User(3, "Venika", LocalDate.now().minusYears(17)));
    }
    private static int count = 3;

    public List<User> findAll(){
        return users;
    }
    public User findOne(Integer id){
        for (User r : users){
            if(r.getId() == id){
                return r;
            }
        }
        return null;
    }

    public User saveUser(User user){
        user.setId(++count);
        users.add(user);
        return user;
    }

    public void deleteById(Integer id){
        for (User r : users){
            if(r.getId().equals(id)){
                users.remove(r);
            }
        }
    }
}
