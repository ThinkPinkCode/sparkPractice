package com.melaniecodes.todo.model;

import com.github.slugify.Slugify;

import java.util.HashSet;
import java.util.Set;

public class CourseIdea {
    private String slug;
    private String courseTitle;
    private String courseCreator;
    private Set<String> voters;

    public CourseIdea(String courseTitle, String courseCreator) {
        voters = new HashSet<>();
        this.courseTitle = courseTitle;
        this.courseCreator = courseCreator;
        Slugify slugify = new Slugify();
        slug = slugify.slugify(courseTitle);
    }

    public String getCourseTitle() {

        return courseTitle;
    }

    public String getCourseCreator() {

        return courseCreator;
    }

    public String getSlug() {

        return slug;
    }

    public boolean addVoter(String voterUsername) {

        return voters.add(voterUsername);
    }

    public int getVoteCount() {

        return voters.size();
    }

    public String getFormattedVoteCount(){
        String noun;

        if (voters.size() == 1){
            noun = "Vote";
        }
        else
            noun = "Votes";

        return getVoteCount()+ " "+ noun;
    }

    public Set<String> getVoters(){
        System.out.println(voters.size());
        return voters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CourseIdea that = (CourseIdea) o;

        if (courseTitle != null ? !courseTitle.equals(that.courseTitle) : that.courseTitle != null) return false;
        return courseCreator != null ? courseCreator.equals(that.courseCreator) : that.courseCreator == null;
    }

    @Override
    public int hashCode() {
        int result = courseTitle != null ? courseTitle.hashCode() : 0;
        result = 31 * result + (courseCreator != null ? courseCreator.hashCode() : 0);
        return result;
    }
}
