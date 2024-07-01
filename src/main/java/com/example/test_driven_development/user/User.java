package com.example.test_driven_development.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record User(@NotEmpty String name, @Email String email) {
    
}
