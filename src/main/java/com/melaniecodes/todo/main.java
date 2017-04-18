package com.melaniecodes.todo;

import spark.ModelAndView;
import spark.Request;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;

public class main
{

    public static void main(String[] args) {

        get("/", (req, res) -> {
            Map<String, String> model = new HashMap<>();
            String username = req.cookie("username");
            model.put("username", username);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());


        post("/signin", (req, res) -> {
            Map<String, String> model = new HashMap<>();
            String username = req.queryParams("username");
            model.put("username", username);
            res.cookie("username", username);
            return new ModelAndView(model, "signin.hbs");
        }, new HandlebarsTemplateEngine());
    }


}
