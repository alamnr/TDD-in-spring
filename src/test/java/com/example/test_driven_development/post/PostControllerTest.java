package com.example.test_driven_development.post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PostRepository postRepository;

    List<Post> posts = new ArrayList<>();

    @BeforeEach
    void setUp() {
        posts = List.of(
                new Post(1, 1, "Hello World", "This is my first post", null),
                new Post(2, 1, "Second Post", "This is my second post", null));
    }

    @Test
    void shouldFinsAllPosts() throws Exception {

        String jsonResponse = """
                [
                    {
                        "id":1,
                        "userId":1,
                        "title":"Hello World",
                        "body": "This is my first post",
                        "version":null
                    },
                    {
                        "id":2,
                        "userId":1,
                        "title": "Second Post",
                        "body":"This is my second post",
                        "version":null
                    }
                ]
                """;
        
        when(postRepository.findAll()).thenReturn(posts);

        mockMvc.perform(get("/api/posts"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse,false));
    }

    @Test
    void shouldFindAPostWhenGivenValidId() throws Exception{
        when(postRepository.findById(1)).thenReturn(Optional.of(posts.get(0)));

        var post = posts.get(0);
        var json = STR."""
                {
                    "id":\{post.id()},
                    "userId":\{post.userId()},
                    "title":"\{post.title()}",
                    "body":"\{post.body()}",
                    "version":null
                }
                """;
        mockMvc.perform(get("/api/posts/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }

    @Test
    void shouldNotFoundPostById() throws Exception{

        when(postRepository.findById(999)).thenThrow(PostNotFoundException.class);

        mockMvc.perform(get("/api/posts/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateNewPostWhenPostIsValid() throws Exception{

        var post = new Post(3,1,"New Title","New Body",null);
        when(postRepository.save(post)).thenReturn(post);
        var json = STR."""
                {
                    "id":\{post.id()},
                    "userId":\{post.userId()},
                    "title":"\{post.title()}",
                    "body":"\{post.body()}",
                    "version":null
                }
                """;
        
        mockMvc.perform(post("/api/posts")
                .contentType("application/json")
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldNotCreateWhenPostIsInvalid() throws Exception{

        var post = new Post(3,1,"","",null);
        when(postRepository.save(post)).thenReturn(post);
        var json = STR."""
                {
                    "id":\{post.id()},
                    "userId":\{post.userId()},
                    "title":"\{post.title()}",
                    "body":"\{post.body()}",
                    "version":null
                }
                """;
        
        mockMvc.perform(post("/api/posts")
                .contentType("application/json")
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldUpdateValidPost() throws Exception{
        Post updated = new Post(1,1,"updated title","updated body",1);

        when(postRepository.findById(1)).thenReturn(Optional.of(posts.get(0)));
        when(postRepository.save(updated)).thenReturn(updated);

        var json = STR."""
                {
                    "id":\{posts.get(0).id()},
                    "userId":\{posts.get(0).userId()},
                    "title":"\{updated.title()}",
                    "body":"\{updated.body()}",
                    "version":\{posts.get(0).version()}
                }
                """;
        
        mockMvc.perform(put("/api/posts/1")
                .contentType("application/json")
                .content(json))
                .andExpect(status().isOk());
    }

    
    @Test
    void shouldNotUpdateInValidPost() throws Exception{
        Post updated = new Post(1,1,"","",1);

        when(postRepository.findById(1)).thenReturn(Optional.of(posts.get(0)));
        when(postRepository.save(updated)).thenReturn(updated);

        var json = STR."""
                {
                    "id":\{posts.get(0).id()},
                    "userId":\{posts.get(0).userId()},
                    "title":"\{updated.title()}",
                    "body":"\{updated.body()}",
                    "version":\{posts.get(0).version()}
                }
                """;
        
        mockMvc.perform(put("/api/posts/1")
                .contentType("application/json")
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldDeletePostWhenFound() throws Exception{
        doNothing().when(postRepository).deleteById(anyInt());;
        mockMvc.perform(delete("/api/posts/1"))
                .andExpect(status().isNoContent());

        verify(postRepository,times(1)).deleteById(anyInt());
    }  
}
