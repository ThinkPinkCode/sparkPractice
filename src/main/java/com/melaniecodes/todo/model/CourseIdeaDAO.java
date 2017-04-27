package com.melaniecodes.todo.model;

import java.util.List;

public interface CourseIdeaDAO {

    boolean add(CourseIdea idea);

    List<CourseIdea> findAll();

    List<String> listVoters();

    CourseIdea findBySlug(String slug);
}
