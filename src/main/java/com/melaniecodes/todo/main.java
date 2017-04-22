package com.melaniecodes.todo;

import com.melaniecodes.todo.model.CourseIdea;
import com.melaniecodes.todo.model.CourseIdeaDAO;
import com.melaniecodes.todo.model.SimpleCourseIdeaDAO;
import spark.ModelAndView;
import spark.Request;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class main
{

    public static void main(String[] args) {

        staticFileLocation("/public");
        CourseIdeaDAO dao = new SimpleCourseIdeaDAO();

       before((request, response) -> {
           if (request.cookie("username") != null){
               request.attribute(("username"), request.cookie("username"));
           }
       });

        before("/ideas", ((request, response) -> {
            if (request.attribute("username") == null) {
                response.redirect("/");
                halt();
            }
        }));

        get("/", (req, res) -> {
            Map<String, String> model = new HashMap<>();
            String username = req.attribute("username");
            model.put("username", username);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        post("/signin", (req, res) -> {
            Map<String, String> model = new HashMap<>();
            String username = req.queryParams("username");
            model.put("username", username);
            res.cookie("username", username);
            res.redirect("/");
            return null;
        });

        get ("/ideas", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("ideas", dao.findAll());
            return new ModelAndView(model, "ideas.hbs");
        }, new HandlebarsTemplateEngine());

        post ("/ideas", (req, res) -> {
            String courseName = req.queryParams("courseName");
            CourseIdea newCourseIdea = new CourseIdea(courseName, req.attribute("username"));
            dao.add(newCourseIdea);
            res.redirect("/ideas");
            return null;
        });

    }


}
