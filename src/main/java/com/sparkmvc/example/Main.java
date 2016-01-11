package com.sparkmvc.example;

import com.sparkmvc.init.Application;
import com.sparkmvc.init.Config;

import static spark.Spark.awaitInitialization;
import static spark.Spark.externalStaticFileLocation;
import static spark.Spark.port;

/**
 * @author nurmuhammad
 */
public class Main {
    public static void main(String[] args) {
        externalStaticFileLocation(Config.get("spark.path.external", "/public"));
        port(Integer.valueOf(Config.get("spark.port", "8888")));

        try {
            Application.init();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        awaitInitialization();
    }
}
