package com.learning.simran.prod_ready_feature_tutorial.DTOs;

import lombok.Data;

@Data
public class SignUpDto {
    private String email;
    private String password;
    private String name;
}
