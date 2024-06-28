package com.example.test_driven_development.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import com.example.test_driven_development.course.CourseJdbcDao;

@DataJdbcTest
public class CourseJdbcDaoTest {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private CourseJdbcDao dao;
    
    @Disabled
    @Test
    void testContext(){
        assertAll(
            () -> {
                assertNotNull(jdbcTemplate);
                assertNotNull(dao);
            }
        );
    }
}
