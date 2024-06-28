package com.example.test_driven_development.post;

import java.util.List;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestClientTest(PostClient.class)
public class PostClientTest {

    @Autowired
    MockRestServiceServer mock;
    @Autowired
    PostClient postClient;

    @Autowired
    ObjectMapper objectMapper;
    
    @Test
    void shouldFindAllPosta() throws JsonProcessingException {

        // given 
        List<Post> posts  = List.of(new Post(1,1, "Hello world","This is my first post",0),
                                    new Post(1,1, "Hello world","This is my first post",0));
        
        // when
        mock.expect(requestTo("https://jsonplaceholder.typicode.com/posts"))
            .andRespond(withSuccess(objectMapper.writeValueAsString(posts),MediaType.APPLICATION_JSON));

        // then
        List<Post> data =  postClient.findAll();

        //assertEquals(2,data.size());
        assertEquals(100,data.size());
    }
}
