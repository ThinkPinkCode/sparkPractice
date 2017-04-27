package com.melaniecodes.todo.model;

import java.util.ArrayList;
import java.util.List;

public class SimpleCourseIdeaDAO implements CourseIdeaDAO {
    private List<CourseIdea> ideas;
    private List<String> voters;

    public SimpleCourseIdeaDAO() {

        ideas = new ArrayList<>();
        voters = new ArrayList<>();
    }

    @Override
    public boolean add(CourseIdea idea) {

        return ideas.add(idea);
    }

    @Override
    public List<String> listVoters() {
        return new ArrayList<>(voters);
    }

    @Override
    public List<CourseIdea> findAll() {

        return new ArrayList<>(ideas);
    }

    @Override
    public CourseIdea findBySlug(String slug) {
        return ideas.stream()
                .filter(idea -> idea.getSlug().equals(slug))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }
}
