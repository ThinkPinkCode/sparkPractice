package com.melaniecodes.todo;

import com.melaniecodes.todo.model.CourseIdea;
import com.melaniecodes.todo.model.CourseIdeaDAO;
import com.melaniecodes.todo.model.NotFoundException;
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

    private static final String FLASH_MESSAGE_KEY = "flashMessage";

    public static void main(String[] args) {

        staticFileLocation("/public");
        CourseIdeaDAO dao = new SimpleCourseIdeaDAO();

        before((req, res) -> {
            if (req.cookie("username") != null){
                req.attribute(("username"), req.cookie("username"));
            }
        });

        before("/ideas", ((req, res) -> {
            if (req.attribute("username") == null) {
                res.redirect("/");
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
            model.put("flashMessage", getFlashMessage(req));
            return new ModelAndView(model, "ideas.hbs");
        }, new HandlebarsTemplateEngine());

        post ("/ideas", (req, res) -> {
            String courseName = req.queryParams("courseName");
            CourseIdea newCourseIdea = new CourseIdea(courseName, req.attribute("username"));
            dao.add(newCourseIdea);
            res.redirect("/ideas");
            return null;
        });

        get ("/ideas/:slug", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            CourseIdea theCourseIdea = dao.findBySlug(req.params("slug"));
            model.put("idea", theCourseIdea);
            return new ModelAndView(model, "ideaDetails.hbs");
        }, new HandlebarsTemplateEngine());

        post("/ideas/:slug/vote", ((req, res) -> {
            CourseIdea myCourseIdea = dao.findBySlug(req.params("slug"));
            boolean added = myCourseIdea.addVoter(req.attribute("username"));
            if (added) {
                setFlashMessage( req, "Thanks for your vote!");
            }
            res.redirect("/ideas");
            return null;
        }));

        exception(NotFoundException.class, (exc, req, res) -> {
            res.status(404);
            HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();
            String html = engine.render(new ModelAndView(null, "not-found.hbs"));
            res.body(html);
        });
    }

    private static void setFlashMessage(Request req, String message) {
        req.session().attribute(FLASH_MESSAGE_KEY, message);
    }

private static String getFlashMessage(Request req){

    if (req.session(false) == null) {
        return null;
    }

    if (!req.session().attributes().contains(FLASH_MESSAGE_KEY)) {
        return null;
    }

    return (String)req.session().attribute(FLASH_MESSAGE_KEY);
}
}
