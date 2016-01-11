package com.sparkmvc.example;

import com.sparkmvc.helper.$;
import com.sparkmvc.init.Application;
import com.sparkmvc.init.Config;
import com.sparkmvc.init.PoolService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import static spark.Spark.awaitInitialization;
import static spark.Spark.externalStaticFileLocation;
import static spark.Spark.port;

/**
 * @author nurmuhammad
 */
public class Main {

    public static void main(String[] args) {

        try {
            ScriptRunner runner = new ScriptRunner(PoolService.getDataSource().getConnection(), false, false);
            String file = $.runFolder() + File.separator + "dump.sql";
            runner.runScript(new BufferedReader(new FileReader(file)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String externarFolder = Config.get("spark.path.external", "/public");
        if(externarFolder.startsWith("...")){
            externarFolder = externarFolder.replace("...", $.runFolder());
        }

        externalStaticFileLocation(externarFolder);
        port(Integer.valueOf(Config.get("spark.port", "8888")));

        try {
            Application.init();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        awaitInitialization();
    }
}
