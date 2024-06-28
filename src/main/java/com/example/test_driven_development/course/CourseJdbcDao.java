package com.example.test_driven_development.course;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class CourseJdbcDao implements DAO<Course> {

    private static final Logger log = LoggerFactory.getLogger(CourseJdbcDao.class);
    private final JdbcTemplate jdbcTemplate;

    public CourseJdbcDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Maps a row in the database to a Course
    RowMapper<Course> rowMapper = (rs, rowNum) -> {
        Course course = new Course(rs.getInt("course_id"), rs.getString("title"),
                rs.getString("description"), rs.getString("link"));
        return course;
    };

    @Override
    public List<Course> list() {
        String sql = "SELECT course_id, title, description, link FROM course ";
        return jdbcTemplate.query(sql, rowMapper);
    }

    // CRUD 
    @Override
    public void create(Course t) {
        
        String  sql = "INSERT INTO course(title, description, link) VALUES (?,?,?)";
        int insert = jdbcTemplate.update(sql,t.title(),t.description(),t.link());
        if(insert == 1){
            log.info("new course created : " + t.title());
        }
    }

    @Override
    public Optional<Course> get(int id) {
        
        String sql = "SELECT course_id, title, description, link FROM course WHERE course_id = ? ";
        Course course = null;
        try {
            course = jdbcTemplate.queryForObject(sql,rowMapper,id);
        } catch (DataAccessException e) {
            log.info("Course not found : " + e.getMessage());
        }
        return Optional.ofNullable(course);
    }

    @Override
    public void update(Course t, int id) {
        String sql = "UPDATE course SET title = ? , description = ? , link = ? where course_id = ? ";
        int update = jdbcTemplate.update(sql, t.title(),t.description(),t.link(), id);
        if(update == 1){
            log.info("Course updated : " + t.title());
        }

    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM course WHERE course_id = ? ";
        int delete = jdbcTemplate.update(sql, id);
        if(delete == 1){
            log.info("Course deleted : " + id);
        }
    }

}
