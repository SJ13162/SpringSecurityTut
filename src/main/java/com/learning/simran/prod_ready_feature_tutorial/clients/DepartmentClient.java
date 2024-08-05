package com.learning.simran.prod_ready_feature_tutorial.clients;

import com.learning.simran.prod_ready_feature_tutorial.DTOs.DepartmentDTO;

import java.util.List;

public interface DepartmentClient {

    List<DepartmentDTO> getDepartments();

    DepartmentDTO getDepartmentById(Long id);

    DepartmentDTO createDepartment(DepartmentDTO departmentDTO);
}
