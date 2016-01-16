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
    Object login(Request request, Response response) {
        Map<String, Object> map = new HashMap<>();
        if (request.requestMethod().toLowerCase().equals("post")) {
            String email = request.queryParams("email");
            String password = request.queryParams("password");

            User user = UserDao.instance.getUserByEmail(email);

            if (user != null && $.md5(user.salt + password).equals(user.password)) {
                request.session().attribute("user", user.email);
                response.redirect("/user/welcome");
                return null;
            }

            map.put("error", "please try again");
        }
        return map;
    }

    @GET(uri = "/user/welcome")
    @Template(viewName = "welcome.ftl")
    Object welcome(Request request, Response response) {
        String email = request.session().attribute("user");
        Map<String, Object> map = new HashMap<>();
        map.put("email", email);
        return map;
    }

    @GET
    @Json
    Object json(Request request, Response response) {
        return UserDao.instance.getUserByEmail("demo@gmail.com");
    }

    @Before(uri = "/user/*")
    void before(Request request, Response response) {
        String email = request.session().attribute("user");
        if ($.isEmpty(email)) {
            response.redirect("/login");
        }
    }


}
