package com.learning.simran.prod_ready_feature_tutorial;

import com.learning.simran.prod_ready_feature_tutorial.DTOs.DepartmentDTO;
import com.learning.simran.prod_ready_feature_tutorial.clients.DepartmentClient;
import com.learning.simran.prod_ready_feature_tutorial.entities.User;
import com.learning.simran.prod_ready_feature_tutorial.services.JwtService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProdReadyFeatureTutorialApplicationTests {

	@Autowired
	DepartmentClient departmentClient;

	@Autowired
	JwtService jwtService;

	@Test
	@Order(3)
	void getAllDepartment () {
		List<DepartmentDTO> departmentDTOList = departmentClient.getDepartments();
		departmentDTOList
				.forEach(x -> System.out.println(x.getTitle()));
	}

	@Test
	@Order(2)
	void getDepartmentByid () {
		DepartmentDTO departmentDTO = departmentClient.getDepartmentById(1L);
		System.out.println(departmentDTO.getTitle());
	}

	@Test
	@Order(1)
	void createDepartment () {
		DepartmentDTO departmentDTO = new DepartmentDTO();
		departmentDTO.setTitle("title created 1");
		departmentDTO.setIsActive(true);
		DepartmentDTO  result = departmentClient.createDepartment(departmentDTO);
		System.out.println(result.getTitle());
	}

	@Test
	void contextLoads(){
		User user = new User(4L, "abcd@gmail.com", "1234");

		String token = jwtService.generateToken(user);

		System.out.println(token);

		Long extractIdFromToken = jwtService.getUserIdFromToken(token);

		System.out.println(extractIdFromToken);
	}
}
