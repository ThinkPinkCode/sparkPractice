package com.melaniecodes.todo;

import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import static spark.Spark.get;
import static spark.Spark.post;

public class main
{

    public static void main(String[] args) {

        get("/", (req, res) -> new ModelAndView(null, "index.hbs"), new HandlebarsTemplateEngine());
        post("/signin", (req, res) -> new ModelAndView(null, "signin.hbs"), new HandlebarsTemplateEngine());
    }


}
