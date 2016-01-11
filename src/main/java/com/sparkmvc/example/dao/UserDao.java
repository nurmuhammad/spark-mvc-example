package com.sparkmvc.example.dao;

import com.sparkmvc.example.model.User;
import com.sparkmvc.helper.$;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Date;
import java.util.UUID;

/**
 * @author nurmuhammad
 */

public class UserDao extends Dao<User> {
    public static final UserDao instance = new UserDao();

    private UserDao() {
    }

    public User getUserByEmail(String email) {
        Sql2o sql2o = sql2o();
        try (Connection con = sql2o.open()) {
            User user = addColumnMapping(con.createQuery("select * from users where email=:email"))
                    .addParameter("email", email)
                    .executeAndFetchFirst(User.class);
            return user;
        } finally {
            returnSql2o(sql2o);
        }
    }

    public User createUser(User user) {
        user.created = (int) (new Date().getTime() / 1000);
        user.changed = user.created;
        user.salt = UUID.randomUUID().toString();
        user.password = $.md5(user.salt + user.password);
        user.status = true;

        Sql2o sql2o = sql2o();
        String sql =
                "insert into users(email, password, salt, status, created, changed) " +
                        "values (:email, :password, :salt, :status, :created, :changed)";

        try (Connection con = sql2o.open()) {
            user.id = (long) addColumnMapping(con.createQuery(sql, true))
                    .bind(user).executeUpdate().getKey();
        } catch (Exception e){
            System.out.println(e.getMessage());
        } finally {
            returnSql2o(sql2o);
        }

        return user;
    }

}
