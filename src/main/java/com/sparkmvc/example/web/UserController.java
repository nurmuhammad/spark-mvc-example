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

    @Init
    void init() {
    }

    @GET(uri = "/")
    @Template(viewName = "login.ftl")
    Object index() {
        Map<String, Object> map = new HashMap<>();
        return map;
    }

    @GET
    void logout() {
        session().removeAttribute("user");
        response().redirect("/");
    }

    @GET(uri = "login")
    @POST(uri = "login")
    @Template(viewName = "login.ftl")
    Object login() {

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
                request().session().attribute("user", user.email);
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

    @GET(uri = "/user/welcome")
    @Template(viewName = "welcome.ftl")
    Object welcome() {
        String email = session().attribute("user");
        Map<String, Object> map = new HashMap<>();
        map.put("email", email);
        return map;
    }

    @GET(uri = "/user/welcome1")
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
        String email = session().attribute("user");
        return $.isEmpty(email);
    }


}
