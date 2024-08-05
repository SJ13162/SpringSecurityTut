package com.learning.simran.prod_ready_feature_tutorial.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DepartmentDTO {
    Long id;
    String title;
    Boolean isActive;
    LocalDate createdAt;
}
