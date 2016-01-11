package com.sparkmvc.example.web;

import com.sparkmvc.ann.Controller;
import com.sparkmvc.ann.GET;
import com.sparkmvc.ann.Init;
import com.sparkmvc.ann.Template;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * @author nurmuhammad
 */

@Controller
public class UserController {

    @Init
    void init(){

    }

    @GET @Template(viewName = "login.ftl")
    Object login(Request request, Response response){
        Map<String, Object> map = new HashMap<>();
        map.put("message", "test message.");

        return map;
    }
}
