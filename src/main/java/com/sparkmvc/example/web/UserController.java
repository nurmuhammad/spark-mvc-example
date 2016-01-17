package com.sparkmvc.example.web;

import com.sparkmvc.ann.*;
import com.sparkmvc.example.dao.UserDao;
import com.sparkmvc.example.model.User;
import com.sparkmvc.helper.$;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

import static com.sparkmvc.helper.$.*;

/**
 * @author nurmuhammad
 */

@Controller
public class UserController {

    public static final String REMEMBER_ME = md5(b64encode("remember me"));
    public static final String USER_SESSION = md5(b64encode("user session"));

    @Init
    void init() {
    }

    @GET("/")
    @Template(viewName = "login.ftl")
    Object index() {
        Map<String, Object> map = new HashMap<>();
        return map;
    }

    @GET
    void logout() {
        session().removeAttribute(USER_SESSION);
        response().removeCookie(REMEMBER_ME);
        response().redirect("/");
    }

    @GET("login")
    @POST("login")
    @Template(viewName = "login.ftl")
    Object login() {

        String cookieEmail = $.request().cookie(REMEMBER_ME);
        if (isLoggedOut() && !isEmpty(cookieEmail)) {
            User user = UserDao.instance.getUserByEmail(cookieEmail);
            if (user != null) {
                request().session().attribute(USER_SESSION, user.email);
            }
            return false;
        }

        String redirect = request().queryParams("r");
        if (!isLoggedOut()) {
            if (isEmpty(redirect)) {
                response().redirect("/user/welcome");
            } else {
                response().redirect(b64decode(redirect));
            }
            return null;
        }

        Map<String, Object> map = new HashMap<>();
        if (request().requestMethod().toLowerCase().equals("post")) {
            String email = request().queryParams("email");
            String password = request().queryParams("password");

            User user = UserDao.instance.getUserByEmail(email);

            if (user != null && $.md5(user.salt + password).equals(user.password)) {
                request().session().attribute(USER_SESSION, user.email);

                if (!isEmpty(request().queryParams("remember-me"))) {

                    response().cookie(REMEMBER_ME, user.email, 7 * 24 * 3600);

                }

                if (isEmpty(redirect)) {
                    response().redirect("/user/welcome");
                } else {
                    response().redirect(b64decode(redirect));
                }
                return null;
            }
            map.put("error", "please try again");
        }

        if (!isEmpty(redirect)) {
            map.put("r", redirect);
        }
        return map;
    }

    @GET("/user/welcome")
    @Template(viewName = "welcome.ftl")
    Object welcome() {
        String email = session().attribute(USER_SESSION);
        Map<String, Object> map = new HashMap<>();
        map.put("email", email);
        return map;
    }

    @GET("/user/welcome1")
    @Template(viewName = "welcome.ftl")
    Object welcome1() {
        Map<String, Object> map = new HashMap<>();
        map.put("email", "test test test");
        return map;
    }

    @GET
    @Json
    Object json() {
        return UserDao.instance.getUserByEmail("demo@gmail.com");
    }

    @Before({@Uri("/user/*"), @Uri("/p/*")})
    void before() {
        if (isLoggedOut()) {
            request().pathInfo();
            response().redirect("/login?r=" + b64encode(path()));
        }
    }

    public static boolean isLoggedOut() {
        String email = session().attribute(USER_SESSION);
        return $.isEmpty(email);
    }


}
