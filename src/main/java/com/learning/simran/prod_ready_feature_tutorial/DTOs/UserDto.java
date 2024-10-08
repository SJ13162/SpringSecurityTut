package com.learning.simran.prod_ready_feature_tutorial.DTOs;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto {

    private Long id;
    private String name;
    private String email;
}
