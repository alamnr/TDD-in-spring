package com.example.test_driven_development.post;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class PostClient{

    private final RestClient restClient;

    public PostClient(RestClient.Builder builder){
        JdkClientHttpRequestFactory factory = new JdkClientHttpRequestFactory();
        this.restClient = builder
                                .baseUrl("https://jsonplaceholder.typicode.com")
                                .requestFactory(factory)
                                .build();
    }

    public List<Post> findAll(){
        return restClient.get()
                         .uri("/posts")
                         .retrieve()
                         .body(new ParameterizedTypeReference<>() { });
    }
}