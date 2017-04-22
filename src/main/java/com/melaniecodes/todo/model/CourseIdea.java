package com.melaniecodes.todo.model;

import com.github.slugify.Slugify;

public class CourseIdea {
    private String slug;
    private String courseTitle;
    private String courseCreator;

    public CourseIdea(String courseTitle, String courseCreator) {
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
